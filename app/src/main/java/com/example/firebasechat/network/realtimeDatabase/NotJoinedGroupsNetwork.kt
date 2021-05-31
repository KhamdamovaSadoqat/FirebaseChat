package com.example.firebasechat.network.realtimeDatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebasechat.model.Group
import com.google.firebase.database.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.ArrayList

class NotJoinedGroupsNetwork: ArrayList<Group>() {

    private val notJoinedGroup = FirebaseDatabase.getInstance().getReference("groupList")
    val notJoinedGroupList = ArrayList<Group>()
    private var  listAsLiveData = MutableLiveData<List<Group>>()

    fun getNotJoinedGroups(): LiveData<List<Group>> {
        notJoinedGroup.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists() && snapshot.value != null){
                    notJoinedGroupList.clear()
                    for (data in snapshot.children){
                        Log.d("Network", "Datasnapshot: ${data.value}")
                        data.getValue(Group::class.java)?.let {
                            Log.d("Network", "Datasnapshot: $it")
                            notJoinedGroupList.add(it) }
                    }
                    listAsLiveData.value = notJoinedGroupList
                    Log.d("Network", "NOT JOINED GROUP LIST: $notJoinedGroupList")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("NotJoinedGroups", "onCancelled: ${error.message}")
            }

        })
        return listAsLiveData
    }

     fun getNotJoinedGroupsList(): ArrayList<Group>{
        GlobalScope.launch {
            notJoinedGroup.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("Network", "onDataChange: called")

                    if (snapshot.exists() && snapshot.value != null) {
                        notJoinedGroupList.clear()
                        for (data in snapshot.children) {
                            data.getValue(Group::class.java)?.let { notJoinedGroupList.add(it) }
                        }
                        Log.d("Network", "NOT JOINED GROUP LIST: $notJoinedGroupList")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Network", "onCancelled: ${error.message}")
                }
            })
        }
        return notJoinedGroupList


//         val result = notJoinedGroup.get()
//
//         if(result.result!=null){
//             notJoinedGroupList.clear()
//             for (data in result.result!!.children){
//                 data.getValue(Group::class.java)?.let { notJoinedGroupList.add(it) }
//             }
//             Log.d("Network", "list $notJoinedGroupList")
//         }





    }


}