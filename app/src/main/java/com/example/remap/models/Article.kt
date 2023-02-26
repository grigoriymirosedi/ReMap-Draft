package com.example.remap.models

import android.os.Parcel
import android.os.Parcelable

data class Article(var articleTitle: String,
                   var articleIconImageURL: String,
                   var article_par1: String,
                   var article_par2: String,
                   var article_par3: String,
                   var article_par4: String,
                   var article_par5: String,
                   var article_par6: String,
                   var article_par7: String,
                   var articleContentImageURL: String): Parcelable
{
    constructor():this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "")

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(articleTitle)
        parcel.writeString(articleIconImageURL)
        parcel.writeString(article_par1)
        parcel.writeString(article_par2)
        parcel.writeString(article_par3)
        parcel.writeString(article_par4)
        parcel.writeString(article_par5)
        parcel.writeString(article_par6)
        parcel.writeString(article_par7)
        parcel.writeString(articleContentImageURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}