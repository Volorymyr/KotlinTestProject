package com.example.learning.kotlinmusicplayer.player

import com.example.learning.kotlinmusicplayer.model.Song
import com.example.learning.kotlinmusicplayer.player.state.PlayerState
import com.example.learning.kotlinmusicplayer.player.state.ReleaseState

class PlayerWrapper {

    private val player: Player = Player()
    var currentState: PlayerState = ReleaseState()

    fun setState(state: PlayerState){
        currentState = state
        state.executePlayerOperation(player)
    }

    fun setSongs(songs: List<Song>){
        player.songs = songs
    }

    fun setCurrentSongPosition(position: Int){
        player.currentSongPosition = position
    }
}