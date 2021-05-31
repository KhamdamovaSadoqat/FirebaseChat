package com.example.firebasechat

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebasechat.model.Group
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

object Singleton {

    private lateinit var database: DatabaseReference
    private val myRef = FirebaseDatabase.getInstance().getReference("User")
    var list = ArrayList<Group>()
    private val notJoinedGroup = FirebaseDatabase.getInstance().getReference("groupList")
    var notJoinedGroupList = arrayListOf<Group>()
    private var listAsLiveData = MutableLiveData<ArrayList<Group>>()
    var TAG = "Singelton"


    fun getJoinedList(userName: String): ArrayList<Group>{
        myRef.child(userName).child("JoinedGroup")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists() && snapshot.value != null) {
                            list.clear()
                            for (data in snapshot.children) {
                                data.getValue(Group::class.java)?.let { list.add(it) }
                            }
//                            Log.d("Network", "List: $list")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
//                        Log.d("Network", list.toString())
                    }
                })
//        Log.d("Network", "JoinedGroupList: $list")
        return  list
    }

    fun getNotJoinedGroup(userName:String): LiveData<ArrayList<Group>> {
//       return withContext(IO){
        notJoinedGroupList.clear()
            val joinedGroup =
                       this@Singleton.notJoinedGroup.get()
                                .addOnSuccessListener { notJoinedGroup ->
                                        for (group in notJoinedGroup.children){

                                            notJoinedGroupList.add(group.getValue(Group::class.java)!!)
                                        }
                                        Log.d(TAG, "NotJoinedGroupList: $notJoinedGroupList")

                                    myRef.child(userName).child("JoinedGroup").get()
                                            .addOnSuccessListener {joinedGroup ->
                                                for (group in joinedGroup.children){
                                                    notJoinedGroupList = notJoinedGroupList.minus(group.getValue(Group::class.java)) as ArrayList<Group>
                                                    Log.d(TAG, "NotJoinedGroupList: $notJoinedGroupList")
                                                }
                                                listAsLiveData.value = notJoinedGroupList
                                                Log.d(TAG, "Livedata: $listAsLiveData")

                                }
                    }

//            Log.d("Network", "getNotJoinedGroup:joinedGroup: ${joinedGroup.result.toString()}  \n notJoinedGroup: ${notJoinedGroup.result.toString()}")
//
//            if (joinedGroup.isSuccessful && joinedGroup.result != null && this@Singleton.notJoinedGroupList.isNotEmpty()){
//
//                Log.d("Network", "NotJoinedGroupList for user: $notJoinedGroupList")
//            }
//            listAsLiveData.postValue(notJoinedGroupList)
//            Log.d("Network", "NotJoinedGroupList as liveData: $listAsLiveData")
//            listAsLiveData
//        }
        return  listAsLiveData
    }

//    fun getNotJoinedList(): ArrayList<Group>{
//        notJoinedGroup.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("Network", "onDataChange: called")
//
//                if (snapshot.exists() && snapshot.value != null) {
//                    notJoinedGroupList.clear()
//                    for (data in snapshot.children) {
//                        data.getValue(Group::class.java)?.let { notJoinedGroupList.add(it) }
//                    }
//                    Log.d("Network", "NOT JOINED GROUP LIST: $notJoinedGroupList")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("Network", "onCancelled: ${error.message}")
//            }
//        })
//        Thread.sleep(1000)
//        Log.d("Network", "notJoinedGroupList: $notJoinedGroup")
//        return  notJoinedGroupList
//    }






}