package com.lopatin.employeekotlin.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeList(
    @SerializedName("response")
    @Expose
    val employeeList: ArrayList<Employee>? = null
)