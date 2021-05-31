package com.example.firebasechat.network.realtimeDatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebasechat.model.Group
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class GroupListNetwork {
    private lateinit var database: DatabaseReference
    private val myRef = FirebaseDatabase.getInstance().getReference("User")
    var list = ArrayList<Group>()
    private var listAsLiveData = MutableLiveData<ArrayList<Group>>()


    fun setDataToGroupList(groupName: String, groupId: String) {
        database = Firebase.database.reference
        database.child("groupList").child(groupName).setValue(Group(groupName, groupId))
    }

    fun checkingForGroupList(userName: String): LiveData<ArrayList<Group>> {
        myRef.child(userName).child("JoinedGroup")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists() && snapshot.value != null) {
                            list.clear()
                            for (data in snapshot.children) {
                                data.getValue(Group::class.java)?.let { list.add(it) }
                            }
                            Log.d("Network", "list ${listAsLiveData.value.toString()}")
                            listAsLiveData.value = list
                            Log.d("Network", "live data ${listAsLiveData.value.toString()}")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Network", list.toString())
                    }
                })

        return listAsLiveData
    }

//     fun getJoinedGroupList(userName: String): ArrayList<Group> {
//
//        GlobalScope.launch {
//            myRef.child(userName).child("JoinedGroup")
//                    .addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            Log.d("Network", "before If: $myRef")
//                            if (snapshot.exists() && snapshot.value != null) {
//                                Log.d("Network", "after if: $snapshot")
//                                list.clear()
//                                for (data in snapshot.children) {
//                                    data.getValue(Group::class.java)?.let { list.add(it) }
//                                }
//                                Log.d("Network", "List: $list")
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            Log.d("Network", list.toString())
//                        }
//                    })
//
//        }
//        Log.d("Network", "Delay: $list")
//        return  list
//    }

    fun addGroupToJoinedGroup(userName: String, group: Group){
        GlobalScope.launch {
            database = Firebase.database.reference
            database.child("User").child(userName).child("JoinedGroup").child(group.groupName).setValue(group)
        }
    }
}










//        Log.d("Network", "Username: $userName")
//        val result = myRef.child(userName).child("JoinedGroup")
//                .get()
//        Log.d("Network", "Result: $result")
//        if(result.result!=null){
//            Log.d("Network", "check: working")
//            list.clear()
//            for (data in result.result!!.children){
//                data.getValue(Group::class.java)?.let { list.add(it) }
//            }
//            Log.d("Network", "list $list")
//        }

//        Log.d("Network", "Username: $userName")
//        myRef.child(userName).child("JoinedGroup")
//                .addValueEventListener(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if(snapshot.exists() && snapshot.value != null){
//                            list.clear()
//                            for (data in snapshot.children){
//                                data.getValue(Group::class.java)?.let { list.add(it) }
//                            }
//                            Log.d("Network", "list $list")
//                        }
//                    }
//                    override fun onCancelled(error: DatabaseError) {
//                        Log.d("Network", list.toString())
//                    }
//                })





