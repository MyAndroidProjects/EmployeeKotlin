package com.lopatin.employeekotlin.fragments.employee_list

import android.content.Context
import com.lopatin.employeekotlin.activities.main.MainActivityPresenter
import com.lopatin.employeekotlin.fragments.employee_info.EmployeeInfoFragment
import com.lopatin.employeekotlin.model.data.DatabaseEmployee
import com.lopatin.employeekotlin.model.database.EmployeeDatabaseManager

object EmployeeListFragmentPresenter : EmployeeListFragmentContract.Presenter {
    private val navigator = EmployeeListFragmentNavigator as EmployeeListFragmentContract.Navigator
    private var employeeList = ArrayList<DatabaseEmployee>()
    private var view: EmployeeListFragmentContract.View? = null

    override fun fragmentIsCreating(context: Context?, specialtyId: Int) {
        context ?: return
        val model: EmployeeListFragmentContract.Model = EmployeeDatabaseManager()
        employeeList = model.getDatabaseEmployeeListBySpecialty(context, specialtyId)
    }

    override fun fragmentIsStarting() {
        view?.setRecyclerAdapter(employeeList)
    }

    override fun setViewToPresenter(view: EmployeeListFragmentContract.View?) {
        EmployeeListFragmentPresenter.view = view
    }

    override fun selectedEmployee(databaseEmployee: DatabaseEmployee) {
        navigator.setFragmentType(MainActivityPresenter.FragmentType.EMPLOYEE_INFO)
        navigator.createFragment(EmployeeInfoFragment.getInstance(databaseEmployee))
    }
}