package com.lopatin.employeekotlin.fragments.specialty

import android.content.Context
import androidx.fragment.app.Fragment
import com.lopatin.employeekotlin.activities.main.MainActivityPresenter
import com.lopatin.employeekotlin.model.data.Specialty

interface SpecialtyListFragmentContract {
    interface View {
        fun setRecyclerAdapter(list: ArrayList<Specialty>)
    }

    interface Presenter {
        fun setViewToPresenter(view: View?)
        fun selectedSpecialty(specialty: Specialty)
        fun fragmentIsStarting(context: Context?)
    }

    interface Navigator {
        fun createFragment(fragment: Fragment)
        fun setFragmentType(currentFragmentType: MainActivityPresenter.FragmentType)
    }

    interface Model {
        fun getSpecialtyListFromSpecialtyTable(context: Context): ArrayList<Specialty>
    }
}