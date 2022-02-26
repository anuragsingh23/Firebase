package com.example.firebase.ui.authentication.signin

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentSigninBinding

class SignInFragment : Fragment(R.layout.fragment_signin) {

    private val viewModel: SigninViewModel by viewModels()

  private var _binding : FragmentSigninBinding? = null
    private val binding  get() = _binding!!






    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSigninBinding.inflate(layoutInflater, container ,false)
        binding.btnLogin.setOnClickListener{
             NavigateTOLogin()
         }

        binding.btnRegister.setOnClickListener {

            val name = binding.etName.text.toString().trim { it <= ' ' }
            val phoneNumber =  binding.etMobile.text.toString().trim { it <= ' ' }
            val countryCode = binding.etCountryCode.text.toString().trim{ it <= ' '}

            when{
                TextUtils.isEmpty(name) -> {
                    Toast.makeText(
                        this.activity,
                        resources.getString(R.string.err_msg_select_name),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(phoneNumber) -> {
                    Toast.makeText(
                        this.activity,
                        resources.getString(R.string.err_msg_select_phone_number),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(countryCode) -> {
                    Toast.makeText(
                        this.activity,
                        resources.getString(R.string.err_msg_select_country_code),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{
                    findNavController()
                        .navigate(R.id.action_signInFragment_to_confirmFragment)
                }
            }
        }

        return binding.root
    }



    private fun NavigateTOLogin() {
        findNavController()
            .navigate(R.id.action_signInFragment_to_loginFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}