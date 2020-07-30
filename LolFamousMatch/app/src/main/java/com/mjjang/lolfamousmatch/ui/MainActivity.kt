package com.mjjang.lolfamousmatch.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.google.android.gms.ads.MobileAds
import com.mjjang.lolfamousmatch.R
import com.mjjang.lolfamousmatch.databinding.ActivityMainBinding
import com.mjjang.lolfamousmatch.manager.DialogManager
import com.mjjang.lolfamousmatch.utilities.VersionManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )

        DialogManager.showLoadingDialog(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onStart() {
        super.onStart()
    }
}