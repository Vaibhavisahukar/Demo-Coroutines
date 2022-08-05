package com.example.demoproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.utils.TypeConverter

@Database(entities = [ResultsItem::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getDataDao(): DataDao

}
