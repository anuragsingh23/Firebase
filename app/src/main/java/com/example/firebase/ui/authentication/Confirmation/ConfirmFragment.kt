package com.example.firebase.ui.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.firebase.R
import com.example.firebase.databinding.FragmentConfirmationBinding

class ConfirmFragment : Fragment(R.layout.fragment_confirmation){

    private var _binding : FragmentConfirmationBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ? {
        _binding = FragmentConfirmationBinding.inflate(layoutInflater,container,false)


        binding.btnValidate.setOnClickListener {
            navigateToChat()
        }

        return binding.root

    }

    private fun navigateToChat() {
        val action =  ConfirmFragmentDirections.actionConfirmFragmentToMainFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}