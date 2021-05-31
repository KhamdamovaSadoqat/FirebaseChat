package com.example.firebasechat.repository

import androidx.lifecycle.LiveData
import com.example.firebasechat.model.Message
import com.example.firebasechat.network.realtimeDatabase.MessageNetwork

class MessageRepository(private val messageNetwork: MessageNetwork) {

    fun sendMessage(message: Message, groupName: String){
        return messageNetwork.sendMessage(message, groupName)
    }

    fun getMessage(groupName: String): LiveData<List<Message>>{
        return messageNetwork.getMessage(groupName)
    }

    fun getLastMessage(groupName: String, index: Int, maxIndex: Int): LiveData<List<Message>>{
        return  messageNetwork.getLastMessage(groupName, index, maxIndex)
    }

}