package com.example.firebase.ui.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.ItemFriendsBinding
import com.example.firebase.model.User
import com.google.firebase.database.core.Context

class FriendsAdapter(
    val userList: MutableList<User>,
) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {


    inner class FriendsViewHolder(val binding: ItemFriendsBinding) : RecyclerView.ViewHolder(binding.root)

    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding = ItemFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FriendsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        with(holder){
            with(userList[position]){
                binding.tvName.text = this.name
                binding.tvPhoneNumber.text = this.phoneNumber
            }
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return userList.size
    }
}