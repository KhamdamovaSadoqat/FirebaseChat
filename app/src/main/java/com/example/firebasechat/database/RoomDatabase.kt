package com.example.firebasechat.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.firebasechat.model.Group
import com.example.firebasechat.model.Message
import com.example.firebasechat.model.Registration
import java.util.ArrayList

@Database(entities = [Group::class, Message::class, Registration::class], version = 1, exportSchema = true)
//@TypeConverters(Converter::class)
abstract class RoomDatabase: androidx.room.RoomDatabase() {
    abstract val groupDao: GroupDao
    abstract val messageDao: MessageDao
    abstract val registrationDao: RegistrationDao

    companion object{
        private var INSTANCE: RoomDatabase? = null

        fun getDatabase(context: Context): RoomDatabase{
            if(INSTANCE != null){
                return  INSTANCE!!
            }
            synchronized(this){
                val database = Room
                        .databaseBuilder(context, RoomDatabase::class.java, "Chat")
                        .build()

                INSTANCE = database
                return INSTANCE!!
            }
        }
    }
}

//class Converter {
//
//    @TypeConverter
//    fun fromArray(list: ArrayList<StrictMath>): String {
//        var string: String = ""
//        for (s in list) string += ("$s,")
//        return string
//    }
//
//    @TypeConverter
//    fun toArray(concatenatedString: String): ArrayList<String> {
//        var myString = ArrayList<String>()
//        for (s in concatenatedString.split(","))
//            myString.add(s)
//        return myString
//    }
//
//}
