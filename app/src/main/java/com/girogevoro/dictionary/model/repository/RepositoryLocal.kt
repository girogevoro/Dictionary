package com.girogevoro.dictionary.model.repository

import com.girogevoro.data.AppState

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
}
