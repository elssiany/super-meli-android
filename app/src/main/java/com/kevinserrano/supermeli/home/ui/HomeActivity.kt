package com.kevinserrano.supermeli.home.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kevinserrano.supermeli.databinding.ActivityHomeBinding
import com.kevinserrano.supermeli.search.ui.SearchActivity
import com.kevinserrano.supermeli.utils.animationForSplashScreen
import com.kevinserrano.supermeli.utils.openWebPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setOnExitAnimationListener { splashScreenView ->
            animationForSplashScreen(onExitAnimation = {
                initContentView()
            }, splashScreenView = splashScreenView)
        }
    }

    private fun initContentView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.imgProfile.setOnClickListener {
            openWebPage(LINKEDIN_URL)
        }
    }
}

const val LINKEDIN_URL = "https://www.linkedin.com/in/kevin-serrano-m/"