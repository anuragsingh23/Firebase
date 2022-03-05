package com.example.firebase.ui.authentication.signin

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentSigninBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class SignInFragment : Fragment(R.layout.fragment_signin) {

    private val viewModel: SigninViewModel by viewModels()

    private var _binding: FragmentSigninBinding? = null
    private val binding : FragmentSigninBinding
                      get() = _binding!!

    private lateinit var   auth : FirebaseAuth
    private var verificationInProgress = false
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSigninBinding.inflate(inflater,container,false)

        auth = Firebase.auth



        binding.btnRegister.setOnClickListener {

            val name = binding.etName.text.toString().trim { it <= ' ' }
            val phoneNumber = binding.etMobile.text.toString().trim { it <= ' ' }
            val countryCode = binding.etCountryCode.text.toString().trim { it <= ' ' }



            val number = countryCode+phoneNumber
            when {
                TextUtils.isEmpty(name) -> {
                    Toast.makeText(
                        this.activity,
                        resources.getString(R.string.inset_name),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(phoneNumber) -> {
                    Toast.makeText(
                        this.activity,
                        resources.getString(R.string.select_phone_number),
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
                else -> {
                    sendOtp(number)
                }
            }



            }

        binding.btnLogin.setOnClickListener { navigateTOLogin() }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(
                    activity,
                    p0.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCodeSent(verifyId: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verifyId, token)

                val bundle = bundleOf(
                    "verifyId" to verifyId ,
                    "token" to token ,
                    "name" to binding.etName.toString().trim(),
                    "phoneNumber" to binding.etMobile.toString().trim(),
                    "countryCode" to binding.etCountryCode.toString().trim()

                )
                findNavController()
                    .navigate(R.id.action_signInFragment_to_confirmFragment , bundle)
            }

        }

        return binding.root
    }

    private fun sendOtp(number : String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        verificationInProgress = true
    }



    private fun navigateTOLogin() {
        findNavController()
            .navigate(R.id.action_signInFragment_to_loginFragment)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}