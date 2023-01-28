package com.example.remap.models

import android.os.Parcel
import android.os.Parcelable

data class EcoMarkers(var imageIconURL: String, var imageContentURL: String, var ecoTitle: String, var ecoExamplesTitle: String, var ecoExamples: String, var ecoDescription: String): Parcelable {

    constructor():this(
        "",
        "",
        "",
        "",
        "",
        ""
    )

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageIconURL)
        parcel.writeString(imageContentURL)
        parcel.writeString(ecoTitle)
        parcel.writeString(ecoExamplesTitle)
        parcel.writeString(ecoExamples)
        parcel.writeString(ecoDescription)
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