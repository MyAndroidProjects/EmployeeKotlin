package com.lopatin.employeekotlin.navigation

import androidx.fragment.app.Fragment

interface NavigationContract {

    interface SetActivities {
        fun setMainActivityToNavigationManager(mainActivity: MainActivity?)
    }

    interface Manager {
        fun setFragmentType(currentFragmentType: com.lopatin.employeekotlin.activities.main.MainActivityPresenter.FragmentType)
        fun createFragment(fragment: Fragment)
    }

    interface MainActivity {
        fun createFragment(fragment: Fragment)
    }

    interface MainActivityPresenter {
        fun setFragmentType(currentFragmentType: com.lopatin.employeekotlin.activities.main.MainActivityPresenter.FragmentType)
    }
}