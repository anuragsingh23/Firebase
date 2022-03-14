package com.example.firebase.ui.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.model.data.Users

class FriendsAdapter: RecyclerView.Adapter<FriendsAdapter.UsersViewHolder>(){


   inner class UsersViewHolder(itemView : View)  : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friends,parent,false)
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        val user = differ.currentList[position]
        holder.apply {


        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}