package com.lopatin.employeekotlin.fragments.specialty

import android.content.Context
import com.lopatin.employeekotlin.activities.main.MainActivityPresenter
import com.lopatin.employeekotlin.fragments.employee_list.EmployeeListFragment
import com.lopatin.employeekotlin.model.data.Specialty
import com.lopatin.employeekotlin.model.database.EmployeeDatabaseManager
import java.util.ArrayList


object SpecialtyListFragmentPresenter : SpecialtyListFragmentContract.Presenter {
    private val navigator = SpecialtyListFragmentNavigator as SpecialtyListFragmentContract.Navigator

    private var specialtyList = ArrayList<Specialty>()
    private var view: SpecialtyListFragmentContract.View? = null

    override fun fragmentIsStarting(context: Context?) {
        context ?: return
        specialtyList = getSpecialtyList(context)
        view?.setRecyclerAdapter(specialtyList)
    }

    override fun selectedSpecialty(specialty: Specialty) {
        specialty.specialtyId ?: return
        navigator.setFragmentType(MainActivityPresenter.FragmentType.EMPLOYEE_LIST)
        navigator.createFragment(
            EmployeeListFragment.getInstance(specialty.specialtyId)
        )
    }

    private fun getSpecialtyList(context: Context): ArrayList<Specialty> {
        val model: SpecialtyListFragmentContract.Model = EmployeeDatabaseManager()
        return model.getSpecialtyListFromSpecialtyTable(context)
    }

    override fun setViewToPresenter(view: SpecialtyListFragmentContract.View?) {
        SpecialtyListFragmentPresenter.view = view
    }
}