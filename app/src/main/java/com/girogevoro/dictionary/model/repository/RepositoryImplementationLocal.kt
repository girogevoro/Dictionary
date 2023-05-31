package com.girogevoro.dictionary.model.repository

import com.girogevoro.data.AppState
import com.girogevoro.data.DataModel
import com.girogevoro.dictionary.model.datasource.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource:
                                    DataSourceLocal<List<DataModel>>
) :
    RepositoryLocal<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}

