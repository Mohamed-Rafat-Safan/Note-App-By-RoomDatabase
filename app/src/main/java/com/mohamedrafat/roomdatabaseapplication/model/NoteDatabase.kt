package com.mohamedrafat.roomdatabaseapplication.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private val DATABASE_NAME = "user_database"
@Database(entities = [NoteEntity::class] , version = 1 , exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun noteDao() : NoteDAO

    companion object{
        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME).build()

                INSTANCE = instance
                return instance
            }
        }

    }
}