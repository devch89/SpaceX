package com.example.spacexapitest

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.spacexapitest.view.CompanyFragment
import com.example.spacexapitest.view.LaunchesFragment
import com.example.spacexapitest.viewmodel.SpaceXViewModel
import java.util.*


class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<SpaceXViewModel>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.main_toolbar)).also {
            title = ""
        }
        val textView: TextView = findViewById(R.id.tv_toolbar_title)
        textView.setText(R.string.toolbar_title)

//        Fragment Ktx
        supportFragmentManager.commit {
            replace(R.id.company_fragment_placeholder, CompanyFragment())
            replace(R.id.launches_fragment_placeholder, LaunchesFragment())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        val itemAsc = menu?.findItem(R.id.sort_asc)
        val itemDesc = menu?.findItem(R.id.sort_desc)

        itemAsc?.setOnMenuItemClickListener {
            viewModel.sortAsc()
            true
        }

        itemDesc?.setOnMenuItemClickListener {
            viewModel.sortDesc()
            true
        }

        return true
    }
}



