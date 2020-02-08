package com.lopatin.employeekotlin.navigation

import androidx.fragment.app.Fragment
import com.lopatin.employeekotlin.activities.main.MainActivityPresenter

/**
 *  NavigationManager connect presenters with current activity
 */

object NavigationManager : NavigationContract.Manager, NavigationContract.SetActivities {

    private var mainActivity: NavigationContract.MainActivity? = null
    private val mainActivityPresenter: NavigationContract.MainActivityPresenter = MainActivityPresenter

    override fun setMainActivityToNavigationManager(mainActivity: NavigationContract.MainActivity?) {
        NavigationManager.mainActivity = mainActivity
    }


    override fun createFragment(fragment: Fragment) {
        mainActivity?.createFragment(fragment)
    }

    override fun setFragmentType(currentFragmentType: MainActivityPresenter.FragmentType) {
        mainActivityPresenter.setFragmentType(currentFragmentType)
    }
}