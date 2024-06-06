package com.example.newsapp.data.core

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ImageBindingAdapter {
    @BindingAdapter("imageByteArray")
    @JvmStatic
    fun setImageFromByteArray(imageView: ImageView, byteArray: ByteArray?) {
        byteArray?.let {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            imageView.setImageBitmap(bitmap)
        }
    }
}