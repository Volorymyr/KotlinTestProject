package com.example.learning.kotlinmusicplayer.task

import android.content.AsyncTaskLoader
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.learning.kotlinmusicplayer.data.SongsHelper
import com.example.learning.kotlinmusicplayer.model.Song

class LoadSongTask(context: Context) : AsyncTaskLoader<List<Song>>(context) {
    var songsHelper: SongsHelper = SongsHelper(context)

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): List<Song> {
        //TODO: save in db in the future
        val songList: List<Song> = songsHelper.getAllLocalSongs()
        return songList
    }

    //TODO: for the future
    val broadCastReceiver: BroadcastReceiver = object: BroadcastReceiver(){
        override fun onReceive(receiverContext: Context, intent: Intent?) {
            onContentChanged()
        }
    }

}