package com.example.firebase.ui.home.friend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.model.data.User


class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.UsersViewHolder>() {

    inner class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_friends,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val users = differ.currentList[position]

        holder.itemView.apply {

        }
    }

    private var onItemClickListener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }
}