package com.example.remap.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "properties")
data class Property(
    @ColumnInfo(name = "_id") @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "property_name") val propertyName: String?,
    @ColumnInfo(name = "property_address") val propertyAddress: String?,
    @ColumnInfo(name = "property_latitude") val propertyLatitude: Double?,
    @ColumnInfo(name = "property_longitude") val propertyLongitude: Double?,
    @ColumnInfo(name = "property_category") val propertyCategory: String?,
    @ColumnInfo(name = "property_description") val propertyDescription: String?,
    @ColumnInfo(name = "property_office_hours") val propertyOfficeHours: String?,
    @ColumnInfo(name = "property_contacts") val propertyContacts: String?
)
