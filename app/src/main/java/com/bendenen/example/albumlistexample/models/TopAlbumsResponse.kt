package com.bendenen.example.albumlistexample.models

data class TopAlbumsResponse(
    val topalbums: TopAlbums
) {
    data class TopAlbums(
        val album: List<Album>
    )
}