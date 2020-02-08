package com.lopatin.employeekotlin.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.*

class EmployeeDatabaseHelper(context: Context) :
    ManagedSQLiteOpenHelper(
        context,
        EmployeeDatabaseInfo.DATABASE_NAME, null,
        DB_VERSION
    ) {
    companion object {
        const val DB_VERSION = 1
        private var instance: EmployeeDatabaseHelper? = null
        @Synchronized
        fun getInstance(context: Context): EmployeeDatabaseHelper {
            if (instance == null) {
                instance =
                    EmployeeDatabaseHelper(context.applicationContext)
            }
            try {
                instance!!
            } catch (e: Exception) {
                Log.d("logException", "ShopDatabaseHelper Exception: $e")
            }
            return instance as EmployeeDatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        createEmployeeTable(db)
        createSpecialtyTable(db)
        createSpecialtyOfEmployeeTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    private fun createEmployeeTable(db: SQLiteDatabase?) {
        try {
            db?.createTable(
                EmployeeDatabaseInfo.TABLE_EMPLOYEE, true,
                EmployeeDatabaseInfo.COLUMN_EMPLOYEE_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EmployeeDatabaseInfo.COLUMN_EMPLOYEE_FIRST_NAME to TEXT,
                EmployeeDatabaseInfo.COLUMN_EMPLOYEE_LAST_NAME to TEXT,
                EmployeeDatabaseInfo.COLUMN_EMPLOYEE_BIRTHDAY to TEXT,
                EmployeeDatabaseInfo.COLUMN_EMPLOYEE_AGE to TEXT,
                EmployeeDatabaseInfo.COLUMN_EMPLOYEE_IMAGE_PATH to TEXT
            )
        } catch (e: Exception) {
            Log.d("logException", "Exception: $e")
        }
    }

    private fun createSpecialtyTable(db: SQLiteDatabase?) {
        try {
            db?.createTable(
                EmployeeDatabaseInfo.TABLE_SPECIALTY, true,
                EmployeeDatabaseInfo.COLUMN_SPECIALTY_ID to INTEGER + PRIMARY_KEY,
                EmployeeDatabaseInfo.COLUMN_SPECIALTY_NAME to TEXT
            )
        } catch (e: Exception) {
            Log.d("logException", "Exception: $e")
        }
    }

    private fun createSpecialtyOfEmployeeTable(db: SQLiteDatabase?) {
        try {
            db?.createTable(
                EmployeeDatabaseInfo.TABLE_SPECIALTY_OF_EMPLOYEE, true,
                EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID to INTEGER,
                EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID to INTEGER
            )
        } catch (e: Exception) {
            Log.d("logException", "Exception: $e")
        }
    }
}