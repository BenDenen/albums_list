package com.bendenen.example.albumlistexample.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(ImageDescription.Converters::class)
data class Album(
    @PrimaryKey val mbid: String,
    val name: String,
    val artist: String,
    val image: List<ImageDescription>,
    val url: String,
    val streamable: Int
)