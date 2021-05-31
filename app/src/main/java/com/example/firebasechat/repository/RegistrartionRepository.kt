package com.example.firebasechat.repository

import android.util.Log
import com.example.firebasechat.database.RegistrationDao
import com.example.firebasechat.model.Registration
import com.example.firebasechat.network.realtimeDatabase.RegistrationNetwork

class RegistrartionRepository(private val registrationNetwork: RegistrationNetwork, private val dataSource: RegistrationDao) {


    fun setDataToFirebase(name: String, userId: String){
        Log.d("RegistrationRepository", "logging: working")
        registrationNetwork.setDataToFirebase(name, userId)
    }

    suspend fun setDataToRoom(name: String, userId: String){
        dataSource.insertUser(Registration(userId, name))
    }
}