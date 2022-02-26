package com.example.firebase.ui.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.firebase.R
import com.example.firebase.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main){


    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =  FragmentMainBinding.inflate(layoutInflater,container,false)


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}