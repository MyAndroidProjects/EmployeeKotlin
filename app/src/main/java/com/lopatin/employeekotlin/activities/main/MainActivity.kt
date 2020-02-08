package com.lopatin.employeekotlin.activities.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.lopatin.employeekotlin.R
import com.lopatin.employeekotlin.navigation.NavigationContract
import com.lopatin.employeekotlin.navigation.NavigationManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.View, NavigationContract.MainActivity {

    private var presenter: MainActivityContract.Presenter? = null
    private var navigationManager: NavigationContract.SetActivities? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar()
        setPresenterAndNavigationManager()
        presenter?.activityIsCreating(this)
    }

    override fun onStop() {
        nullifyPresenterAndNavigationManager()
        super.onStop()
    }

    override fun onBackPressed() {
        presenter?.buttonBackPressed()
    }


    private fun setPresenterAndNavigationManager() {
        presenter = MainActivityPresenter
        presenter?.setViewToPresenter(this)
        navigationManager = NavigationManager
        navigationManager?.setMainActivityToNavigationManager(this)
    }

    private fun nullifyPresenterAndNavigationManager() {
        navigationManager?.setMainActivityToNavigationManager(null)
        presenter?.setViewToPresenter(null)
        presenter = null
        navigationManager = null
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun createFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun showBackButton(isShow: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
    }

    override fun setToolbarTitle(titleId: Int) {
        toolbar.title = getString(titleId)
    }

    override fun showProgressBar(isShow: Boolean) {
        if (isShow) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun callSuperBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                presenter?.buttonBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
