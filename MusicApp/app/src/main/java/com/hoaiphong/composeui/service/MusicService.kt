package com.hoaiphong.composeui.service

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.hoaiphong.composeui.R
import com.hoaiphong.composeui.MainActivity
import com.hoaiphong.composeui.data.local.model.entity.Song
import com.hoaiphong.composeui.ui.playsong.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Exception

class MusicService : Service() {

    private val binder = LocalBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused: Boolean = false

    private var isServiceStarted = false

    private var originalPlaylist: List<Song> = emptyList()
    private var currentPlaylist: List<Song> = emptyList()
    private var currentSongIndex: Int = 0
    private var isShuffle: Boolean = false
    private var isLoop: Boolean = false

    private lateinit var mediaSession: MediaSessionCompat

    private val _isPlaying = MutableLiveData(false)
    private val _currentSong = MutableLiveData<Song?>()

    // Exposed StateFlow to UI
    private val _playerState = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    private val handler = Handler(Looper.getMainLooper())
    private val positionUpdater = object : Runnable {
        override fun run() {
            val pos = mediaPlayer?.currentPosition?.toLong() ?: 0L
            val dur = mediaPlayer?.duration?.toLong() ?: 0L
            val current = _playerState.value
            _playerState.value = current.copy(
                currentTime = pos,
                duration = dur,
                isPlaying = mediaPlayer?.isPlaying == true
            )
            handler.postDelayed(this, 500L)
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mediaSession = MediaSessionCompat(this, "MusicService").apply {
            setCallback(object : MediaSessionCompat.Callback() {
                override fun onPlay() = playCurrentSong()
                override fun onPause() = pauseSong()
                override fun onSkipToNext() = playNextSong()
                override fun onSkipToPrevious() = playPreviousSong()
            })
            isActive = true
        }
    }

    override fun onDestroy() {
        releaseMediaPlayer()
        mediaSession.release()
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onUnbind(intent: Intent?): Boolean {
        return true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isServiceStarted) {
            isServiceStarted = true
            startForeground(NOTIFICATION_ID, buildNotification(title = "No song playing"))
        }

        intent?.action?.let { action ->
            when (action) {
                ACTION_PLAY_PAUSE -> if (isPlaying()) pauseSong() else playCurrentSong()
                ACTION_NEXT -> playNextSong()
                ACTION_PREVIOUS -> playPreviousSong()
                ACTION_PLAY_PLAYLIST -> {
                    val playlist = intent.getParcelableArrayListExtra<Song>("playlist") ?: emptyList()
                    val startIndex = intent.getIntExtra("startIndex", 0)
                    setPlaylist(playlist, startIndex)
                    playCurrentSong(forcePlay = true)
                }
                ACTION_STOP -> {
                    stopSong()
                    stopSelf()
                }
            }
        }

        return START_STICKY
    }

    // ------------- MediaPlayer helpers -------------

    private fun initMediaPlayerIfNeeded() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setOnCompletionListener {
                if (!isLoop) playNextSong()
            }
            mediaPlayer?.setOnErrorListener { mp, what, extra ->
                Log.e(TAG, "MediaPlayer error: what=$what extra=$extra")
                mp.reset()
                _isPlaying.postValue(false)
                _currentSong.postValue(null)
                _playerState.value = PlayerState()
                true
            }
        } else {
            mediaPlayer?.reset()
        }
    }

    private fun releaseMediaPlayer() {
        try {
            mediaPlayer?.stop()
        } catch (e: Exception) { /* ignore */ }
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacks(positionUpdater)
        _isPlaying.postValue(false)
        _currentSong.postValue(null)
        _playerState.value = PlayerState()
    }

    private fun prepareAndStart(uri: String, song: Song) {
        try {
            initMediaPlayerIfNeeded()
            mediaPlayer?.apply {
                setDataSource(uri)
                setOnPreparedListener {
                    start()
                    isPaused = false
                    _isPlaying.postValue(true)
                    _currentSong.postValue(song)
                    updateMediaSessionMetadata(song)
                    startPositionUpdates()
                    startForeground(NOTIFICATION_ID, buildNotification(song = song))
                    _playerState.value = PlayerState(
                        songName = song.name ?: "",
                        artistName = song.artist ?: "Unknown Artist",
                        image = song.image ?: "",
                        currentTime = 0L, 
                        duration = duration.toLong(),
                        isPlaying = true
                    )
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            Log.e(TAG, "prepareAndStart error", e)
        }
    }

    private fun updateMediaSessionMetadata(song: Song) {
        mediaSession.setMetadata(
            MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, song.name)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, song.artist ?: "Unknown Artist")
                .build()
        )
        mediaSession.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1f)
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY_PAUSE or
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                )
                .build()
        )
    }

    private fun startPositionUpdates() {
        handler.removeCallbacks(positionUpdater)
        handler.post(positionUpdater)
    }

    // ------------- Public control API -------------

    fun setPlaylist(playlist: List<Song>, startIndex: Int = 0) {
        originalPlaylist = playlist.toList()
        currentPlaylist = originalPlaylist.toList()
        currentSongIndex = startIndex.coerceIn(0, currentPlaylist.size - 1).takeIf { currentPlaylist.isNotEmpty() } ?: 0
    }

    fun playCurrentSong(forcePlay: Boolean = false) {
        if (currentPlaylist.isEmpty()) return
        val song = currentPlaylist.getOrNull(currentSongIndex) ?: return

        if (!forcePlay && mediaPlayer != null && isPaused) {
            resumeSong()
            return
        }
        prepareAndStart(song.data, song)
    }

    fun pauseSong() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isPaused = true
                _isPlaying.postValue(false)
                updateMediaSessionPlaybackState(PlaybackStateCompat.STATE_PAUSED, it.currentPosition.toLong())
                _playerState.value = _playerState.value.copy(isPlaying = false)
                updateNotificationForCurrentSong()
            }
        }
    }

    private fun resumeSong() {
        mediaPlayer?.let {
            it.start()
            isPaused = false
            _isPlaying.postValue(true)
            updateMediaSessionPlaybackState(PlaybackStateCompat.STATE_PLAYING, it.currentPosition.toLong())
            startPositionUpdates()
            updateNotificationForCurrentSong()
        }
    }

    private fun updateMediaSessionPlaybackState(state: Int, position: Long) {
        mediaSession.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(state, position, 1f)
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY_PAUSE or
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                )
                .build()
        )
    }

    fun stopSong() {
        mediaPlayer?.stop()
        isPaused = false
        _isPlaying.postValue(false)
        _playerState.value = PlayerState()
        stopForeground(true)
    }

    fun playNextSong() {
        if (currentPlaylist.isEmpty()) return
        currentSongIndex = (currentSongIndex + 1) % currentPlaylist.size
        isPaused = false
        playCurrentSong(true)
    }

    fun playPreviousSong() {
        if (currentPlaylist.isEmpty()) return
        currentSongIndex = if (currentSongIndex - 1 < 0) currentPlaylist.size - 1 else currentSongIndex - 1
        isPaused = false
        playCurrentSong(true)
    }

    fun toggleLoop() {
        isLoop = !isLoop
        mediaPlayer?.isLooping = isLoop
        _playerState.value = _playerState.value.copy(isRepeat = isLoop)
    }

    fun toggleShuffle() {
        isShuffle = !isShuffle
        if (isShuffle) {
            val current = currentPlaylist.getOrNull(currentSongIndex)
            val shuffled = originalPlaylist.toMutableList().apply { shuffle() }
            current?.let {
                shuffled.removeAll { it.songId == current.songId }
                shuffled.add(0, current)
            }
            currentPlaylist = shuffled.toList()
            currentSongIndex = 0
        } else {
            val current = currentPlaylist.getOrNull(currentSongIndex)
            currentPlaylist = originalPlaylist.toList()
            currentSongIndex = current?.let {
                originalPlaylist.indexOfFirst { it.songId == current.songId }.takeIf { it != -1 } ?: 0
            } ?: 0
        }
        _playerState.value = _playerState.value.copy(isShuffle = isShuffle)
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying == true

    fun seekTo(position: Long) {
        mediaPlayer?.seekTo(position.toInt())
    }

    // ------------- Notification helpers -------------

    private fun buildNotification(song: Song? = null, title: String? = null): Notification {
        val playPauseIntent = PendingIntent.getService(
            this, 0, Intent(this, MusicService::class.java).apply { action = ACTION_PLAY_PAUSE },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val nextIntent = PendingIntent.getService(
            this, 1, Intent(this, MusicService::class.java).apply { action = ACTION_NEXT },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val prevIntent = PendingIntent.getService(
            this, 2, Intent(this, MusicService::class.java).apply { action = ACTION_PREVIOUS },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val contentIntent = PendingIntent.getActivity(
            this,
            3,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setOnlyAlertOnce(true)
            .setContentIntent(contentIntent)

        if (song != null) {
            builder
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
                .setContentTitle(song.name)
                .setContentText(song.artist ?: "Unknown Artist")
                .addAction(R.drawable.ic_back, "Prev", prevIntent)
                .addAction(if (isPlaying()) R.drawable.ic_pause else R.drawable.ic_play,
                           if (isPlaying()) "Pause" else "Play", playPauseIntent)
                .addAction(R.drawable.ic_next, "Next", nextIntent)
                .setOngoing(isPlaying())
        } else {
            builder
                .setContentTitle(title ?: "No song playing")
                .setContentText("Unknown Artist")
                .setOngoing(false)
        }

        return builder.build()
    }

    private fun updateNotificationForCurrentSong() {
        _currentSong.value?.let { song ->
            val manager = getSystemService(NotificationManager::class.java)
            manager?.notify(NOTIFICATION_ID, buildNotification(song = song))
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Music Playback", NotificationManager.IMPORTANCE_LOW).apply {
                setShowBadge(false)
            }
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "MusicChannel"
        const val NOTIFICATION_ID = 101
        const val ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREVIOUS = "ACTION_PREVIOUS"
        const val ACTION_PLAY_PLAYLIST = "ACTION_PLAY_PLAYLIST"
        const val ACTION_STOP = "ACTION_STOP"
        private const val TAG = "MusicService"
    }
}
