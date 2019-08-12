package com.karashok.common.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name UserResponse
 * @date 2019/08/03 11:49
 **/
data class UserResponse(
    @SerializedName("email") var email: String = "",
    @SerializedName("icon") var icon: String = "",
    @SerializedName("id") var id: Int = 0,
    @SerializedName("password") var password: String = "",
    @SerializedName("token") var token: String = "",
    @SerializedName("type") var type: Int = 0,
    @SerializedName("username") var username: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(icon)
        parcel.writeInt(id)
        parcel.writeString(password)
        parcel.writeString(token)
        parcel.writeInt(type)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserResponse> {
        override fun createFromParcel(parcel: Parcel): UserResponse {
            return UserResponse(parcel)
        }

        override fun newArray(size: Int): Array<UserResponse?> {
            return arrayOfNulls(size)
        }
    }

}