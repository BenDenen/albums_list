package com.bendenen.example.albumlistexample.screens.topalbums

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bendenen.example.albumlistexample.R
import com.bendenen.example.albumlistexample.screens.AlbumsAdapter
import com.bendenen.example.albumlistexample.screens.topalbums.viewmodel.TopAlbumsViewModel
import com.bendenen.example.albumlistexample.utils.ItemClickSupport
import com.bendenen.example.albumlistexample.utils.ResourceObserver
import com.bendenen.example.albumlistexample.utils.extensions.hide
import com.bendenen.example.albumlistexample.utils.extensions.show
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class TopAlbumsActivity : AppCompatActivity() {

    private lateinit var topAlbumsViewModel: TopAlbumsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_albums)

        val artistId = intent.getStringExtra(ARTIST_ID_KEY)
        if (artistId.isNullOrEmpty()) {
            Log.e(TAG, "Artist Id should be provided")
            finish()
            return
        }

        album_list.layoutManager = LinearLayoutManager(this)
        val adapter = AlbumsAdapter(this)
        album_list.adapter = adapter

        topAlbumsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TopAlbumsViewModel::class.java).also { it ->
                it.topAlbumsResponse.observe(
                    this, ResourceObserver(
                        "MainActivity",
                        hideLoading = ::hideLoading,
                        showLoading = ::showLoading,
                        onSuccess = {
                            val albums = it.topalbums.album
                            if (albums.isNullOrEmpty()) {
                                album_list.hide()
                                empty_list.show()
                                return@ResourceObserver
                            }
                            adapter.setData(albums)
                            album_list.show()
                            empty_list.hide()

                        },
                        onError = ::showErrorMessage
                    )
                )

            }
        topAlbumsViewModel.artistId.value = artistId

        ItemClickSupport
            .addTo(album_list)
            .setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                    val item = adapter.getDataList()[position]

                }
            })
            .setOnItemLongClickListener(object : ItemClickSupport.OnItemLongClickListener {
                override fun onItemLongClicked(
                    recyclerView: RecyclerView,
                    position: Int, v: View
                ): Boolean {
                    val album = adapter.getDataList()[position]

                    val alertDialog = AlertDialog
                        .Builder(this@TopAlbumsActivity)
                        .create().also {
                            it.setTitle(R.string.top_albums_delete_title)
                            it.setMessage(getString(R.string.top_albums_delete_text, album.name))
                            it.setButton(
                                AlertDialog.BUTTON_POSITIVE, getString(R.string.top_albums_delete_ok)
                            ) { dialog, which ->
                                topAlbumsViewModel.deleteData =
                                        TopAlbumsViewModel.DeleteData(album.artist.mbid, album.name)
                                topAlbumsViewModel.artistId.value = artistId
                            }
                        }

                    alertDialog.show()

                    return true
                }

            })
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressbar.visibility = View.GONE
    }

    companion object {

        const val ARTIST_ID_KEY = "artist_id"
        const val TAG = "TopAlbumsActivity"

        fun createIntent(context: Context, artistId: String): Intent {
            return Intent(context, TopAlbumsActivity::class.java).also {
                it.putExtra(ARTIST_ID_KEY, artistId)
            }
        }

    }

}