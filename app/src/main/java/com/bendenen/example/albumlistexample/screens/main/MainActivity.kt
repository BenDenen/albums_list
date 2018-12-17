package com.bendenen.example.albumlistexample.screens.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendenen.example.albumlistexample.R
import com.bendenen.example.albumlistexample.screens.AlbumsAdapter
import com.bendenen.example.albumlistexample.screens.main.viewmodel.MainViewModel
import com.bendenen.example.albumlistexample.screens.search.SearchActivity
import com.bendenen.example.albumlistexample.utils.ResourceObserver
import com.bendenen.example.albumlistexample.utils.extensions.hide
import com.bendenen.example.albumlistexample.utils.extensions.show
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        album_list.layoutManager = LinearLayoutManager(this)
        val adapter = AlbumsAdapter(this)
        album_list.adapter = adapter

        mainViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java).also { it ->
                it.albums.observe(
                    this, ResourceObserver(
                        "MainActivity",
                        hideLoading = ::hideLoading,
                        showLoading = ::showLoading,
                        onSuccess = {
                            if (it.isNullOrEmpty()) {
                                album_list.hide()
                                empty_list.show()
                                return@ResourceObserver
                            }
                            adapter.setData(it)
                            album_list.show()
                            empty_list.hide()

                        },
                        onError = ::showErrorMessage
                    )
                )

            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                startActivity(SearchActivity.createIntent(this))
        }
        return true
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
}
