package com.example.newsapp.data.core

import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import java.net.MalformedURLException
import java.net.URL

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