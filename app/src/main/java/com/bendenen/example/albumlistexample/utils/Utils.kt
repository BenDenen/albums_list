package com.bendenen.example.albumlistexample.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.net.ConnectivityManager
import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Utils @Inject constructor(private val context: Context) {

    fun hasConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isAvailable && activeNetwork.isConnected
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }

}