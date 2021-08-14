package com.example.todo_cleanarch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.internal.synchronized

@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
//@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

//    companion object {
//        @Volatile
//        private var INSTANCE: ToDoDatabase? = null
//
//        fun getDatabase(context: Context): ToDoDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ToDoDatabase::class.java,
//                    "todo_database"
//                ).build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }

    companion object {
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase =
            INSTANCE ?: kotlin.synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ToDoDatabase::class.java, "todo_database"
            ).build()
    }

}