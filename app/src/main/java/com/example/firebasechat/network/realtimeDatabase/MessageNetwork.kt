package com.example.firebasechat.network.realtimeDatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.firebasechat.model.Message
import com.example.firebasechat.ui.chat.GetDate
import com.google.firebase.database.*
import kotlin.collections.ArrayList
import kotlin.math.log

class MessageNetwork {

    private val myRef = FirebaseDatabase.getInstance().reference
    var list = ArrayList<Message>()
    private var listAsLiveData = MutableLiveData<List<Message>>()
    private var liveData = MutableLiveData<List<Message>>()
    var msg  = ArrayList<Message>()

    fun sendMessage(message: Message, groupName: String){
        val msgId = GetDate.referenceMaking()
        myRef.child("messages").child(groupName).child(msgId.toString()).setValue(message)
    }

    fun getMessage(groupName: String): LiveData<List<Message>> {
        myRef.child("messages").child(groupName)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists() && snapshot.value != null) {
                            list.clear()
                            for (data in snapshot.children) {
                                data.getValue(Message::class.java)?.let { list.add(it) }
                            }
                            listAsLiveData.value = list

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Network", list.toString())
                    }
                })

        return listAsLiveData
    }

    fun getLastMessage(groupName: String, index: Int, maxIndex: Int): LiveData<List<Message>>{
        msg.clear()
        for(index in 0..maxIndex){
            msg.add(index, Message(" ", " ", " "))
        }
        myRef.child("messages").child(groupName).orderByKey().limitToLast(1)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d("Group", "onDataChange: groupName: $groupName")
                        Log.d("Group", "onDataChange: snapshot exist: ${snapshot.exists()}")
                        Log.d("Group", "onDataChange: snapshot value: ${snapshot.value}")
                        if (snapshot.exists() && snapshot.value != null) {
                            for (data in snapshot.children) {
                                data.getValue(Message::class.java)?.let {
                                    msg.removeAt(index)
                                    msg.add(index, it) }
                            }
                            liveData.value = msg
                        }else{
                            msg.add(index, Message(" ", " ", " "))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Network", "onCancelled: something went wrong")
                    }

                })
        return  liveData
    }



}

