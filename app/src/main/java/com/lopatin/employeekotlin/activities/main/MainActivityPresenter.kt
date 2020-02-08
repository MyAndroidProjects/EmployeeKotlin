package com.lopatin.employeekotlin.activities.main

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.lopatin.employeekotlin.fragments.specialty.SpecialtyListFragment
import com.lopatin.employeekotlin.model.data.DatabaseEmployee
import com.lopatin.employeekotlin.model.data.Employee
import com.lopatin.employeekotlin.model.data.EmployeeList
import com.lopatin.employeekotlin.model.database.EmployeeDatabaseManager
import com.lopatin.employeekotlin.network.JsonConverterRetrofit
import com.lopatin.employeekotlin.R
import com.lopatin.employeekotlin.navigation.NavigationContract
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter


object MainActivityPresenter : MainActivityContract.Presenter, NavigationContract.MainActivityPresenter {

    enum class FragmentType {
        SPECIALTY,
        EMPLOYEE_LIST,
        EMPLOYEE_INFO
    }

    private val navigator: MainActivityContract.Navigator = MainActivityNavigator
    private var view: MainActivityContract.View? = null

    private var lastFragmentType: FragmentType = MainActivityPresenter.FragmentType.SPECIALTY
    private var currentFragmentType: FragmentType = MainActivityPresenter.FragmentType.SPECIALTY
        set(value) {
            lastFragmentType = field
            field = value
            when (value) {
                MainActivityPresenter.FragmentType.SPECIALTY -> {
                    view?.showBackButton(false)
                    view?.setToolbarTitle(R.string.toolbar_title_specialty)
                }
                MainActivityPresenter.FragmentType.EMPLOYEE_LIST -> {
                    view?.showBackButton(true)
                    view?.setToolbarTitle(R.string.toolbar_title_employees)
                }
                MainActivityPresenter.FragmentType.EMPLOYEE_INFO -> {
                    view?.showBackButton(true)
                    view?.setToolbarTitle(R.string.toolbar_title_employee_info)
                }
            }
        }
    private var isFirstStartActivity: Boolean = true
    private const val URL: String = "testTask.json"
    private const val PLACE_HOLDER = """««"""

    override fun setViewToPresenter(view: MainActivityContract.View?) {
        MainActivityPresenter.view = view
    }

    override fun buttonBackPressed() {
        if (currentFragmentType == MainActivityPresenter.FragmentType.SPECIALTY) {
            view?.callSuperBackPressed()
            view?.callSuperBackPressed()
        } else {
            currentFragmentType = lastFragmentType
            view?.callSuperBackPressed()
        }
    }

    override fun activityIsCreating(context: Context) {
        if (isFirstStartActivity) {
            getDataFromRawAndSetToDatabase(context)
            isFirstStartActivity = false
        } else {
            createSpecialtyFragment()
        }
    }

    /**
     * getting data from server replaced getting data from raw/all_employees.json
     */
    private fun getDataFromRaw(context: Context): EmployeeList {
        val inputStream = context.resources.openRawResource(R.raw.all_employees)
        val writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int = reader.read(buffer)
            while (n != -1) {
                writer.write(buffer, 0, n)
                n = reader.read(buffer)
            }
        } finally {
            inputStream.close()
        }

