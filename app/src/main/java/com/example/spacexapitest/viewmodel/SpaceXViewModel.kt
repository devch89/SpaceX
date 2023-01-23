package com.example.spacexapitest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexapitest.model.remote.CompanyResponse
import com.example.spacexapitest.model.remote.LaunchRocket
import com.example.spacexapitest.model.remote.SpaceXNetwork
import kotlinx.coroutines.*


class SpaceXViewModel : ViewModel() {

    private val _launches = MutableLiveData<List<LaunchRocket>>()
    val launches: LiveData<List<LaunchRocket>>
        get() = _launches

    private val _company = MutableLiveData<CompanyResponse>()
    val company: LiveData<CompanyResponse>
        get() = _company


    private val exHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

    }

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main + exHandler)


    private val currentDispatcher = Dispatchers.IO

    init {

        coroutineScope.launch {
            val companyResponse = SpaceXNetwork.spaceXApi.getCompanyInfo()

            if (companyResponse.isSuccessful) {
                companyResponse.body()?.let {
                    _company.value = it

                }
            }
        }


        coroutineScope.launch {
            val launches = coroutineScope.async {
                val allLaunches = SpaceXNetwork.spaceXApi.getAllLaunches()
                allLaunches.docs.map { launchItem ->
                    val rocketInfo = SpaceXNetwork.spaceXApi.getRocketInfo(
                        launchItem.rocketId
                    )
                    LaunchRocket(launchItem, rocketInfo)
                }
            }
            launches.await().also {
                _launches.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel("The viewModel is cleared.")
    }

    fun sortAsc() {
        _launches.value?.sortedBy {
            it.launchItem.date_utc
        }.let {
            _launches.value = it
        }

    }

    fun sortDesc() {
        _launches.value?.sortedByDescending {
            it.launchItem.date_utc
        }.let {
            _launches.value = it
        }
    }

}

