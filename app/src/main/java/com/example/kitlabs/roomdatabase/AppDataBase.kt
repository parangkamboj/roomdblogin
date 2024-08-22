package com.example.kitlabs.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User:: class], version = 3)
abstract class AppDataBase : RoomDatabase() {
    abstract fun Dao() : Dao
    companion object{
        @Volatile
        private var INSTANCE : AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "AppDatabase3"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}