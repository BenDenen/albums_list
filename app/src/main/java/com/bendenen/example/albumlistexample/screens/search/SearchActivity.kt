package com.bendenen.example.albumlistexample.screens.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bendenen.example.albumlistexample.R
import dagger.android.AndroidInjection

class SearchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }

    }
}