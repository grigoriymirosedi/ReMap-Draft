package com.example.remap.models

import android.os.Parcel
import android.os.Parcelable

data class EcoMarkers(var imageResource: Int, var contentImage: Int, var title: String, var examplesTitle: String, var examples: String, var description: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageResource)
        parcel.writeInt(contentImage)
        parcel.writeString(title)
        parcel.writeString(examplesTitle)
        parcel.writeString(examples)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EcoMarkers> {
        override fun createFromParcel(parcel: Parcel): EcoMarkers {
            return EcoMarkers(parcel)
        }

        override fun newArray(size: Int): Array<EcoMarkers?> {
            return arrayOfNulls(size)
        }
    }
}