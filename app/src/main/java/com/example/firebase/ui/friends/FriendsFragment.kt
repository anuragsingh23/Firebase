package com.example.firebase.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.databinding.FragmentFriendsBinding
import com.example.firebase.databinding.FragmentSigninBinding
import com.example.firebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FriendsFragment : Fragment(R.layout.fragment_friends) {


    private var _binding: FragmentFriendsBinding? = null
    private val binding: FragmentFriendsBinding
        get() = _binding!!

    private lateinit var  userRecyclerView : RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var adapter: FriendsAdapter
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDBref : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)


        mAuth = FirebaseAuth.getInstance()
        mDBref = FirebaseDatabase.getInstance().getReference()
        userList = ArrayList()
        adapter =  FriendsAdapter( userList)
        userRecyclerView =
            binding.rvFriends
        userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        userRecyclerView.adapter = adapter

        mDBref.child("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()

                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    userList.add(currentUser!!)
                }
                 adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}