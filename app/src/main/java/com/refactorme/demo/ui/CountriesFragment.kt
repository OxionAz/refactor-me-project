package com.refactorme.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.refactorme.demo.databinding.CountriesFragmentBinding
import com.refactorme.demo.ui.extention.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountriesFragment : Fragment() {

    private lateinit var binding: CountriesFragmentBinding

    private val viewModel: CountriesViewModel by viewModel()

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
        observe(viewModel.countriesList, adapter::setData)
        observe(viewModel.loading) { binding.loadButton.isEnabled = !it }
        observe(viewModel.errLiveData) { Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show() }

        binding.countriesList.adapter = adapter
        binding.countriesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.loadButton.setOnClickListener { viewModel.loadCountries() }
    }
}