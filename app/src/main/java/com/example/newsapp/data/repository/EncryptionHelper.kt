package com.example.newsapp.data.repository

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.coroutines.ContinuationInterceptor

object EncryptionHelper {
    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val KEY_ALIAS = "newsAppKey"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val KEY_SIZE = 256

    init {
        generateKey()
    }

    private fun generateKey() {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(KEY_SIZE)
                    .build()
            )
            keyGenerator.generateKey()
        }
    }

    private fun getKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        return keyStore.getKey(KEY_ALIAS, null) as SecretKey
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        val ivAndEncrypted = iv + encryptedBytes
        return Base64.encodeToString(ivAndEncrypted, Base64.DEFAULT)
    }

    fun decrypt(encrypted: String): String? {
        val key = getKey()
        return if (key != null) {
            val ivAndEncrypted = Base64.decode(encrypted, Base64.DEFAULT)
            val iv = ivAndEncrypted.sliceArray(0 until 12)
            val encryptedBytes = ivAndEncrypted.sliceArray(12 until ivAndEncrypted.size)
            val cipher = Cipher.getInstance(ALGORITHM)
            val spec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, key, spec)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            String(decryptedBytes)
        } else {
            null
        }
    }

}