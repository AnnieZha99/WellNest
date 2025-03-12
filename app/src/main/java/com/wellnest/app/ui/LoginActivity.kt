package com.wellnest.app.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wellnest.app.R
import com.wellnest.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Set up click listeners
        binding.loginButton.setOnClickListener { attemptLogin() }
        binding.registerLink.setOnClickListener { navigateToRegistration() }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToMainActivity()
        }
    }

    private fun attemptLogin() {
        // Reset errors
        binding.emailLayout.error = null
        binding.passwordLayout.error = null

        // Store values at the time of the login attempt
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            binding.passwordLayout.error = getString(R.string.invalid_password)
            focusView = binding.password
            cancel = true
        }

        // Check for a valid email address
        if (TextUtils.isEmpty(email)) {
            binding.emailLayout.error = getString(R.string.invalid_email)
            focusView = binding.email
            cancel = true
        } else if (!isEmailValid(email)) {
            binding.emailLayout.error = getString(R.string.invalid_email)
            focusView = binding.email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first form field with an error
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt
            showProgress(true)
            signInWithEmailAndPassword(email, password)
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                showProgress(false)
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    navigateToMainActivity()
                } else {
                    // If sign in fails, display a message to the user
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, getString(R.string.login_failed),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showProgress(show: Boolean) {
        binding.loading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun navigateToRegistration() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close the login activity so the user can't go back
    }
} 