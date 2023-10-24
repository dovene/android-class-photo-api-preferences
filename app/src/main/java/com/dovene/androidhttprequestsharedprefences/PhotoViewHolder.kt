package com.dovene.androidhttprequestsharedprefences

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var photoIV: ImageView
    private var authorTV: TextView
    private var containerCL: ConstraintLayout
    private var bookmark: ImageView

    init {
        photoIV = itemView.findViewById(R.id.photo_iv)
        authorTV = itemView.findViewById(R.id.author_tv)
        containerCL = itemView.findViewById(R.id.container)
        bookmark = itemView.findViewById(R.id.bookmark)
    }

    fun bind(photo: Photos, photoItemCallback: PhotoItemCallback
    ) {
        authorTV.text = photo.photographer
        Glide.with(photoIV.context).load(photo.src?.original)
            .into(photoIV)
        containerCL.setOnClickListener {
            photoItemCallback.onCellClick(photo)
        }
        bookmark.setOnClickListener {
            photoItemCallback.onSavePhoto(photo)
        }
    }
}


