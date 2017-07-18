package com.example.learning.kotlinmusicplayer.util

import android.util.Log

object PlayerLog {

    val PLAYER_LOG_TAG = "PlayerLog"

    fun d(message: String){
        Log.d(PLAYER_LOG_TAG, message)
    }

    fun i(message: String){
        Log.i(PLAYER_LOG_TAG, message)
    }

    fun v(message: String){
        Log.v(PLAYER_LOG_TAG, message)
    }
}