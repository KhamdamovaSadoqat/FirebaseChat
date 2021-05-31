package com.example.firebasechat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "Registration")
data class Registration(
    @PrimaryKey
    val userId: String,
    val userName: String,
//    val joinedGroups: ArrayList<Group>? = null
)

//class Converter {
//
//        @TypeConverter
//        fun fromArray(list: ArrayList<StrictMath>): String {
//            var string: String = ""
//            for (s in list) string += ("$s,")
//            return string
//        }
//
//        @TypeConverter
//        fun toArray(concatenatedString: String): ArrayList<String> {
//            var myString = ArrayList<String>()
//            for (s in concatenatedString.split(","))
//                myString.add(s)
//            return myString
//        }
//
//}
