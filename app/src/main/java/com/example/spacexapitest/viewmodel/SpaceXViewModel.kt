package com.example.spacexapitest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexapitest.model.remote.CompanyResponse
import com.example.spacexapitest.model.remote.LaunchRocket
import com.example.spacexapitest.model.remote.SpaceXNetwork
import kotlinx.coroutines.*

private const val TAG = "SpaceXViewModel"
class SpaceXViewModel : ViewModel() {

    private val _launches = MutableLiveData<List<LaunchRocket>>()
    val launches: LiveData<List<LaunchRocket>>
        get() = _launches

    private val _company = MutableLiveData<CompanyResponse>()
    val company: LiveData<CompanyResponse>
        get() = _company


    /**
     * Suspend functions(async/non blocking)
     * Coroutine Scope
     * Container of Coroutines.
     * Entry point for the suspend action.
     * A coroutine will be suspended and resumed from the Corotuine Scope.
     * Coroutines shares the memory allocation
     * Defines the Coroitine Builders
     * Types: GlobalScope - attached to the application lifecycle (not often used)
     *        LifecycleScope - attached to the lifecycle of the view (fragment or the activity)
     *        ViewModelScope - attached to the lifecycle of the viewmodel.
     *        CoroutineScope - parent definition
     */

    private val exHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

    }

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main + exHandler)

    /**
     * Dispatchers controlles the access to a ThreadPool.
     * Its part of the Corotuine Context.
     * Main. - give access to the Main Thread of the current process.
     * Default - small thread pool to do background operations(CPU Intensive).
     * IO - Medium to large Thread pool for INPUT/OUTPUT operations.(Starts and receives in a different thread)
     * Unconfined - Don't use in Android. It will jump between threads available.
     */

    private val currentDispatcher = Dispatchers.IO

    init {
//        Launch and forget
//        we will evaluate the entry point 3 times or more until the coroutines are finished
//        it would be better to create 2 coroutines scopes. we also may be able to create a separate suspend fun and call it inside the coroutine scope
//        suspends funs can only be called inside other suspend funs or inside coroutineScopes
//        launch is the promise of doing the job
            coroutineScope.launch {
            Log.d(TAG, "init: company")
            val companyResponse = SpaceXNetwork.spaceXApi.getCompanyInfo()

            if (companyResponse.isSuccessful) {
                Log.d(TAG, "init: isSuccessful")
                companyResponse.body()?.let {
                    Log.d(TAG, "init: letSuccessful $it")
//                    we are inside the main thread so we just need to setValue and not postValue
                    _company.value = it

                }
            }
//           async {  } we are able to call it in one coroutine scope
        }

//        We will launch another coroutine where we done expect a return but inside we will
        coroutineScope.launch {
            Log.d(TAG, "launches: ")
            //        async is the promise of giving a return of something
            val launches = coroutineScope.async {
                Log.d(TAG, "launch scope init: ")
                val allLaunches = SpaceXNetwork.spaceXApi.getAllLaunches()
                Log.d(TAG, "launches async: $allLaunches")
                allLaunches.docs.map { launchItem ->
                    Log.d(TAG, "launches map : $launchItem")
//             we have to make another network call because we need to get inside of getRocketInfo
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
}