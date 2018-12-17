package com.bendenen.example.albumlistexample.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(ImageDescription.Converters::class)
data class Artist(
    @PrimaryKey val mbid: String,
    val name: String,
    val url: String,
    val image: List<ImageDescription>
)
