package com.example.remap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remap.data.entity.Property
import com.example.remap.data.dao.PropertyDao

@Database(entities = [Property::class], version = 1)
abstract class PropertyDB : RoomDatabase() {
    abstract fun getPropertyDao(): PropertyDao
    companion object {
        fun getDB(context: Context) : PropertyDB {
            return Room.databaseBuilder(
                context.applicationContext,
                PropertyDB::class.java,
                "test.db"
            )
                .createFromAsset("database/properties.db")
                .build()
        }
    }
}