package com.example.learning.kotlinmusicplayer.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.learning.kotlinmusicplayer.R
import com.example.learning.kotlinmusicplayer.model.Song

class TrackListAdapter(val context: Context, songs: List<Song>, val trackListCallback: TrackListCallback?)
    : RecyclerView.Adapter<TrackListAdapter.SongItemViewHolder>() {
    private val EMPTY_LIST_COUNT: Int = 1
    private var playList: MutableList<Song> = songs as MutableList<Song>

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TrackListAdapter.SongItemViewHolder {
        when (playList.isEmpty()) {
            true -> return SongItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_play_list_empty, parent, false))
            else -> return SongItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_play_list, parent, false))
        }
    }

    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        if (playList.isEmpty()) return

        bindSong(holder, playList[position])

        holder.playListRoot?.setOnClickListener({
            selectItem(playList[position])
            trackListCallback?.onTrackListItemClick(position, playList[position].isSelected)
        })
    }


    private fun bindSong(holder: SongItemViewHolder, song: Song) {
        holder.songArtistTextView?.text = song.artist
        holder.songNameTextView?.text = song.title
        when (song.isSelected) {
            true -> {
                setSongViewsColors(holder, ContextCompat.getColor(context, R.color.colorPrimary),
                        ContextCompat.getColor(context, R.color.colorPrimary))
            }
            else -> {
                setSongViewsColors(holder, ContextCompat.getColor(context, R.color.colorBlack),
                        ContextCompat.getColor(context, R.color.colorPrimary))
            }
        }
    }

    private fun setSongViewsColors(holder: SongItemViewHolder, songNameTvColor: Int, songArtistTvColor: Int){
        holder.songNameTextView?.setTextColor(songNameTvColor)
        holder.songArtistTextView?.setTextColor(songArtistTvColor)
    }

    /**
     * @selectedSong, the item with song became selected
     * other will be deselected
     */
    private fun selectItem(selectedSong: Song) {
        playList.filter { it !== selectedSong }
                .forEach { it.isSelected = false }
        selectedSong.isSelected = !selectedSong.isSelected
        //Update UI
        //TODO: in the future make notifyItemChanged for previous and current selected items
        notifyDataSetChanged()
    }

    /**
     * If we don't found songs, show list item with
     * info text about empty list
     */
    override fun getItemCount(): Int {
        return if (playList.isEmpty()) EMPTY_LIST_COUNT else playList.size
    }

    class SongItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var songNameTextView: TextView? = itemView.findViewById(R.id.songNameTextView) as TextView?
        var songArtistTextView: TextView? = itemView.findViewById(R.id.songArtistTextView) as TextView?
        var playListRoot: LinearLayout? = itemView.findViewById(R.id.playListRoot) as LinearLayout?
    }
}