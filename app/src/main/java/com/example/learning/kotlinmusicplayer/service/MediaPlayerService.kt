package com.example.learning.kotlinmusicplayer.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.learning.kotlinmusicplayer.model.Song
import com.example.learning.kotlinmusicplayer.player.PlayerWrapper
import com.example.learning.kotlinmusicplayer.player.state.PlayerState
import com.example.learning.kotlinmusicplayer.player.state.ReleaseState

class MediaPlayerService : Service(){

    val playerWrapper: PlayerWrapper = PlayerWrapper()

    val musicBinder: IBinder = MusicBinder()

    var songs: List<Song>? = null
    set(value){
        if (value != null) {
            playerWrapper.setSongs(value)
        }
    }
    var currentSongPosition: Int = 0
    set(value){
        playerWrapper.setCurrentSongPosition(value)
    }

    override fun onBind(intent: Intent?): IBinder {
        return musicBinder
    }


    override fun onUnbind(intent: Intent?): Boolean {
        playerWrapper.setState(ReleaseState())
        return true
    }

    inner class MusicBinder : Binder() {
        internal val getMediaPlayerService: MediaPlayerService
            get() = this@MediaPlayerService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    fun setPlayerState(state: PlayerState){
        playerWrapper.setState(state)
    }

    fun getState(): PlayerState{
        return playerWrapper.currentState
    }
}