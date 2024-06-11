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
//    @JvmStatic
//    @BindingAdapter("android:text")
//    fun setUrlText(view: EditText, value: URL?) {
//        view.setText(value?.toString() ?: "")
//    }
//
//    @JvmStatic
//    @BindingAdapter("android:textAttrChanged")
//    fun setListeners(view: EditText, attrChange: InverseBindingListener) {
//        view.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable?) {
//                attrChange.onChange()
//            }
//        })
//    }
//
//    @JvmStatic
//    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
//    fun getUrlText(view: EditText): URL? {
//        return try {
//            URL(view.text.toString())
//        } catch (e: MalformedURLException) {
//            null
//        }
//    }
}