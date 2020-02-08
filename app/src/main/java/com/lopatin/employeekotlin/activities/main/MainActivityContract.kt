package com.lopatin.employeekotlin.activities.main

import android.content.Context
import androidx.fragment.app.Fragment
import com.lopatin.employeekotlin.model.data.DatabaseEmployee

interface MainActivityContract {
    interface View {
        fun showBackButton(isShow: Boolean)
        fun callSuperBackPressed()
        fun showProgressBar(isShow: Boolean)
        fun setToolbarTitle(titleId: Int)
    }

    interface Presenter {
        fun setViewToPresenter(view: View?)
        fun activityIsCreating(context: Context)
        fun buttonBackPressed()
    }

    interface Navigator {
        fun createFragment(fragment: Fragment)
    }

    interface Model {
        fun putEmployeeToEmployeeTable(context: Context, databaseEmployee: DatabaseEmployee): Long
        fun putEmployeeAndSpecialtyToSpecialtyOfEmployeeTable(
            context: Context,
            specialtyId: Long,
            employeeId: Long
        ): Long

        fun putSpecialtyToSpecialtyTable(
            context: Context,
            specialtyId: Long,
            specialtyName: String
        ): Long
    }
}