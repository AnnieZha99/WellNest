package com.wellnest.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wellnest.app.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Set up user info
        val currentUser = auth.currentUser
        if (currentUser != null) {
            binding.userName.text = currentUser.displayName ?: "User"
            binding.userEmail.text = currentUser.email
        }

        // Set up button click listeners
        binding.savedLocationsButton.setOnClickListener {
            Toast.makeText(context, "Saved locations clicked", Toast.LENGTH_SHORT).show()
        }

        binding.healthPreferencesButton.setOnClickListener {
            Toast.makeText(context, "Health preferences clicked", Toast.LENGTH_SHORT).show()
        }

        binding.logoutButton.setOnClickListener {
            signOut()
        }

        return root
    }

    private fun signOut() {
        auth.signOut()
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
        
        // Navigate to login screen
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 