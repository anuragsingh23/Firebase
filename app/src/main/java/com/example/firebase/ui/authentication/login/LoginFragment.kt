
package com.example.firebase.ui.authentication.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.firebase.R
import com.example.firebase.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login){


    private lateinit var  viewModel : LoginViewModel
    private var binding : FragmentLoginBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}