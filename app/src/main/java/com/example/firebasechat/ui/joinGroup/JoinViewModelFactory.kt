package com.example.firebasechat.ui.joinGroup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebasechat.database.GroupDao
import com.example.firebasechat.ui.main.MainViewModel

class JoinViewModelFactory(private val groupDao: GroupDao): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JoinViewModel::class.java)){
            return JoinViewModel(groupDao) as T
        } else throw  IllegalArgumentException("JoinViewModel class not found")
    }
}
