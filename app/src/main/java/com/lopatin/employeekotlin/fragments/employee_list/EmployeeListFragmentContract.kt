package com.lopatin.employeekotlin.fragments.employee_list

import android.content.Context
import androidx.fragment.app.Fragment
import com.lopatin.employeekotlin.activities.main.MainActivityPresenter
import com.lopatin.employeekotlin.model.data.DatabaseEmployee

interface EmployeeListFragmentContract {
    interface View {
        fun setRecyclerAdapter(list: ArrayList<DatabaseEmployee>)
    }

    interface Presenter {
        fun fragmentIsCreating(context: Context?, specialtyId: Int)
        fun fragmentIsStarting()
        fun setViewToPresenter(view: View?)
        fun selectedEmployee(databaseEmployee: DatabaseEmployee)
    }

    interface Navigator {
        fun createFragment(fragment: Fragment)
        fun setFragmentType(currentFragmentType: MainActivityPresenter.FragmentType)
    }

    interface Model {
        fun getDatabaseEmployeeListBySpecialty(context: Context, specialtyId: Int): ArrayList<DatabaseEmployee>
    }
}