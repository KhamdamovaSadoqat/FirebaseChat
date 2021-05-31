package com.example.firebasechat.network.realtimeDatabase

import com.example.firebasechat.model.Group
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreatGroupNetwork{

    private lateinit var database: DatabaseReference

    fun createNewGroup(userName: String, groupName: String, groupId: String){
        database = Firebase.database.reference
        val newGroup  = Group(groupName, groupId)
        database.child("User").child(userName).child("JoinedGroup").child(groupName).setValue(newGroup)
    }


}