        val jsonString = writer.toString()
        val gson: Gson = Gson()
        val employeeListFromJson: EmployeeList = gson.fromJson(jsonString.toString(), EmployeeList::class.java)
        return employeeListFromJson
    }

    /**
     * getting data from server replaced getting data from raw/all_employees.json with coroutines
     */
    private fun getDataFromRawAndSetToDatabase(context: Context) {
        view?.showProgressBar(true)
        CoroutineScope(Dispatchers.Main + Job()).launch {
            // при ошибке получения данныx выбрасывает ошибку
            try {
                withContext(Dispatchers.Default) {
                    /**
                     * иммитация задержки ответа с сервера
                     */
                    delay(400)
                    val employeeListResponse = getDataFromRaw(context)
                    val employeeList = employeeListResponse.employeeList
                    employeeList ?: return@withContext
                    var i = 0
                    for (employee: Employee in employeeList.iterator()) {
                        val databaseEmployee = convertEmployeeToDatabaseEmployee(employee)
                        databaseEmployee ?: continue
                        fillDatabase(context, databaseEmployee, employee)
                        i++
                    }
                    withContext(Dispatchers.Main) {
                        view?.showProgressBar(false)
                        createSpecialtyFragment()
                    }
                }
            } catch (ex: Exception) {
                Log.d("logException", "Exception: $ex")
            }
        }
    }

    /**
     * getting data from server replaced getting data from raw/all_employees.json
     *  get data from url
     */
   private fun getDataFromNetAndSetToDatabase(context: Context) {
       val jsonApi = JsonConverterRetrofit.retrofitApiJson
        val observer = object : Observer<EmployeeList> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(employeeList: EmployeeList) {
                view?.showProgressBar(true)
                val list = employeeList.employeeList
                list ?: return
                var i = 0
                for (employee: Employee in list.iterator()) {
                    val databaseEmployee = convertEmployeeToDatabaseEmployee(employee)
                    databaseEmployee ?: continue
                    fillDatabase(context, databaseEmployee, employee)
                    i++
                }
            }

            override fun onError(e: Throwable) {
                Log.d("logError", "getDataFromNet onError $e")
            }

            override fun onComplete() {
                view?.showProgressBar(false)
                createSpecialtyFragment()
            }
        }

        jsonApi.getObservableData(URL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun fillDatabase(context: Context, databaseEmployee: DatabaseEmployee, employee: Employee) {
        val model: MainActivityContract.Model = EmployeeDatabaseManager()
        val employeeId = model.putEmployeeToEmployeeTable(context, databaseEmployee)
        val specialtyList = employee.specialty
        if (specialtyList != null) {
            for (specialty in specialtyList.iterator()) {
                specialty.specialtyId ?: continue
                val specialtyIdLong = specialty.specialtyId.toLong()
                specialty.name ?: continue
                model.putSpecialtyToSpecialtyTable(context, specialtyIdLong, specialty.name)
                model.putEmployeeAndSpecialtyToSpecialtyOfEmployeeTable(context, specialtyIdLong, employeeId)
            }
        }
    }

    private fun createSpecialtyFragment() {
        currentFragmentType = MainActivityPresenter.FragmentType.SPECIALTY
        navigator.createFragment(
            SpecialtyListFragment.getInstance()
        )
    }

    private fun convertEmployeeToDatabaseEmployee(employee: Employee): DatabaseEmployee? {
        val id: Int = -1
        var firstName = ""
        var lastName = ""
        var birthdayDate = PLACE_HOLDER
        var age = ""
        var avatarUrl = ""

        val fName = employee.fName
        if (fName != null) {
            firstName = convertStringToNameTemplate(fName)
        }
        val lName = employee.lName
        if (lName != null) {
            lastName = convertStringToNameTemplate(lName)
        }
        val birthday = employee.birthday
        if (birthday != null) {
            birthdayDate = convertBirthdayData(birthday)
        }
        age = calculateAge(birthdayDate)
        val avatarPath = employee.avatarUrl
        if (avatarPath != null) {
            avatarUrl = avatarPath
        }

        return DatabaseEmployee(
            id,
            firstName,
            lastName,
            birthdayDate,
            age,
            avatarUrl
        )
    }

    private fun convertStringToNameTemplate(string: String): String {
        val stringStringBuilder = StringBuilder(string)
        var i = 0
        for (c in stringStringBuilder) {
            if (i == 0) {
                stringStringBuilder.setCharAt(i, c.toUpperCase())
            } else {
                stringStringBuilder.setCharAt(i, c.toLowerCase())
                c.toLowerCase()
            }
            i++
        }
        return String(stringStringBuilder)
    }

    private fun convertBirthdayData(date: String): String {
        val dateBirthday: Date?
        val regexYMD = Regex(pattern = """\d{4}-\d{2}-\d{2}""")
        val regexDMY = Regex(pattern = """\d{2}-\d{2}-\d{4}""")

        dateBirthday = when {
            regexYMD.matches(input = date) -> {
                val dateFormat = "yyyy-MM-dd"
                val formatDate = SimpleDateFormat(dateFormat, Locale.getDefault())
                formatDate.parse(date)
            }
            regexDMY.matches(input = date) -> {
                val dateFormat = "dd-MM-yyyy"
                val formatDate = SimpleDateFormat(dateFormat, Locale.getDefault())
                formatDate.parse(date)
            }
            else -> return PLACE_HOLDER
        }

        val dateFormat = "dd.MM.yyyy"
        val formatDate = SimpleDateFormat(dateFormat, Locale.getDefault())
        dateBirthday?: return PLACE_HOLDER
        return formatDate.format(dateBirthday)
    }

    private fun calculateAge(date: String): String {

        if (date == PLACE_HOLDER) {
            return PLACE_HOLDER
        }
        val year: Int
        val month: Int
        val day: Int
        val dateParts = date.split(".")
        year = dateParts[2].toInt()
        month = dateParts[1].toInt()
        day = dateParts[0].toInt()

        val currentTime: Date = Calendar.getInstance().time
        val dateFormat = "dd.MM.yyyy"
        val formatDate = SimpleDateFormat(dateFormat, Locale.getDefault())
        val currentDate = formatDate.format(currentTime)
        val currentYear: Int
        val currentMonth: Int
        val currentDay: Int
        val currentDateParts = currentDate.split(".")
        currentYear = currentDateParts[2].toInt()
        currentMonth = currentDateParts[1].toInt()
        currentDay = currentDateParts[0].toInt()

        val ageYear = currentYear - year
        if (currentMonth < month) {
            ageYear - 1
        } else if (currentMonth == month) {
            if (currentDay < day) {
                ageYear - 1
            }
        }
        return ageYear.toString()
    }

    // NavigationContract.MainActivityPresenter
    override fun setFragmentType(currentFragmentType: FragmentType) {
        MainActivityPresenter.currentFragmentType = currentFragmentType
    }
}