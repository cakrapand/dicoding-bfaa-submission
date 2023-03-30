package com.example.githubuser.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubuser.R
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.ui.main.MainActivity
import com.example.githubuser.ui.main.MainViewModel

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstanceWithDataStore(application, dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageView: ImageView = findViewById(R.id.image_view)
        supportActionBar?.hide()

        mainViewModel.getThemeSetting().observe(this) {
            if (it) {
                imageView.setImageResource(R.mipmap.ic_launcher_dark_foreground)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                imageView.setImageResource(R.mipmap.ic_launcher_foreground)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }
}