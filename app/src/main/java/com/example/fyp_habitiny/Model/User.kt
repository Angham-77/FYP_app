package com.example.fyp_habitiny.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class User (val userId: Int, var userFullName: String, var userEmail: String, var userPhoneNo: String,
            val userUserName: String, val userPassword: String) : Serializable, Parcelable  {
    // Implementation of Serializable
    // No additional implementation is needed for Serializable

    // Implementation of Parcelable
    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

    private constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeString(userFullName)
        parcel.writeString(userEmail)
        parcel.writeString(userPhoneNo)
        parcel.writeString(userUserName)
        parcel.writeString(userPassword)
    }

    override fun describeContents(): Int {
        return 0
    }
}
