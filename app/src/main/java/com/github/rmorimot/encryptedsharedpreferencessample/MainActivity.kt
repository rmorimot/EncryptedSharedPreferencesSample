package com.github.rmorimot.encryptedsharedpreferencessample

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences()
        val editor = preferences.edit()
        editor.putString("hoge", "hoge")
        editor.apply()
    }

    private fun getSharedPreferences()  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } else {
        // Android5系以下では利用できないので独自に暗号化(ここでは単にSharedPreferencesを利用)
        applicationContext.getSharedPreferences(
            "secret_shared_prefs",
            Context.MODE_PRIVATE
        )
    }
}
