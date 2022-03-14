package com.example.firebase.ui.authentication.confirmation

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebase.R
import com.example.firebase.databinding.FragmentConfirmationBinding
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ConfirmFragment : Fragment(R.layout.fragment_confirmation){


    private var  _mBinding : FragmentConfirmationBinding? = null

    private val TAG = ""
    private val mBinding : FragmentConfirmationBinding
                        get() = _mBinding!!

    private lateinit var   auth : FirebaseAuth

   // private lateinit var verificationId: String

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


        val args : ConfirmFragmentArgs by navArgs()

        val name = args.name
        val phoneNumber = args.phoneNumber
        val countryCode = args.countyCode
        val verificationId = args.storedVerificationId


        auth = Firebase.auth


        mBinding.tvMessage.text = countryCode + phoneNumber


        mBinding.validateButton.setOnClickListener {


            val otp = mBinding.otpView.toString().trim()

            if (TextUtils.isEmpty(otp))
            {
                Toast.makeText(
                    this.activity,
                    resources.getString(R.string.inset_otp),
                    Toast.LENGTH_SHORT
                ).show()
            return@setOnClickListener
            }

            else {
                val credential = PhoneAuthProvider.getCredential(verificationId , otp)
                    signInWithPhoneAuthCredential(credential) }
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)

            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val action = R.id.action_confirmFragment_to_chatFragment2
                    findNavController().navigate(action )
                }
                else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        mBinding.otpView.error = "Invalid code."
                    }

                }
            }
    }
    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}