package com.example.firebase.ui.extrafragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentLandingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LandingFragment : Fragment(R.layout.fragment_landing) {

    private var _binding : FragmentLandingBinding? = null
    private var binding = _binding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLandingBinding.inflate(layoutInflater,container,false)
        return _binding!!.root
    }

    override fun onStart() {
        super.onStart()
        // Initialize Firebase Auth
        val auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser!=null)
        {
            navigateToChatFragment()
            onDestroy()
        }
        else
        {
            navigateToRegisterUser()
            onDestroy()
        }
    }

    private fun navigateToRegisterUser() {
        val action = LandingFragmentDirections.actionLandingFragmentToSignInFragment()
        findNavController().navigate(action)
    }

    private fun navigateToChatFragment() {
        val action = LandingFragmentDirections.actionLandingFragmentToChatFragment2()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}