package com.example.remap.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.remap.data.entity.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {
    @Query("SELECT * FROM properties")
    fun getAllProperties(): Flow<List<Property>>
}