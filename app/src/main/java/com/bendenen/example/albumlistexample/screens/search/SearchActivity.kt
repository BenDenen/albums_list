package com.bendenen.example.albumlistexample.screens.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bendenen.example.albumlistexample.R
import com.bendenen.example.albumlistexample.screens.main.viewmodel.MainViewModel
import com.bendenen.example.albumlistexample.screens.search.viewmodel.SearchViewModel
import com.bendenen.example.albumlistexample.utils.ResourceObserver
import com.bendenen.example.albumlistexample.utils.extensions.hide
import com.bendenen.example.albumlistexample.utils.extensions.show
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        artisi_list.layoutManager = GridLayoutManager(this,2) as RecyclerView.LayoutManager?
        val adapter = SearchArtistAdapter(this)
        artisi_list.adapter = adapter

        searchViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SearchViewModel::class.java).also { it ->
                it.searchResponse.observe(
                    this, ResourceObserver(
                        "SearchActivity",
                        hideLoading = ::hideLoading,
                        showLoading = ::showLoading,
                        onSuccess = {
                            val artists = it.results.artistmatches.artist
                            if (artists.isNullOrEmpty() ) {
                                Log.e("MyTag", "Null")
                                artisi_list.hide()
                                empty_list.show()
                                return@ResourceObserver
                            }
                            Log.e("MyTag", "Not Null")
                            adapter.setData(artists)
                            empty_list.hide()
                        },
                        onError = ::showErrorMessage
                    )
                )

            }

        search_button.setOnClickListener {
            val text = search_view.text.toString()
            if(text.isEmpty()) {
                Toast.makeText(this, R.string.search_empty_error, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            searchViewModel.artistName.value = text
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

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }

    }
}