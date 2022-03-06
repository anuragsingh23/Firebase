package com.example.firebase.ui.authentication.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
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

        val name = arguments?.getString("name")
        val phoneNumber = arguments?.getString("phoneNumber")
        val countryCode = arguments?.getString("countryCode")
        val verificationId = arguments?.getString("verificationId")


        auth = Firebase.auth

        val number = countryCode + phoneNumber
        mBinding.tvMessage.text = number
        mBinding.tvOtp.text = verificationId

        mBinding.validateButton.setOnClickListener {

            val action = R.id.action_confirmFragment_to_chatFragment2
            findNavController().navigate(action )
          /*  val otp = mBinding.otpView.toString().trim()

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
                if (verificationId != null){
                    val credential = PhoneAuthProvider.getCredential(verificationId, otp)
                    signInWithPhoneAuthCredential(credential)
                }

            }
      */


        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)

            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    val user = task.result?.user
                    val bundle=bundleOf(
                        "user" to user
                    )
                    val action = R.id.action_confirmFragment_to_chatFragment2
                    findNavController().navigate(action , bundle)
                        onDestroy()
                } else {
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