package com.example.firebasechat.repository

import androidx.lifecycle.LiveData
import com.example.firebasechat.database.GroupDao
import com.example.firebasechat.model.Group
import com.example.firebasechat.network.realtimeDatabase.CreatGroupNetwork
import com.example.firebasechat.network.realtimeDatabase.GroupListNetwork
import com.example.firebasechat.network.realtimeDatabase.NotJoinedGroupsNetwork
import javax.sql.CommonDataSource

class GroupRepository(private val createNetwork: CreatGroupNetwork,
                      private val groupListNetwork: GroupListNetwork,
                      private val notJoinedGroups: NotJoinedGroupsNetwork,
                      private  val dataSource: GroupDao) {

    fun createNewGroup(userName: String, groupName: String, groupId: String){
        createNetwork.createNewGroup(userName, groupName, groupId)
    }

    fun setDataToGroupList(groupName: String, groupId: String){
        groupListNetwork.setDataToGroupList(groupName, groupId)
    }

    fun getGroupList(userName: String): LiveData<ArrayList<com.example.firebasechat.model.Group>> {
        return groupListNetwork.checkingForGroupList(userName)
    }

    fun addGroupToJoinedGroup(userName: String, group: com.example.firebasechat.model.Group){
        return groupListNetwork.addGroupToJoinedGroup(userName, group)
    }

    fun getNotJoinedGroupLiveData(): LiveData<List<com.example.firebasechat.model.Group>>{
        return notJoinedGroups.getNotJoinedGroups()
    }
//
    fun getNotJoinedGroupList(): ArrayList<com.example.firebasechat.model.Group>{
        return notJoinedGroups.getNotJoinedGroupsList()
    }

//    suspend fun getJoinedGroupList(userName: String): ArrayList<com.example.firebasechat.model.Group>{
//        return groupListNetwork.getJoinedGroupList(userName)
//    }

    //room

    suspend fun insertGroup(group: Group){
        dataSource.insertGroup(group)
    }

    fun getGroupList(): LiveData<List<Group>>{
        return  dataSource.getGroupList()
    }





}