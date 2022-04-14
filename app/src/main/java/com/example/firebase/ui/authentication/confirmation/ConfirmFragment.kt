package com.example.firebase.ui.authentication.confirmation

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebase.R
import com.example.firebase.databinding.FragmentConfirmationBinding
import com.example.firebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ConfirmFragment : Fragment(R.layout.fragment_confirmation){


    private var  _mBinding : FragmentConfirmationBinding? = null

    private val mBinding : FragmentConfirmationBinding
                        get() = _mBinding!!

    private lateinit var   auth : FirebaseAuth
    private lateinit  var mDbRef : DatabaseReference
    private lateinit var name : String
    private lateinit var number : String


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

            name = args.name
        val phoneNumber = args.phoneNumber
        val countryCode = args.countyCode
        val verificationId = args.storedVerificationId


        number  = countryCode + phoneNumber
        auth = Firebase.auth


        mBinding.tvMessage.text = phoneNumber


        mBinding.validateButton.setOnClickListener {


            val otp = mBinding.otpView.text.toString().trim()

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

                     addUserToDatabase(name,number,auth.currentUser?.uid!!)
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

    private fun addUserToDatabase(name: String, number: String, uid: String) {

        mDbRef = FirebaseDatabase.getInstance().getReference("Users")

        mDbRef.child(uid).setValue(User(name,number,uid))


    }

    override fun onDestroy() {
        super.onDestroy()
        _mBinding = null
    }
}