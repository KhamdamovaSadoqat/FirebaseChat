package com.example.firebasechat.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebasechat.database.GroupDao
import com.example.firebasechat.database.RegistrationDao
import com.example.firebasechat.network.realtimeDatabase.RegistrationNetwork
import com.example.firebasechat.ui.main.MainViewModel

class RegistrationViewModelFactory(private val registrationNetwork: RegistrationNetwork,
                                   private val dataSource: RegistrationDao): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)){
            return RegistrationViewModel(registrationNetwork, dataSource) as T
        } else throw  IllegalArgumentException("RegistrationViewModel class not found")
    }
}