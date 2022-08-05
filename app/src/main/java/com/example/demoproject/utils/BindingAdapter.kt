package com.example.demoproject.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.demoproject.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.placeholder)
        .into(view)
}
