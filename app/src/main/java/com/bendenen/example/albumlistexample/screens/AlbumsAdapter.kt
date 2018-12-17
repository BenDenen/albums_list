package com.bendenen.example.albumlistexample.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bendenen.example.albumlistexample.R
import com.bendenen.example.albumlistexample.models.Album
import com.bendenen.example.albumlistexample.models.ImageDescription
import com.squareup.picasso.Picasso

class AlbumsAdapter(context: Context) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private val dataList = ArrayList<Album>()

    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            layoutInflater.inflate(
                R.layout.saved_album_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.album = dataList[position]
    }

    fun setData(newData: List<Album>) {
        val postDiffCallback = PostDiffCallback(dataList, newData)
        val diffResult = DiffUtil.calculateDiff(postDiffCallback)

        dataList.clear()
        dataList.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getDataList(): List<Album> = dataList

    internal inner class PostDiffCallback(
        private val oldAlbumList: List<Album>,
        private val newAlbumList: List<Album>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldAlbumList.size
        }

        override fun getNewListSize(): Int {
            return newAlbumList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldAlbumList[oldItemPosition].name === newAlbumList[newItemPosition].name)
                    && (oldAlbumList[oldItemPosition].artist.mbid === newAlbumList[newItemPosition].artist.mbid)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldAlbumList[oldItemPosition] == newAlbumList[newItemPosition]
        }
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val albumImage: ImageView = itemView.findViewById(R.id.album_image)
        private val albumName: TextView = itemView.findViewById(R.id.album_name)
        private val artistName: TextView = itemView.findViewById(R.id.artist_name)
        private val picasso = Picasso.with(itemView.context)

        private val imageSize = itemView.context.resources.getDimensionPixelSize(R.dimen.saved_album_image_size)

        var album: Album? = null
            set(value) {
                field = value

                fun Album.getListImageUrl(): String? {
                    var imageUrl = this.image.firstOrNull { it.size == ImageDescription.SizeType.MEDIUM }?.text
                    if (imageUrl == null) {
                        imageUrl = this.image.firstOrNull { it.size == ImageDescription.SizeType.LARGE }?.text
                    }
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
                    .load(album?.getListImageUrl())
                    .error(R.drawable.ic_no_image_available)
                    .placeholder(R.drawable.ic_no_image_available)
                    .resize(imageSize, imageSize)
                    .centerInside()
                    .into(albumImage)

                albumName.text = album?.name ?: itemView.context.getString(R.string.no_name)
                artistName.text = album?.artist?.name ?: itemView.context.getString(R.string.undefined)
            }


    }
}