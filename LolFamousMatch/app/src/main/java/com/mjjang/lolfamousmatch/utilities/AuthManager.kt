package com.mjjang.lolfamousmatch.utilities

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.mjjang.lolfamousmatch.R

object AuthManager {

    val RC_SIGN_IN = 1000
    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    fun init(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)

        auth = FirebaseAuth.getInstance()
    }

    fun isLogin() : Boolean {
        return auth.currentUser != null
    }
}