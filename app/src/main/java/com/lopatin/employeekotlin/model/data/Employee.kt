package com.lopatin.employeekotlin.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("f_name")
    @Expose
    var fName: String? = null,
    @SerializedName("l_name")
    @Expose
    var lName: String? = null,
    @SerializedName("birthday")
    @Expose
    var birthday: String? = null,
    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null,
    @SerializedName("specialty")
    @Expose
    var specialty: ArrayList<Specialty>? = null
)

