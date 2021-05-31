package com.example.firebasechat.network.realtimeDatabase

import android.util.Log
import com.example.firebasechat.model.Registration
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class RegistrationNetwork {

    private lateinit var database: DatabaseReference
    private val myRef = FirebaseDatabase.getInstance().getReference("user")

    fun setDataToFirebase(name: String, userId: String){
        Log.d("RegistrationNetwork", "logging: working")
        database = Firebase.database.reference
//        val setRef = myRef.push()
        database.child("User").child(name).child("UserInformation").setValue(userId)
//        setRef.child(name).setValue(registration)
    }

}