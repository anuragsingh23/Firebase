package com.example.firebase.ui.message

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.databinding.FragmentMessageFragmntBinding
import com.example.firebase.model.Message
import com.example.firebase.model.User
import com.example.firebase.ui.MainActivity
import com.example.firebase.ui.chat.ChatAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class MessageFragment : Fragment(R.layout.fragment_message_fragmnt) {


    private var _binding: FragmentMessageFragmntBinding? = null
    private val binding: FragmentMessageFragmntBinding
    get() = _binding!!

    private lateinit var  messageRecyclerView : RecyclerView



    private lateinit var messageList  : ArrayList<Message>
    private lateinit var adapter: MessageAdapter
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbref : DatabaseReference
    var senderRoom : String ? =null
      var receiverRoom : String ? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageFragmntBinding.inflate(inflater, container, false)


        messageRecyclerView = binding.rvChat


        val name = arguments?.getString("name1")
        val receiverUid = arguments?.getString("uid")

        var senderUid = FirebaseAuth.getInstance().uid
        mDbref = FirebaseDatabase.getInstance().reference

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid
        (activity as MainActivity).supportActionBar?.title = name


        binding.etMessage
        mAuth = FirebaseAuth.getInstance()
        mDbref = FirebaseDatabase.getInstance().getReference()
        messageList = arrayListOf()
        adapter = MessageAdapter(messageList)
        messageRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        messageRecyclerView.adapter = adapter

        mDbref.child("Chats").child(senderRoom!!).child("messages").
        addValueEventListener(object  : ValueEventListener{


            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()

                for (postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.imSent.setOnClickListener {
            val message =binding.etMessage.text.toString()
            val messageObject = Message(message , senderUid)
            mDbref.child("Chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbref.child("Chats").child(receiverUid!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.etMessage.setText("")
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}