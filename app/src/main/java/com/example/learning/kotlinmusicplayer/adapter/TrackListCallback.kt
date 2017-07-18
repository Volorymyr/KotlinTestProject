package com.example.learning.kotlinmusicplayer.adapter

interface TrackListCallback {
    //TODO:in the future change boolean to PlayerState
    fun onTrackListItemClick(position: Int, isStartPlaying: Boolean)
}