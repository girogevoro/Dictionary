package com.girogevoro.dictionary.model.datasource

import com.girogevoro.dictionary.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
}
