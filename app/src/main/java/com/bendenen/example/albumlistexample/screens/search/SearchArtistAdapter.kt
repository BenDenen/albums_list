package com.bendenen.example.albumlistexample.screens.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bendenen.example.albumlistexample.R
import com.bendenen.example.albumlistexample.models.Artist
import com.bendenen.example.albumlistexample.models.ImageDescription
import com.squareup.picasso.Picasso

class SearchArtistAdapter(context: Context) : RecyclerView.Adapter<SearchArtistAdapter.ArtistViewHolder>() {

    private val dataList = ArrayList<Artist>()

    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder =
        ArtistViewHolder(layoutInflater.inflate(R.layout.artist_item, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.artist = dataList[position]
    }

    fun setData(newData: List<Artist>) {
        val postDiffCallback = PostDiffCallback(dataList, newData)
        val diffResult = DiffUtil.calculateDiff(postDiffCallback)

        dataList.clear()
        dataList.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getDataList(): List<Artist> = dataList

    internal inner class PostDiffCallback(
        private val oldArtistList: List<Artist>,
        private val newArtisiList: List<Artist>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldArtistList.size
        }

        override fun getNewListSize(): Int {
            return newArtisiList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldArtistList[oldItemPosition].mbid === newArtisiList[newItemPosition].mbid
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldArtistList[oldItemPosition] == newArtisiList[newItemPosition]
        }
    }

    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val artistImage: ImageView = itemView.findViewById(R.id.artist_image)
        private val artistName: TextView = itemView.findViewById(R.id.artist_name)
        private val picasso = Picasso.with(itemView.context).also { it.setLoggingEnabled(true); }

        private val imageSize = itemView.context.resources.getDimensionPixelSize(R.dimen.saved_album_image_size)

        var artist: Artist? = null
            set(value) {
                field = value

                fun Artist.getListImageUrl(): String? {
                    var imageUrl = this.image.firstOrNull { it.size == ImageDescription.SizeType.LARGE }?.text
                    if (imageUrl == null) {
                        imageUrl =
                                this.image.firstOrNull { it.size == ImageDescription.SizeType.EXTRA_LARGE }?.text
                    }
                    if (imageUrl != null && imageUrl.trim().isEmpty()) {
                        imageUrl = null
                    }
                    return imageUrl
                }
                picasso
                    .load(artist?.getListImageUrl())
                    .error(R.drawable.ic_no_image_available)
                    .placeholder(R.drawable.ic_no_image_available)
                    .resize(imageSize, imageSize)
                    .centerInside()
                    .into(artistImage)

                artistName.text = artist?.name ?: itemView.context.getString(R.string.undefined)
            }


    }
}