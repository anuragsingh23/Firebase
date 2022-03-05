package com.example.firebase.ui.authentication.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.firebase.R
import com.example.firebase.Utils.Constants
import com.example.firebase.Utils.Constants.MESSAGE
import com.example.firebase.databinding.FragmentConfirmationBinding

import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class ConfirmFragment : Fragment(R.layout.fragment_confirmation){


    private var  _mBinding : FragmentConfirmationBinding? = null

    private val TAG = ""
    private val mBinding : FragmentConfirmationBinding
                        get() = _mBinding!!

    private lateinit var   auth : FirebaseAuth
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentConfirmationBinding.inflate(layoutInflater,container,false)



        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let { onViewStateRestored(it) }

        val name = arguments?.getString("name")
        val phoneNumber = arguments?.getString("phoneNumber")
        val countryCode = arguments?.getString("countryCode")


        val number = countryCode + phoneNumber
        mBinding.tvMessage.text = "$phoneNumber"

        mBinding.validateButton.setOnClickListener {

            startPhoneNumberVerification(number)
            verifyPhoneNumberWithCode(storedVerificationId , mBinding.otpView.toString())

        }

        mBinding.resend.setOnClickListener {

            resendVerificationCode(number, resendToken )

        }

        auth = Firebase.auth



        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                TODO("Not yet implemented")

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")

            }

            override fun onCodeSent(verifyid: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verifyid, token)

                storedVerificationId = verifyid
                resendToken = token
            }

        }
    }

    private fun startPhoneNumberVerification(number : String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        verificationInProgress = true
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        mBinding.otpView.error = "Invalid code."
                    }

                }
            }
    }



    private fun resendVerificationCode(
        number: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}