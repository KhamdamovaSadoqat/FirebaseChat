package com.example.firebasechat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlin.collections.ArrayList

@Entity(tableName = "Group")
data class Group(
        val groupName: String="",
        @PrimaryKey
        val groupId: String = ""
)

