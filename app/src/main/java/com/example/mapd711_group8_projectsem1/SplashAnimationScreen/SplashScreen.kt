package com.example.mapd711_group8_projectsem1.SplashAnimationScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import com.example.mapd711_group8_projectsem1.R
import com.example.mapd711_group8_projectsem1.UserAuthenticationFirebase.SignUpActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        hideStatusBar()

        val backgroundImage = findViewById<ImageView>(R.id.splashscreenImage)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.logoanimation)
        backgroundImage.startAnimation(slideAnimation)




        Handler().postDelayed({ startActivity(Intent(this@SplashScreen, SignUpActivity::class.java))
        finish()}, 3500)
    }

    private fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}