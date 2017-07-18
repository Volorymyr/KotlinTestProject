package com.example.learning.kotlinmusicplayer.main

import com.example.learning.kotlinmusicplayer.BaseView
import com.example.learning.kotlinmusicplayer.model.Song
import com.example.learning.kotlinmusicplayer.player.state.PlayerState

interface IMainView<T> : BaseView<T> {
    fun updatePlayListData(songs: List<Song>)
    fun changeControlPanelState(state: PlayerState)
}