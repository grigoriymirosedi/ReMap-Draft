package com.example.remap_admin.domain

import android.os.Parcel
import android.os.Parcelable

data class MapItem(
                   var categoryBatteries: Boolean,
                   var categoryClothes: Boolean,
                   var categoryGlass: Boolean,
                   var categoryLamps: Boolean,
                   var categoryMetal: Boolean,
                   var categoryPaper: Boolean,
                   var categoryPlastic: Boolean,
                   var categoryEcomob: Boolean,
                   var property_adress: String,
                   var property_contacts: String,
                   var property_description: String,
                   var property_latitude: Double,
                   var property_longitude: Double,
                   var property_name: String,
                   var property_office_hours: String
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    constructor():this(
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        "",
        "",
        "",
        43.279,
        39.313,
        "",
        "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (categoryBatteries) 1 else 0)
        parcel.writeByte(if (categoryClothes) 1 else 0)
        parcel.writeByte(if (categoryGlass) 1 else 0)
        parcel.writeByte(if (categoryLamps) 1 else 0)
        parcel.writeByte(if (categoryMetal) 1 else 0)
        parcel.writeByte(if (categoryPaper) 1 else 0)
        parcel.writeByte(if (categoryPlastic) 1 else 0)
        parcel.writeByte(if (categoryEcomob) 1 else 0)
        parcel.writeString(property_adress)
        parcel.writeString(property_contacts)
        parcel.writeString(property_description)
        parcel.writeDouble(property_latitude)
        parcel.writeDouble(property_longitude)
        parcel.writeString(property_name)
        parcel.writeString(property_office_hours)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MapItem> {
        override fun createFromParcel(parcel: Parcel): MapItem {
            return MapItem(parcel)
        }

        override fun newArray(size: Int): Array<MapItem?> {
            return arrayOfNulls(size)
        }
    }
}
