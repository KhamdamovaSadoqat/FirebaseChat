package com.example.firebasechat.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasechat.database.GroupDao
import com.example.firebasechat.model.Group
import com.example.firebasechat.model.Message
import com.example.firebasechat.network.realtimeDatabase.CreatGroupNetwork
import com.example.firebasechat.network.realtimeDatabase.GroupListNetwork
import com.example.firebasechat.network.realtimeDatabase.MessageNetwork
import com.example.firebasechat.network.realtimeDatabase.NotJoinedGroupsNetwork
import com.example.firebasechat.repository.GroupRepository
import com.example.firebasechat.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList


class MainViewModel(dataSource: GroupDao): ViewModel() {

    private val getGroupList =  GroupListNetwork()
    private val createNetwork = CreatGroupNetwork()
    private val notJoinedGroupsNetwork = NotJoinedGroupsNetwork()
    private val groupRepository = GroupRepository(createNetwork, getGroupList, notJoinedGroupsNetwork, dataSource)
    private lateinit var groupList: LiveData<ArrayList<Group>>
    private val messageNetwork = MessageNetwork()
    private val repository = MessageRepository(messageNetwork)


    fun getGroupList(userName: String): LiveData<ArrayList<Group>>{
        groupList =  groupRepository.getGroupList(userName)
        return groupList
    }

    fun insertDataToRoom(groupName: String, groupId: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                groupRepository.insertGroup(Group(groupName, groupId))
            }
        }
    }

    fun getGroupListFromRoom(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                groupRepository.getGroupList()
            }
        }
    }

    fun createNewGroup(userName: String, groupName: String, groupId: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                groupRepository.createNewGroup(userName, groupName, groupId)
            }
        }
    }

    fun setDataToGroupList(groupName: String, groupId: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                groupRepository.setDataToGroupList(groupName, groupId)
            }
        }
    }

    fun getLastMessage(groupName: String, index: Int, maxIndex: Int): LiveData<List<Message>>{
        return repository.getLastMessage(groupName, index, maxIndex)
    }



}