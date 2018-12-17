package com.bendenen.example.albumlistexample.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Json

data class ImageDescription(
    @Json(name = "#text")
    val text: String,
    val size: SizeType
) {
    enum class SizeType(val serverKey: String) {

        @Json(name = SMALL_KEY)
        SMALL(SizeType.SMALL_KEY),

        @Json(name = MEDIUM_KEY)
        MEDIUM(SizeType.MEDIUM_KEY),

        @Json(name = LARGE_KEY)
        LARGE(SizeType.LARGE_KEY),

        @Json(name = EXTRA_LARGE_KEY)
        EXTRA_LARGE(SizeType.EXTRA_LARGE_KEY),

        @Json(name = MEGA_KEY)
        MEGA(SizeType.MEGA_KEY);


        companion object {
            const val SMALL_KEY = "small"
            const val MEDIUM_KEY = "medium"
            const val LARGE_KEY = "large"
            const val EXTRA_LARGE_KEY = "extralarge"
            const val MEGA_KEY = "mega"
        }
    }

    class Converters {

        @TypeConverter
        fun fromImageDescriptionList(image: List<ImageDescription>): String = Gson().toJson(image)

        @TypeConverter
        fun fromImageDescriptionString(value: String): List<ImageDescription> {
            val listType = object : TypeToken<ArrayList<ImageDescription>>() {}.type
            return Gson().fromJson(value, listType)
        }

    }
}