package com.example.firebase.ui.chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.Firebase
import com.example.firebase.R
import com.example.firebase.databinding.ItemFriendsBinding
import com.example.firebase.model.User
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.withContext

class ChatAdapter(
    private val userList: MutableList<User>,
    val context: Context
) : RecyclerView.Adapter<ChatAdapter.FriendsViewHolder>() {


    inner class FriendsViewHolder(val binding: ItemFriendsBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding = ItemFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FriendsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {



        with(holder){
            with(userList[position]) {
                binding.tvName.text = this.name
                binding.tvPhoneNumber.text = this.phoneNumber



                holder.itemView.setOnClickListener {
                    val name1 =  this.name
                    val uid = FirebaseAuth.getInstance().currentUser?.uid

                    val bundle = bundleOf(
                        "name1" to name1 ,
                        "uid" to uid
                    )
                    it?.findNavController()
                        ?.navigate(R.id.action_chatFragment2_to_messageFragment,bundle)


                }
            }
        }
    }

    // return the size of list
    override fun getItemCount(): Int {
        return userList.size
    }


}