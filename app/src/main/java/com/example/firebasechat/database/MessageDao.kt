package com.example.firebasechat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.firebasechat.model.Message
import com.example.firebasechat.model.Registration

@Dao
interface MessageDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(msg: Message)

}