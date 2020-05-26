package com.lopatin.employeekotlin.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DatabaseEmployee(
    var id: Int,
    val fName: String,
    val lName: String,
    val birthday: String,
    val age: String,
    val avatarUrl: String
) : Parcelable