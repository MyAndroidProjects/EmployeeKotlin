package com.lopatin.employeekotlin.activities.main

import androidx.fragment.app.Fragment
import com.lopatin.employeekotlin.navigation.NavigationContract
import com.lopatin.employeekotlin.navigation.NavigationManager

/**
 * Navigator set commands to Navigation Manager
 */
object MainActivityNavigator : MainActivityContract.Navigator {
    private val manager:NavigationContract.Manager = NavigationManager

    override fun createFragment(fragment: Fragment) {
        manager.createFragment(fragment)
    }
}