package com.lopatin.employeekotlin.fragments.specialty

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_specialty.*
import com.lopatin.employeekotlin.R
import com.lopatin.employeekotlin.fragments.BaseFragment
import com.lopatin.employeekotlin.model.data.Specialty

class SpecialtyListFragment : BaseFragment(), SpecialtyListFragmentAdapter.SpecialtyListItemClickListener,
    SpecialtyListFragmentContract.View {

    companion object {
        @Synchronized
        fun getInstance(): SpecialtyListFragment {
            return SpecialtyListFragment()
        }
    }

    private var presenter: SpecialtyListFragmentContract.Presenter? = null

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_specialty
    }

    override fun getValuesFromArguments() {}


    override fun setPresenter() {
        presenter = SpecialtyListFragmentPresenter
        presenter?.setViewToPresenter(this)
    }

    override fun nullifyPresenter() {
        presenter?.setViewToPresenter(null)
        presenter = null
    }

    override fun onStart() {
        super.onStart()
        presenter?.fragmentIsStarting(context)
    }

    override fun onSpecialtyListItemClick(specialty: Specialty) {
        presenter?.selectedSpecialty(specialty)
    }

    override fun setRecyclerAdapter(list: ArrayList<Specialty>) {
        val adapter = SpecialtyListFragmentAdapter(list, this)
        val layoutManager = LinearLayoutManager(context)
        recyclerViewSpecialty.layoutManager = layoutManager
        recyclerViewSpecialty.adapter = adapter
    }
}