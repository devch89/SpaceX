package com.example.spacexapitest

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.spacexapitest.model.remote.CompanyResponse
import com.example.spacexapitest.view.CompanyFragment
import com.example.spacexapitest.view.LaunchesFragment
import com.example.spacexapitest.viewmodel.SpaceXViewModel
import java.util.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val viewModel: SpaceXViewModel by lazy {
        ViewModelProvider(this)[SpaceXViewModel::class.java]
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// region TimeTest
//        val outputFormat: DateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm")
//        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
//
//
////        val day = "2006-03-24T22:30:00.000Z"
////        val simpleDateFormat =
////            SimpleDateFormat("MM/dd/yyyy HH:mm")
////        val launchDate = simpleDateFormat.format(day)
//
//        val inputText = "2006-03-24T22:30:00.000Z"
//        val date: Date = inputFormat.parse(inputText)
//        val outputText: String = outputFormat.format(date)
//
//        val time = Calendar.getInstance().time
//        val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm")
//        val current = formatter.format(time)
//
//
//        val currentDate = current
//        val finalDate = outputText
//        val date1: Date
//        val date2: Date
//        val dates = SimpleDateFormat("MM/dd/yyyy HH:mm")
//        date1 = dates.parse(currentDate)
//        date2 = dates.parse(finalDate)
//        val difference: Long = abs(date1.time - date2.time)
//        val differenceDates = difference / (24 * 60 * 60 * 1000)
//        val dayDifference = differenceDates.toString()
//
//        Log.d(TAG, "onCreate: Current date: $dayDifference")
//    }
//        endregion
//    region    Traditional Way
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.company_fragment_placeholder, CompanyFragment())
//            .replace(R.id.launches_fragment_placeholder, LaunchesFragment())
//            .commit()
//        endregion
//        Fragment Ktx
        supportFragmentManager.commit {
            replace(R.id.company_fragment_placeholder, CompanyFragment())
            replace(R.id.launches_fragment_placeholder, LaunchesFragment())
        }

    }



}


