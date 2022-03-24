package com.example.firebase.ui.chat

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.firebase.R
import com.example.firebase.databinding.ActivityMainBinding
import com.example.firebase.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId){
            R.id.action_friends_fragment -> {
                updateUI()
                true
            }
           R.id.action_logout ->{
                signOut()
                true
           }
           else ->
               super.onOptionsItemSelected(item)
        }

    }

    fun updateUI(){
        val action = ChatFragmentDirections.actionChatFragment2ToFriendsFragment()
        findNavController().navigate(action)
    }
    private fun signOut() {
        auth.signOut()
        updateUi()
    }

    private fun updateUi() {
        val action = ChatFragmentDirections.actionChatFragment2ToSignInFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}