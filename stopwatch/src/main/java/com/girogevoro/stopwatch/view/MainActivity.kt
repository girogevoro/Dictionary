package com.girogevoro.stopwatch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.girogevoro.stopwatch.databinding.ActivityMainBinding
import com.girogevoro.stopwatch.model.*
import com.girogevoro.stopwatch.viewModel.StopwatchViewModel
import com.girogevoro.stopwatch.viewModel.StopwatchViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() {
            return _binding!!
        }

    private val timestampProvider: TimestampProvider = TimestampProviderImpl()

    private lateinit var modelFirst: StopwatchViewModel
    private lateinit var modelSecond: StopwatchViewModel

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        scope.launch {
            modelFirst.ticker.collect {
                binding.textTimeOne.text = it
            }
        }

        scope.launch {
            modelSecond.ticker.collect {
                binding.textTimeTwo.text = it
            }
        }
    }

    private fun init() {
        modelFirst = StopwatchViewModelFactory(
            stopwatchStateHolder = StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider),
                ),
                ElapsedTimeCalculator(timestampProvider),
                TimestampMillisecondsFormatter()
            ),
            CoroutineScope(
                Dispatchers.Default
                        + SupervisorJob()
            )
        ).create(StopwatchViewModel::class.java)

        modelSecond = StopwatchViewModelFactory(
            stopwatchStateHolder = StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider),
                ),
                ElapsedTimeCalculator(timestampProvider),
                TimestampMillisecondsFormatter()
            ),
            CoroutineScope(
                Dispatchers.Default
                        + SupervisorJob()
            )
        ).create(StopwatchViewModel::class.java)

        binding.buttonStartOne.setOnClickListener {
            modelFirst.start()
        }
        binding.buttonPauseOne.setOnClickListener {
            modelFirst.pause()
        }
        binding.buttonStopOne.setOnClickListener {
            modelFirst.stop()
        }
        binding.buttonStartTwo.setOnClickListener {
            modelSecond.start()
        }
        binding.buttonPauseTwo.setOnClickListener {
            modelSecond.pause()
        }
        binding.buttonStopTwo.setOnClickListener {
            modelSecond.stop()
        }
    }
}

