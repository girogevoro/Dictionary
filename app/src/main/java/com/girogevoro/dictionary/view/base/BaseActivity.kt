package com.girogevoro.dictionary.view.base

import androidx.appcompat.app.AppCompatActivity
import com.girogevoro.dictionary.model.data.AppState

abstract class BaseActivity<T : AppState> : AppCompatActivity() {
    abstract val model: BaseViewModel<T>
    abstract fun renderData(appState: T)
}
