package com.example.firebasechat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Message")
data class Message(
        val sender: String="",
        @PrimaryKey
        val date: String="",
        val msgText: String=""
)