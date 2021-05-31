package com.example.firebasechat.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firebasechat.model.Group

@Dao
interface GroupDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group)

    @Query("Select * from `Group` Group by groupName")
    fun getGroupList(): LiveData<List<Group>>

}