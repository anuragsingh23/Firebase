package com.example.firebase.ui.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.ReceiveBinding
import com.example.firebase.databinding.SentBinding
import com.example.firebase.model.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter (val messageList : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType==1){
            val binding = ReceiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceiveViewHolder(binding)
        } else {
            val binding = SentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SentViewHolder(binding)
        }


    }

    val ITEM_SENT : Long= 1
    val ITEM_RECEIVE : Long = 2

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

       val currentMessage = messageList[position]

        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            viewHolder.sentMessage.text = currentMessage.message
        }
        else {

            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemId(position: Int): Long {
        val currentUser = messageList[position]

        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentUser.senderId)){
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
      return  messageList.size

    }


    inner class SentViewHolder(val sbinding: SentBinding): RecyclerView.ViewHolder(sbinding.root){
            val sentMessage = sbinding.tvSentMessage
    }
    inner class ReceiveViewHolder(val rBinding : ReceiveBinding): RecyclerView.ViewHolder(rBinding.root){
            val receiveMessage = rBinding.tvReceiveMessage
    }
}



