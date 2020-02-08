package com.lopatin.employeekotlin.fragments.employee_info

import android.content.Context
import com.lopatin.employeekotlin.model.data.DatabaseEmployee

interface EmployeeInfoFragmentContract {
    interface View {
        fun setFragmentViewFields(employee: DatabaseEmployee)
        fun setSpecialtyViewFields(specialtyList: ArrayList<String>)
    }

    interface Presenter {
        fun setViewToPresenter(view: View?)
        fun setEmployeeToPresenter(employee: DatabaseEmployee)
        fun fragmentIsCreating(context: Context?, employeeId: Int)
        fun fragmentIsStarting()
    }

    interface Model {
        fun getSpecialtyListByDatabaseEmployee(context: Context, employeeId: Int): ArrayList<String>
    }
}