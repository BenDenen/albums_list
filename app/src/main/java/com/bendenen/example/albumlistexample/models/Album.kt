package com.bendenen.example.albumlistexample.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(ImageDescription.Converters::class)
data class Album(
    val mbid: String? = null,
    @PrimaryKey val name: String,
    @Embedded(prefix = "artist_")
    val artist: ArtistView,
    val image: List<ImageDescription>,
    val url: String
) {
    data class ArtistView(
        val name: String,
        val mbid: String,
        val url: String
    )
}