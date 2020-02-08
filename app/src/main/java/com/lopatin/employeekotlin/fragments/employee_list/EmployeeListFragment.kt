package com.lopatin.employeekotlin.fragments.employee_list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_employee_list.*
import com.lopatin.employeekotlin.R
import com.lopatin.employeekotlin.fragments.BaseFragment
import com.lopatin.employeekotlin.model.data.DatabaseEmployee

class EmployeeListFragment : BaseFragment(), EmployeeListFragmentContract.View,
    EmployeeListFragmentAdapter.EmployeeListItemClickListener {

    companion object {
        const val specialtyIdKey: String = "specialtyId"
        @Synchronized
        fun getInstance(specialtyId: Int): EmployeeListFragment {
            val fragment = EmployeeListFragment()
            val args = Bundle()
            args.putInt(specialtyIdKey, specialtyId)
            fragment.arguments = args
            return fragment
        }
    }

    private var presenter: EmployeeListFragmentContract.Presenter? = null
    private var specialtyId: Int = 0

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_employee_list
    }

    override fun getValuesFromArguments() {
        specialtyId = arguments?.getInt(specialtyIdKey) ?: return
    }

    override fun setPresenter() {
        presenter = EmployeeListFragmentPresenter
        presenter?.setViewToPresenter(this)
    }

    override fun nullifyPresenter() {
        presenter?.setViewToPresenter(null)
        presenter = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val createdView = super.onCreateView(inflater, container, savedInstanceState)
        presenter = EmployeeListFragmentPresenter
        presenter?.fragmentIsCreating(context, specialtyId)
        return createdView
    }

    override fun onStart() {
        super.onStart()
        presenter?.fragmentIsStarting()
    }

    override fun setRecyclerAdapter(list: ArrayList<DatabaseEmployee>) {
        val adapter = EmployeeListFragmentAdapter(list, this)
        val layoutManager = LinearLayoutManager(context)
        recyclerViewEmployeeList.layoutManager = layoutManager
        recyclerViewEmployeeList.adapter = adapter
    }

    override fun onEmployeeItemClick(databaseEmployee: DatabaseEmployee) {
        presenter?.selectedEmployee(databaseEmployee)
    }
}