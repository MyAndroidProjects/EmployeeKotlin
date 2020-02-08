package com.lopatin.employeekotlin.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.lopatin.employeekotlin.activities.main.MainActivityContract
import com.lopatin.employeekotlin.fragments.employee_info.EmployeeInfoFragmentContract
import com.lopatin.employeekotlin.fragments.employee_list.EmployeeListFragmentContract
import com.lopatin.employeekotlin.fragments.specialty.SpecialtyListFragmentContract
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import com.lopatin.employeekotlin.model.data.DatabaseEmployee
import com.lopatin.employeekotlin.model.data.Specialty

class EmployeeDatabaseManager : MainActivityContract.Model, SpecialtyListFragmentContract.Model,
    EmployeeListFragmentContract.Model,
    EmployeeInfoFragmentContract.Model {

    override fun putEmployeeToEmployeeTable(context: Context, databaseEmployee: DatabaseEmployee): Long {
        val id: Long
        try {
            val databaseHelper = EmployeeDatabaseHelper.getInstance(context)
            id = databaseHelper.use {
                insert(
                    EmployeeDatabaseInfo.TABLE_EMPLOYEE,
                    EmployeeDatabaseInfo.COLUMN_EMPLOYEE_FIRST_NAME to databaseEmployee.fName,
                    EmployeeDatabaseInfo.COLUMN_EMPLOYEE_LAST_NAME to databaseEmployee.lName,
                    EmployeeDatabaseInfo.COLUMN_EMPLOYEE_BIRTHDAY to databaseEmployee.birthday,
                    EmployeeDatabaseInfo.COLUMN_EMPLOYEE_AGE to databaseEmployee.age,
                    EmployeeDatabaseInfo.COLUMN_EMPLOYEE_IMAGE_PATH to databaseEmployee.avatarUrl
                )
            }
        } catch (e: Exception) {
            Log.d("logException", "Exception: $e")
            return -1
        }
        return id
    }

    override fun putEmployeeAndSpecialtyToSpecialtyOfEmployeeTable(
        context: Context,
        specialtyId: Long,
        employeeId: Long
    ): Long {
        val id: Long
        try {
            val databaseHelper = EmployeeDatabaseHelper.getInstance(context)
            id = databaseHelper.use {
                insert(
                    EmployeeDatabaseInfo.TABLE_SPECIALTY_OF_EMPLOYEE,
                    EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID to specialtyId,
                    EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID to employeeId
                )
            }
        } catch (e: Exception) {
            Log.d("logException", "Exception: $e")
            return -1
        }
        return id
    }

    override fun putSpecialtyToSpecialtyTable(
        context: Context,
        specialtyId: Long,
        specialtyName: String
    ): Long {
        val id: Long
        try {
            val databaseHelper = EmployeeDatabaseHelper.getInstance(context)
            val values = ContentValues()
            values.put(EmployeeDatabaseInfo.COLUMN_SPECIALTY_ID, specialtyId)
            values.put(EmployeeDatabaseInfo.COLUMN_SPECIALTY_NAME, specialtyName)
            id = databaseHelper.use {
                insertWithOnConflict(
                    EmployeeDatabaseInfo.TABLE_SPECIALTY,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_IGNORE
                )
            }
        } catch (e: Exception) {
            Log.d("logException", "Exception: $e")
            return -1
        }
        return id
    }

    override fun getSpecialtyListFromSpecialtyTable(context: Context): ArrayList<Specialty> {
        val list = ArrayList<Specialty>()
        try {
            val databaseHelper = EmployeeDatabaseHelper.getInstance(context)
            databaseHelper.use {
                select(EmployeeDatabaseInfo.TABLE_SPECIALTY, "*").exec {
                    if (this.moveToFirst()) {
                        do {
                            val specialty: Specialty = getSpecialtyFromCursor(this)
                            list.add(specialty)
                        } while (this.moveToNext())
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("logException", "getProductListFromShoppingCartDatabase Exception: $e")
        }
        return list
    }

    private fun getSpecialtyFromCursor(cursor: Cursor): Specialty {
        return Specialty(
            cursor.getInt(0),
            cursor.getString(1)
        )
    }

    override fun getDatabaseEmployeeListBySpecialty(context: Context, specialtyId: Int): ArrayList<DatabaseEmployee> {
        val idList: ArrayList<Int> = getEmployeeIdListBySpecialty(context, specialtyId)
        val employeeList = ArrayList<DatabaseEmployee>()
        for (id: Int in idList) {
            val databaseEmployee = getDatabaseEmployeeById(context, id)
            if (databaseEmployee != null) {
                employeeList.add(databaseEmployee)
            }
        }
        return employeeList
    }

    private fun getEmployeeIdListBySpecialty(context: Context, specialtyId: Int): ArrayList<Int> {
        val idList: ArrayList<Int> = ArrayList()
        try {
            val databaseHelper = EmployeeDatabaseHelper.getInstance(context)
            var cursor: Cursor? = null

            databaseHelper.use {
                cursor = rawQuery(
                    "SELECT ${EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID} " +
                            "FROM ${EmployeeDatabaseInfo.TABLE_SPECIALTY_OF_EMPLOYEE} " +
                            "WHERE ${EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID} " +
                            "LIKE $specialtyId ", null
                )
                if (cursor != null) {
                    val cursorNotNull: Cursor = cursor as Cursor
                    if (cursorNotNull.moveToFirst()) {
                        do {
                            val id = cursorNotNull.getInt(0)
                            idList.add(id)
                        } while (cursorNotNull.moveToNext())
                    }
                    cursorNotNull.close()
                }
                cursor?.close()
            }

        } catch (e: Exception) {
            Log.d("logException", "getEmployeeIdListBySpecialty  Exception: $e")
        }
        return idList
    }

    private fun getDatabaseEmployeeById(context: Context, id: Int): DatabaseEmployee? {
        var databaseEmployee: DatabaseEmployee? = null
        try {
            val databaseHelper = EmployeeDatabaseHelper.getInstance(context)
            var cursor: Cursor? = null

            databaseHelper.use {
                cursor = rawQuery(
                    "SELECT * " +
                            "FROM ${EmployeeDatabaseInfo.TABLE_EMPLOYEE} " +
                            "WHERE ${EmployeeDatabaseInfo.COLUMN_EMPLOYEE_ID} " +
                            "LIKE $id " +
                            "ORDER BY ${EmployeeDatabaseInfo.COLUMN_EMPLOYEE_LAST_NAME}, ${EmployeeDatabaseInfo.COLUMN_EMPLOYEE_FIRST_NAME}",
                    null
                )
                if (cursor != null) {
                    val cursorNotNull: Cursor = cursor as Cursor
                    if (cursorNotNull.moveToFirst()) {
                        val employeeFromCursor = DatabaseEmployee(
                            cursorNotNull.getInt(EmployeeDatabaseInfo.COLUMN_EMPLOYEE_ID_INDEX),
                            cursorNotNull.getString(EmployeeDatabaseInfo.COLUMN_EMPLOYEE_FIRST_NAME_INDEX),
                            cursorNotNull.getString(EmployeeDatabaseInfo.COLUMN_EMPLOYEE_LAST_NAME_INDEX),
                            cursorNotNull.getString(EmployeeDatabaseInfo.COLUMN_EMPLOYEE_BIRTHDAY_INDEX),
                            cursorNotNull.getString(EmployeeDatabaseInfo.COLUMN_EMPLOYEE_AGE_INDEX),
                            cursorNotNull.getString(EmployeeDatabaseInfo.COLUMN_EMPLOYEE_IMAGE_PATH_INDEX)
                        )
                        databaseEmployee = employeeFromCursor
                    }
                    cursorNotNull.close()
                }
                cursor?.close()
            }

        } catch (e: Exception) {
            Log.d("logException", "getDatabaseEmployeeById  Exception: $e")
        }
        return databaseEmployee
    }


    override fun getSpecialtyListByDatabaseEmployee(context: Context, employeeId: Int): ArrayList<String> {
        val specialtyIdList = getSpecialtyIdListByEmployee(context, employeeId)
        val specialtyNameList = ArrayList<String>()
        try {
            val databaseHelper = EmployeeDatabaseHelper.getInstance(context)
            var cursor: Cursor? = null
            for (specialtyId in specialtyIdList) {
                databaseHelper.use {
                    cursor = rawQuery(
                        "SELECT ${EmployeeDatabaseInfo.COLUMN_SPECIALTY_NAME} " +
                                "FROM ${EmployeeDatabaseInfo.TABLE_SPECIALTY} " +
                                "WHERE ${EmployeeDatabaseInfo.COLUMN_SPECIALTY_ID} " +
                                "LIKE $specialtyId ", null
                    )
                    if (cursor != null) {
                        val cursorNotNull: Cursor = cursor as Cursor
                        if (cursorNotNull.moveToFirst()) {
                            do {
                                val specialtyName = cursorNotNull.getString(0)
                                specialtyNameList.add(specialtyName)
                            } while (cursorNotNull.moveToNext())
                        }
                        cursorNotNull.close()
                    }
                }
                cursor?.close()
            }

        } catch (e: Exception) {
            Log.d("logException", " getSpecialtyIdListByEmployee Exception: $e")
        }
        return specialtyNameList
    }

    private fun getSpecialtyIdListByEmployee(context: Context, employeeId: Int): ArrayList<Int> {
        val idList: ArrayList<Int> = ArrayList()
        try {
            val databaseHelper = EmployeeDatabaseHelper.getInstance(context)
            var cursor: Cursor? = null

            databaseHelper.use {
                cursor = rawQuery(
                    "SELECT ${EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID} " +
                            "FROM ${EmployeeDatabaseInfo.TABLE_SPECIALTY_OF_EMPLOYEE} " +
                            "WHERE ${EmployeeDatabaseInfo.COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID} " +
                            "LIKE $employeeId ", null
                )
                if (cursor != null) {
                    val cursorNotNull: Cursor = cursor as Cursor
                    if (cursorNotNull.moveToFirst()) {
                        do {
                            val id = cursorNotNull.getInt(0)
                            idList.add(id)
                        } while (cursorNotNull.moveToNext())
                    }
                    cursorNotNull.close()
                }
                cursor?.close()
            }

        } catch (e: Exception) {
            Log.d("logException", " getSpecialtyIdListByEmployee Exception: $e")
        }
        return idList
    }


}