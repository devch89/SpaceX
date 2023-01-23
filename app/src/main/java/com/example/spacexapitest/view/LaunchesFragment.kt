package com.example.spacexapitest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexapitest.databinding.SpacexDisplayLayoutBinding
import com.example.spacexapitest.model.remote.LaunchRocket
import com.example.spacexapitest.view.adapter.RocketAdapter
import com.example.spacexapitest.viewmodel.SpaceXViewModel

class LaunchesFragment : Fragment() {

    private lateinit var binding: SpacexDisplayLayoutBinding
    private val viewModel: SpaceXViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = SpacexDisplayLayoutBinding.inflate(inflater, container, false)

        viewModel.launches.observe(viewLifecycleOwner) {
            updateView(it)
        }
        return binding.root
    }

    private fun updateView(data: List<LaunchRocket>) {
        binding.apply {
            rvSpacexResults.layoutManager = LinearLayoutManager(context)
            rvSpacexResults.adapter = RocketAdapter(data)
        }
    }

}