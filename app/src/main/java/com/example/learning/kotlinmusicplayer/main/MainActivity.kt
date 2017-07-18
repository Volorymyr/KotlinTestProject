package com.example.learning.kotlinmusicplayer.main

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.RelativeLayout
import butterknife.bindView
import com.example.learning.kotlinmusicplayer.R
import com.example.learning.kotlinmusicplayer.adapter.TrackListAdapter
import com.example.learning.kotlinmusicplayer.model.Song
import com.example.learning.kotlinmusicplayer.player.state.PlayerState
import com.example.learning.kotlinmusicplayer.player.state.StopState
import com.example.learning.kotlinmusicplayer.service.MediaPlayerService
import com.example.learning.kotlinmusicplayer.task.LoadSongTask

class MainActivity : AppCompatActivity(), IMainView<MainPresenter>, ServiceConnection {

    val PERMISSION_REQUEST_CODE = 1
    val requestPermissionArray: Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    lateinit var mainPresenter: MainPresenter
    var trackListAdapter: TrackListAdapter? = null
    val trackListRecyclerView: RecyclerView by bindView(R.id.playlistRecyclerView)
    val playerControlPanel: RelativeLayout by bindView(R.id.playerControlPanel)

    private object Resources {
        var playerControlPanelHeight: Int = 0

        fun init(context: Context) {
            playerControlPanelHeight = context.resources.getDimensionPixelOffset(R.dimen.control_player_panel_height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //if android > 6 we need to permission for reading data from external storage
        //in other case just create presenter and start work
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
                && permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, requestPermissionArray, PERMISSION_REQUEST_CODE)
            return
        }

        initActivity()
    }

    private fun initActivity() {
        Resources.init(applicationContext)
        initPresenter()
        initTrackListView()
    }

    private fun initTrackListView() {
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this.applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        val dividerItem: DividerItemDecoration = DividerItemDecoration(this.applicationContext,
                linearLayoutManager.orientation)
        dividerItem.setDrawable(ContextCompat.getDrawable(this.applicationContext, R.drawable.vertical_divider))

        trackListRecyclerView.layoutManager = linearLayoutManager
        trackListRecyclerView.addItemDecoration(dividerItem)
    }

    private fun initPresenter() {
        mainPresenter = MainPresenter(this, LoadSongTask(applicationContext), loaderManager)
        mainPresenter.start()
    }

    override fun updatePlayListData(songs: List<Song>) {
        if (trackListAdapter == null) {
            trackListAdapter = TrackListAdapter(applicationContext, songs, mainPresenter)
            trackListRecyclerView.adapter = trackListAdapter
        } else {
            trackListAdapter?.notifyDataSetChanged()
        }
    }

    override fun changeControlPanelState(state: PlayerState) {
        when (state.TAG){
            StopState().TAG ->
                playerControlPanel.animate().translationY(Resources.playerControlPanelHeight.toFloat())
            else ->
                playerControlPanel.animate().translationY(-(Resources.playerControlPanelHeight.toFloat()))
        }
    }

    override fun onResume() {
        super.onResume()
        //TODO: in the future need to check if we already connected
        bindService(Intent(this.applicationContext, MediaPlayerService::class.java), this, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> initActivity()
            else -> this.finish()
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        mainPresenter.onDetachService()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        mainPresenter.onAttachService(service as MediaPlayerService.MusicBinder)
    }

}
