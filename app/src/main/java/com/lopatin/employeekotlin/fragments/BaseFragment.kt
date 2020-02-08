package com.lopatin.employeekotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {
    abstract fun getFragmentLayout(): Int

    abstract fun getValuesFromArguments()

    abstract fun setPresenter()

    abstract fun nullifyPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        getValuesFromArguments()
        setPresenter()
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun onStop() {
        nullifyPresenter()
        super.onStop()
    }
}