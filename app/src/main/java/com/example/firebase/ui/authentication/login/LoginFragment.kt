
package com.example.firebase.ui.authentication.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.FragmentLoginBinding
import com.example.firebase.ui.authentication.signin.SignInFragmentDirections
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment(R.layout.fragment_login){


    private lateinit var  viewModel : LoginViewModel
    private var _binding : FragmentLoginBinding? = null
    private val binding  : FragmentLoginBinding
                get() = _binding!!

    private lateinit var   auth : FirebaseAuth
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var phoneNumber : String
    lateinit var number : String
    lateinit var countryCode : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentLoginBinding.inflate(inflater,container,false)

        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {

            phoneNumber = binding.etMobile.text.toString().trim { it <= ' ' }
            countryCode = binding.etCountryCode.text.toString().trim { it <= ' ' }

            number = countryCode+phoneNumber

            when {
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

                storedVerificationId = verifyId


                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToConfirmFragment(

                    countryCode,
                    phoneNumber,
                    storedVerificationId!!))

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




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}