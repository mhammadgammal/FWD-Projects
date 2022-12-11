package com.udacity.project4.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.udacity.project4.R
import com.udacity.project4.locationreminders.RemindersActivity

/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RemindersActivity.
 */
class AuthenticationActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "AuthenticationActivity"
        const val SIGN_IN_RESULT_CODE = 1001
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        val loginBTN = findViewById<Button>(R.id.loginBTN)
        loginBTN.setOnClickListener {
            signInFlow()
        }
        if(FirebaseAuth.getInstance().currentUser != null)
        {
            startActivity(Intent(this, RemindersActivity::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == SIGN_IN_RESULT_CODE) {
            IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK)
                Log.d(TAG, "onActivityResult: success")
            else
                Log.d(TAG, "onActivityResult: un-success")
        }
    }

    private fun signInFlow() {
        val authenticateProviders = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(authenticateProviders)
                .build(),
            SIGN_IN_RESULT_CODE
        )
    }
}
