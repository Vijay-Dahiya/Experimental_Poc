package com.example.experiment.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import com.example.experiment.databinding.ActivityObservableDemoBinding
import com.example.experiment.viewmodels.ObservableDemo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ObservableDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityObservableDemoBinding
    private lateinit var viewModel: ObservableDemo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityObservableDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ObservableDemo::class.java]
        setOnClicks()

    }

    private fun setOnClicks() {
        binding.btnLiveData.setOnClickListener{
            viewModel.triggerLiveData()
        }

        binding.btnStateFlow.setOnClickListener {
            viewModel.triggerStateFlow()
        }

        binding.btnFlow.setOnClickListener {
            viewModel.triggerFlow()
        }

        binding.btnSharedFlow.setOnClickListener {
            viewModel.triggerSharedFlow()
        }
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        viewModel.liveData.observe(this) {
            binding.tvLiveData.text = it
        }

        lifecycleScope.launch {
//            withStarted {
//                viewModel.stateFlow.collectLatest {
//                    binding.tvFlow.text = it
//                }
//            }
        }

    }
}