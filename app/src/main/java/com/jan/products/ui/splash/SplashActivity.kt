package com.jan.products.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.jan.products.ui.main.MainActivity
import com.jan.products.R
import com.jan.products.base.BaseActivity
import com.jan.products.data.preferences.SharedPreferencesManager
import com.jan.products.ui.login.LoginActivity

class SplashActivity : BaseActivity() {

    override fun layoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        Handler().postDelayed({
            if (SharedPreferencesManager.getExpirationDate(this@SplashActivity) != "-") {
                val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            } else {
                val tourIntent = Intent(this@SplashActivity, LoginActivity::class.java)
                tourIntent.putExtra("pageTour", "-")
                startActivity(tourIntent)
                finish()
            }
        }, SPLASH_TIME)
    }

    companion object {
        private const val SPLASH_TIME = 3000L
    }
}
