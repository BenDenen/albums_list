package com.bendenen.example.albumlistexample.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendenen.example.albumlistexample.R
import com.bendenen.example.albumlistexample.screens.main.viewmodel.MainViewModel
import com.bendenen.example.albumlistexample.utils.ResourceObserver
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
        val adapter = SavedAlbumsAdapter(this)
        album_list.adapter = adapter

        mainViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java).also { it ->
                it.albums.observe(
                    this, ResourceObserver(
                        "MainActivity",
                        hideLoading = ::hideLoading,
                        showLoading = ::showLoading,
                        onSuccess = {
                            adapter.setData(it)
                        },
                        onError = ::showErrorMessage
                    )
                )

            }
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
