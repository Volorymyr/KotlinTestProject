package com.example.learning.kotlinmusicplayer.player

import android.media.MediaPlayer
import com.example.learning.kotlinmusicplayer.model.Song
import com.example.learning.kotlinmusicplayer.util.PlayerLog

class Player : MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private val mediaPlayer: MediaPlayer

    var songs: List<Song>? = null
    var currentSongPosition: Int? = null

    init {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnCompletionListener(this)
    }

    fun startPlaying() {
        try {
            mediaPlayer.setDataSource(songs?.get(currentSongPosition as Int)?.path)
        }catch (exc: Exception){
            PlayerLog.d(exc.message as String)
            return
        }

        mediaPlayer.prepareAsync()
    }

    fun stopSong() {
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    fun release() {
        currentSongPosition = null
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCompletion(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}