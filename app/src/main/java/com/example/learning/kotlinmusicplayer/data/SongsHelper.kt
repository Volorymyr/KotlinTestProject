package com.example.learning.kotlinmusicplayer.data

import android.content.ContentResolver
import android.content.Context
import com.example.learning.kotlinmusicplayer.model.Song

class SongsHelper(val context: Context) {

    fun getAllLocalSongs(): List<Song>{
        val songList: MutableList<Song> = ArrayList()
        val musicResolver = context.contentResolver as ContentResolver
        val musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor = musicResolver.query(musicUri, null, null, null, null)

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            val titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)
            val path = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA)
            val artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST)
            val musicTypeColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.IS_MUSIC)
            //add songs to list
            do {
                val songPath = musicCursor.getString(path)
                val songTitle = musicCursor.getString(titleColumn)
                val songArtist = musicCursor.getString(artistColumn)
                val songMusicType = musicCursor.getInt(musicTypeColumn)
                songList.addSong(Song(songPath, songTitle, songArtist), songMusicType)
            } while (musicCursor.moveToNext())
            musicCursor.close()
        }
        return songList
    }

    /**
     * check is our audio file are song
     */
    private fun MutableList<Song>.addSong(song: Song, musicType: Int) {
        if (musicType == SongInfo.SONG_TYPE_MUSIC) {
            add(song)
        }
    }

}