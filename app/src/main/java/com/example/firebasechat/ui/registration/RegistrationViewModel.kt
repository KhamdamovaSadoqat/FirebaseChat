package com.example.firebasechat.ui.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasechat.database.RegistrationDao
import com.example.firebasechat.network.realtimeDatabase.CreatGroupNetwork
import com.example.firebasechat.network.realtimeDatabase.GroupListNetwork
import com.example.firebasechat.network.realtimeDatabase.NotJoinedGroupsNetwork
import com.example.firebasechat.network.realtimeDatabase.RegistrationNetwork
import com.example.firebasechat.repository.GroupRepository
import com.example.firebasechat.repository.RegistrartionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(registrationNetwork: RegistrationNetwork, dataSource: RegistrationDao) : ViewModel() {


    private val registrartionRepository = RegistrartionRepository(registrationNetwork, dataSource)

    fun setDataToFirebase(userName: String, userId: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                Log.d("RegistrationFragment", "logging: working")
                registrartionRepository.setDataToFirebase(userName, userId)
            }
        }
    }

    fun setDatatoRoom(groupName: String, groupId: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                registrartionRepository.setDataToRoom(groupId, groupName)
            }
        }
    }


}