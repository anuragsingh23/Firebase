
package com.example.firebase.ui.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login){


    private lateinit var  viewModel : LoginViewModel
    private var binding : FragmentLoginBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding?.btnLogin?.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToConfirmFragment())
            }

        return binding!!.root
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}