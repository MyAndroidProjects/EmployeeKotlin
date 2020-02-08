package com.lopatin.employeekotlin.fragments.specialty

import androidx.fragment.app.Fragment
import com.lopatin.employeekotlin.activities.main.MainActivityPresenter
import com.lopatin.employeekotlin.navigation.NavigationContract
import com.lopatin.employeekotlin.navigation.NavigationManager

/**
 * Navigator set commands to Navigation Manager
 */

object SpecialtyListFragmentNavigator : SpecialtyListFragmentContract.Navigator {
    private val manager = NavigationManager as NavigationContract.Manager

    override fun createFragment(fragment: Fragment) {
        manager.createFragment(fragment)
    }

    override fun setFragmentType(currentFragmentType: MainActivityPresenter.FragmentType) {
        manager.setFragmentType(currentFragmentType)
    }
}