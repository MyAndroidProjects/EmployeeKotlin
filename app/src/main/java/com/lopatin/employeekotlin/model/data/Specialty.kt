package com.lopatin.employeekotlin.model.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Specialty(
    @SerializedName("specialty_id")
    @Expose
    val specialtyId: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null
): Parcelable