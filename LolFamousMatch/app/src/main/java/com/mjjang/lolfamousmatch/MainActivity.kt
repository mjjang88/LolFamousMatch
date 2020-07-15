package com.mjjang.lolfamousmatch

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.mjjang.lolfamousmatch.databinding.ActivityMainBinding
import com.mjjang.lolfamousmatch.databinding.FragmentSignInBinding
import com.mjjang.lolfamousmatch.utilities.AuthManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // firebase 인증
        AuthManager.init(this)

        // Admob 초기화
        MobileAds.initialize(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = AuthManager.auth.currentUser
        Toast.makeText(this, "onStart : 인증 email : ${currentUser?.email}", Toast.LENGTH_SHORT).show()
    }
}