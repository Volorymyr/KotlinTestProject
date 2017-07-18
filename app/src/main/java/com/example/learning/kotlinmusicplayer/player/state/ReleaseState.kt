package com.example.learning.kotlinmusicplayer.player.state

import com.example.learning.kotlinmusicplayer.player.Player

class ReleaseState : PlayerState {
    override val TAG: String = this.javaClass.simpleName

    override fun executePlayerOperation(player: Player) {
       player.release()
    }
}