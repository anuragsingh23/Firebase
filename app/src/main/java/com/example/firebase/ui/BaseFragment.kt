package com.example.firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.firebase.databinding.FragmentBaseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseFragment : Fragment() {

    private var _binding : FragmentBaseBinding? = null
     private var binding = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBaseBinding.inflate(layoutInflater,container,false)



        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Initialize Firebase Auth
        val auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser!=null){
            navigateToMainFragment()
            onDestroy()
        }
        else{
            navigateToRegisteUser()
            onDestroy()

        }
    }
    fun navigateToRegisteUser(){
        val action = BaseFragmentDirections.actionBaseFragment2ToSignInFragment()
        findNavController().navigate(action)
    }
    fun navigateToMainFragment(){
       val action= BaseFragmentDirections.actionBaseFragment2ToMainFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}