package com.example.learning.kotlinmusicplayer.main

import com.example.learning.kotlinmusicplayer.BasePresenter
import com.example.learning.kotlinmusicplayer.service.MediaPlayerService

interface IMainPresenter : BasePresenter {
    fun onAttachService(service: MediaPlayerService.MusicBinder)
    fun onDetachService()
}