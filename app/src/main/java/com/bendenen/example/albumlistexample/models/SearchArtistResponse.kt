package com.bendenen.example.albumlistexample.models

data class SearchArtistResponse(
    val results: Results
) {

    data class Results(
        val artistmatches: ArtistMatches
    ) {

        data class ArtistMatches(
            val artist: List<Artist>
        )
    }
}