package com.girogevoro.stopwatch.viewModel

import androidx.lifecycle.ViewModel
import com.girogevoro.stopwatch.model.StopwatchStateHolder
import kotlinx.coroutines.Job
import kotlinx.coroutines.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StopwatchViewModel(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope: CoroutineScope
) : ViewModel() {

    private var job: Job? = null
    private val mutableTicker = MutableStateFlow("")
    val ticker: StateFlow<String> = mutableTicker

    fun start() {
        startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        job?.cancel()
        job = scope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(DELAY)
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        job?.cancel()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = DEFAULT_TIMER_VALUE
    }

    companion object {
        private const val DELAY = 20L
        private const val DEFAULT_TIMER_VALUE = "00:00:000"
    }
}