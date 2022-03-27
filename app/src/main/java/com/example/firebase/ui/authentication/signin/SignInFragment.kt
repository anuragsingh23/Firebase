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
import com.example.firebase.model.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class SignInFragment : Fragment(R.layout.fragment_signin) {

    private val viewModel: SigninViewModel by viewModels()

    private var _binding: FragmentSigninBinding? = null
    private val binding : FragmentSigninBinding
                      get() = _binding!!

    private lateinit var   auth : FirebaseAuth
    private lateinit var mDbref : DatabaseReference
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    lateinit var ResendToken : PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var name : String
    lateinit var phoneNumber : String
    lateinit var number : String
    lateinit var countryCode : String





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSigninBinding.inflate(inflater,container,false)

        auth = Firebase.auth





        binding.btnRegister.setOnClickListener {


             name = binding.etName.text.toString().trim { it <= ' ' }
             phoneNumber = binding.etMobile.text.toString().trim { it <= ' ' }
             countryCode = binding.etCountryCode.text.toString().trim { it <= ' ' }

             number = countryCode+phoneNumber
          /*  if (!checkExistingUser(number) ){

                Toast.makeText(
                    this.activity,
                    "number already registered",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } */

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

       // binding.btnLogin.setOnClickListener { navigateTOLogin() }

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
                ResendToken = token

               findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToConfirmFragment(
                    name,
                    countryCode,
                    phoneNumber,
                    storedVerificationId!!))

            }

        }

        return binding.root
    }

    private fun checkExistingUser(phone : String){
        mDbref = FirebaseDatabase.getInstance().getReference("users")
        mDbref.orderByChild("phoneNumber").equalTo(phone).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.getValue() != null){
                    Toast.makeText(
                        activity,
                        "user already register",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                else{

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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