package com.mjjang.lolfamousmatch.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.mjjang.lolfamousmatch.databinding.FragmentSignInBinding
import com.mjjang.lolfamousmatch.utilities.AuthManager

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignInBinding.inflate(inflater, container, false)

        context ?: return binding.root

        binding.buttonSignIn.setOnClickListener {
            signIn()
        }

        return binding.root
    }

    private fun signIn() {
        val signInIntent = AuthManager.googleSignInClient.signInIntent
        startActivityForResult(signInIntent, AuthManager.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AuthManager.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Auth", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Auth", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        AuthManager.auth.signInWithCredential(credential)
            .addOnCompleteListener {  task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Auth", "signInWithCredential:success")
                    val user = AuthManager.auth.currentUser
                    Toast.makeText(activity, "인증성공! email : ${user?.email}", Toast.LENGTH_SHORT).show()

                    view?.findNavController()?.navigateUp()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "signInWithCredential:failure", task.exception)
                }
            }
    }
}