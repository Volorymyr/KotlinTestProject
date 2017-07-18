package com.example.learning.kotlinmusicplayer.player.state

import com.example.learning.kotlinmusicplayer.player.Player
import com.example.learning.kotlinmusicplayer.util.PlayerLog

interface PlayerState {
     val TAG: String

     fun executePlayerOperation(player: Player){
          PlayerLog.d("executePlayerOperation called")
     }
}