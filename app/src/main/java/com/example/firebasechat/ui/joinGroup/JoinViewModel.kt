package com.example.firebasechat.ui.joinGroup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasechat.Singleton
import com.example.firebasechat.database.GroupDao
import com.example.firebasechat.model.Group
import com.example.firebasechat.network.realtimeDatabase.CreatGroupNetwork
import com.example.firebasechat.network.realtimeDatabase.GroupListNetwork
import com.example.firebasechat.network.realtimeDatabase.NotJoinedGroupsNetwork
import com.example.firebasechat.repository.GroupRepository

class JoinViewModel(groupDao: GroupDao) : ViewModel() {

    private val getGroupList =  GroupListNetwork()
    private val createNetwork = CreatGroupNetwork()
    private val notJoinedGroupsNetwork = NotJoinedGroupsNetwork()
    private val groupRepository = GroupRepository(createNetwork, getGroupList, notJoinedGroupsNetwork, groupDao)
    private lateinit var notJoinedGroupList: ArrayList<Group>
    private lateinit var joinedGroupList: ArrayList<Group>
    private var minusLiveData = MutableLiveData<ArrayList<Group>>()
    lateinit var minus: ArrayList<Group>


//    fun getNotJoinedGroupList(userName: String): LiveData<ArrayList<Group>> {
//            minusLiveData = Singleton.getNotJoinedGroup(userName) as MutableLiveData<ArrayList<Group>>
//            Log.d("Network", "MutableLiveData: $minusLiveData")
//        return  minusLiveData
//
////        joinedGroupList = Singleton.getJoinedList(userName)
////        notJoinedGroupList = Singleton.getNotJoinedList()
////
////        minus = notJoinedGroupList.minus(joinedGroupList) as ArrayList<Group>
////        minusLiveData.value = minus
////
////        Log.d("ViewModel", "minus livedata return: $minusLiveData")
////        return minusLiveData
//    }

    fun addGroupToJoinedGroup(userName: String, group: Group){
        return groupRepository.addGroupToJoinedGroup(userName, group)
    }

    fun getNotJoinedGroupLiveData(): LiveData<List<Group>>{
        return groupRepository.getNotJoinedGroupLiveData()
    }



//    suspend fun heavyWork(userName: String) = withContext(Dispatchers.IO){
//        joinedGroupList = groupRepository.getJoinedGroupList(userName)
//        Log.d("ViewModel", "Joined: $joinedGroupList")
//        notJoinedGroupList = groupRepository.getNotJoinedGroupList()
//        Log.d("ViewModel", "Not joined: $notJoinedGroupList")
//
//        minus = notJoinedGroupList.minus(joinedGroupList) as ArrayList<Group>
//        Log.d("ViewModel", "Minus: $minus")
//        minusLiveData.value = minus
//        Log.d("ViewModel", "minus livedata: $minusLiveData")
//    }


}