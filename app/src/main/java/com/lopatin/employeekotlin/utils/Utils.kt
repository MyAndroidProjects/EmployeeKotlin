package com.lopatin.employeekotlin.utils

import android.util.DisplayMetrics
import com.lopatin.employeekotlin.EmployeeApplication

object Utils {

    fun dpToPx(dp: Float): Int {
        EmployeeApplication.getApplicationContext().resources?.displayMetrics?.let {
            return (dp * (it.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        }
        return 0
    }
}