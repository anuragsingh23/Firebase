package com.example.firebase.ui.authentication.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentSigninBinding

class SignInFragment : Fragment(R.layout.fragment_signin) {

    private val viewModel: SigninViewModel by viewModels()

    private var binding: FragmentSigninBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSigninBinding.inflate(inflater,container,false)

         binding?.btnLogin?.setOnClickListener{
             NaviigateTOLogin()
         }
        binding?.btnRegister?.setOnClickListener {
            findNavController()
                .navigate(R.id.action_signInFragment_to_confirmFragment)
        }
        return binding?.root
    }


    private fun NaviigateTOLogin() {
        findNavController()
            .navigate(R.id.action_signInFragment_to_loginFragment)
    }



    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}