package com.girogevoro.stopwatch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.girogevoro.stopwatch.model.StopwatchStateHolder
import kotlinx.coroutines.CoroutineScope

class StopwatchViewModelFactory(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope: CoroutineScope
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        StopwatchViewModel(stopwatchStateHolder, scope) as T

}