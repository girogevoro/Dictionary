package com.girogevoro.dictionary.view.base

import com.girogevoro.dictionary.model.data.AppState

interface View {
    fun renderData(appState: AppState)
}