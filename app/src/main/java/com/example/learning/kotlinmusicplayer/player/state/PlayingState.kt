package com.example.learning.kotlinmusicplayer.player.state

import com.example.learning.kotlinmusicplayer.player.Player
import com.example.learning.kotlinmusicplayer.util.PlayerLog

class PlayingState : PlayerState {
    override val TAG: String = this.javaClass.simpleName

    override fun executePlayerOperation(player: Player) {
        try {
            player.startPlaying()
        }catch (exc: Exception){
            //TODO: handle error in future
            PlayerLog.d(exc.message as String)
        }
    }
}