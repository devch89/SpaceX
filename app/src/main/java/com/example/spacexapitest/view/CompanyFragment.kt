package com.example.spacexapitest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.spacexapitest.R
import com.example.spacexapitest.databinding.SpacexCompanyInfoDisplayBinding
import com.example.spacexapitest.model.remote.CompanyResponse
import com.example.spacexapitest.viewmodel.SpaceXViewModel

private const val TAG = "CompanyFragment"

class CompanyFragment: Fragment() {

    private lateinit var binding: SpacexCompanyInfoDisplayBinding
    private val viewModel: SpaceXViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = SpacexCompanyInfoDisplayBinding.inflate(inflater,container,false)
        // we use observe because Live data is an observable
        viewModel.company.observe(viewLifecycleOwner){
            updateView(it)
        }
//            object : Observer<CompanyResponse> {
//            override fun onChanged(t: CompanyResponse?) {
//                TODO("Not yet implemented")
//            }
//        })
        return binding.root
    }

    private fun updateView(data: CompanyResponse) {
        Log.d(TAG, "updateView: $data")
        binding.apply {
            tvSpxCompanyInfo.text =
                getString(R.string.company_info,
                data.name,
                data.founder,
                data.founded,
                data.employees,
                data.launchSite,
                data.valuation)
        }
    }

}