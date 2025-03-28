package com.refactorme.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.refactorme.demo.databinding.CountriesFragmentBinding

class CountriesFragment(
    private val fields: String
) : Fragment() {

    private lateinit var binding: CountriesFragmentBinding

    private val viewModel: CountriesViewModel by viewModels()
    private val adapter = CountriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountriesFragmentBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        viewModel.countriesList.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.countriesList.adapter = adapter
        binding.countriesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.loadButton.setOnClickListener {
            viewModel.loadCountries(fields)
        }
    }
}