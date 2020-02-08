package com.lopatin.employeekotlin.fragments.employee_info

import android.content.Context
import com.lopatin.employeekotlin.model.data.DatabaseEmployee
import com.lopatin.employeekotlin.model.database.EmployeeDatabaseManager

object EmployeeInfoFragmentPresenter: EmployeeInfoFragmentContract.Presenter {
    private var view: EmployeeInfoFragmentContract.View? = null
    private lateinit var specialtyList: ArrayList<String>
    private lateinit var employee: DatabaseEmployee

    override fun setViewToPresenter(view: EmployeeInfoFragmentContract.View?) {
        EmployeeInfoFragmentPresenter.view = view
    }

    override fun setEmployeeToPresenter(employee: DatabaseEmployee) {
        this.employee = employee
    }

    override fun fragmentIsCreating(context: Context?, employeeId: Int) {
        context ?: return
        val model: EmployeeInfoFragmentContract.Model = EmployeeDatabaseManager()
        specialtyList = model.getSpecialtyListByDatabaseEmployee(context, employeeId)
    }

    override fun fragmentIsStarting() {
        view?.setSpecialtyViewFields(specialtyList)
        view?.setFragmentViewFields(employee)
    }
}