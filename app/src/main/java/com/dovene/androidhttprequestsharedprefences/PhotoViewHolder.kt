package com.dovene.androidhttprequestsharedprefences

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var photoIV: ImageView
    private var authorTV: TextView
    init {
        photoIV = itemView.findViewById(R.id.photo_iv)
        authorTV = itemView.findViewById(R.id.author_tv)
    }

    fun bind(photo: Photos
    ) {
        authorTV.text = photo.photographer

        Glide.with(photoIV.context).load(photo.src?.original)
            .into(photoIV)
    }
}


