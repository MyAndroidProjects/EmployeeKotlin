package com.lopatin.employeekotlin.model.database

/**
 * в базе данных три таблицы,
 * "specialty", "employee" и "specialty_of_employee"
 */
object EmployeeDatabaseInfo {
    const val DATABASE_NAME = "employeeDatabase"

    const val TABLE_SPECIALTY = "specialty"
    const val TABLE_EMPLOYEE = "employee"
    const val TABLE_SPECIALTY_OF_EMPLOYEE = "specialty_of_employee"

    // таблица "specialty"
    const val COLUMN_SPECIALTY_ID = "_id"
    const val COLUMN_SPECIALTY_NAME = "name"

    // таблица "employee"
    const val COLUMN_EMPLOYEE_ID = "_id"
    const val COLUMN_EMPLOYEE_FIRST_NAME = "f_name"
    const val COLUMN_EMPLOYEE_LAST_NAME = "l_name"
    const val COLUMN_EMPLOYEE_BIRTHDAY = "birthday"
    const val COLUMN_EMPLOYEE_AGE = "age"
    const val COLUMN_EMPLOYEE_IMAGE_PATH = "avatar_url"

    // индексы столбцов таблицы employee
    const val COLUMN_EMPLOYEE_ID_INDEX = 0
    const val COLUMN_EMPLOYEE_FIRST_NAME_INDEX = 1
    const val COLUMN_EMPLOYEE_LAST_NAME_INDEX = 2
    const val COLUMN_EMPLOYEE_BIRTHDAY_INDEX = 3
    const val COLUMN_EMPLOYEE_AGE_INDEX = 4
    const val COLUMN_EMPLOYEE_IMAGE_PATH_INDEX = 5

    // таблица "specialty_of_employee"
    const val COLUMN_SPECIALTY_OF_EMPLOYEE_ID = "_id"
    const val COLUMN_SPECIALTY_OF_EMPLOYEE_SPECIALTY_ID = "specialty_id"
    const val COLUMN_SPECIALTY_OF_EMPLOYEE_EMPLOYEE_ID = "employee_id"
}