package com.example.learning.kotlinmusicplayer.main

import android.app.LoaderManager
import android.content.Loader
import android.os.Bundle
import com.example.learning.kotlinmusicplayer.adapter.TrackListCallback
import com.example.learning.kotlinmusicplayer.model.Song
import com.example.learning.kotlinmusicplayer.player.state.PlayingState
import com.example.learning.kotlinmusicplayer.player.state.StopState
import com.example.learning.kotlinmusicplayer.service.MediaPlayerService
import com.example.learning.kotlinmusicplayer.task.LoadSongTask

class MainPresenter(val view: IMainView<MainPresenter>,val loadSongTask: LoadSongTask,
                    val loaderManager: LoaderManager) : IMainPresenter, TrackListCallback {

    private val SONG_LOADER_ID = 1
    private var serviceBinder: MediaPlayerService.MusicBinder? = null

    //TODO:in the future remove this list, and make passing data through DB
    private var songs: List<Song>? = null

    override fun start() {
        loadSongList()
    }

    private fun loadSongList() {
        loaderManager.initLoader(SONG_LOADER_ID, null, loaderCallback)
    }

    /**
     * @position of song in list
     * @isStartPlaying in the future replace boolean on PlayerState
     */
    override fun onTrackListItemClick(position: Int, isStartPlaying: Boolean) {
        serviceBinder?.getMediaPlayerService?.songs = this@MainPresenter.songs

        if (serviceBinder?.getMediaPlayerService?.getState()?.TAG.equals(PlayingState().TAG)){
            serviceBinder?.getMediaPlayerService?.setPlayerState(StopState())
        }

        serviceBinder?.getMediaPlayerService?.currentSongPosition = position
        serviceBinder?.getMediaPlayerService?.setPlayerState(if (!isStartPlaying) StopState() else PlayingState())
        //change UI
        serviceBinder?.getMediaPlayerService?.getState()?.let { view.changeControlPanelState(it) }
    }

    override fun onAttachService(service: MediaPlayerService.MusicBinder) {
        serviceBinder = service
    }

    override fun onDetachService() {
        serviceBinder = null
    }

    val loaderCallback: LoaderManager.LoaderCallbacks<List<Song>> = object : LoaderManager.LoaderCallbacks<List<Song>> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Song>> {
            return loadSongTask
        }

        override fun onLoaderReset(loader: Loader<List<Song>>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onLoadFinished(loader: Loader<List<Song>>?, songs: List<Song>) {
            //TODO:in the future remove this list, and make passing data through DB
            this@MainPresenter.songs = songs
            view.updatePlayListData(songs)
        }
    }

}