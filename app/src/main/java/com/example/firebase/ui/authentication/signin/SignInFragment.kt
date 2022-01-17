package com.example.firebase.ui.authentication.signin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentSigninBinding

class SignInFragment : Fragment(R.layout.fragment_signin) {

    private var binding: FragmentSigninBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.btnLogin?.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToLoginFragment2())
        }

    }



    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}