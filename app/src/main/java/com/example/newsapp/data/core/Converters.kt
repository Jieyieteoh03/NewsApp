package com.example.newsapp.data.core
import androidx.room.TypeConverter
import java.net.URL

class Converters {
    @TypeConverter
    fun fromUrl(url: URL?): String? {
        return url?.toString()
    }

    @TypeConverter
    fun toUrl(urlString: String?): URL? {
        return if (urlString.isNullOrEmpty()) {
            null
        } else try {
            URL(urlString)
        } catch (e: Exception) {
            null
        }
    }
}