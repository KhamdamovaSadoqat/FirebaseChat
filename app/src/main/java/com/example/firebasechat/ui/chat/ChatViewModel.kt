package com.example.firebasechat.ui.chat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasechat.R
import com.example.firebasechat.databinding.ChatFragmentBinding
import com.example.firebasechat.model.Message
import com.example.firebasechat.network.realtimeDatabase.MessageNetwork
import com.example.firebasechat.repository.MessageRepository

class ChatViewModel() : ViewModel() {

    var msgText: String? = null
    var message: String? = null
    var date: String? = null
    private var sender: String? = null
    var groupName: String? = null
    private val messageNetwork = MessageNetwork()
    private val repository = MessageRepository(messageNetwork)

    fun groupNameis(groupNameis: String){
        groupName = groupNameis
        Log.d("Chat", "group: $groupName")
    }

    fun sendMessage(message: Message, groupName: String){
        repository.sendMessage(message, groupName)
    }

    fun getMessage(groupName: String): LiveData<List<com.example.firebasechat.model.Message>>{
        return repository.getMessage(groupName)
    }

}