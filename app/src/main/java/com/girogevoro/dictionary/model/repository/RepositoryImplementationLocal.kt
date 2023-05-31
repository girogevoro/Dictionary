package com.girogevoro.dictionary.model.repository

import com.girogevoro.dictionary.model.data.AppState
import com.girogevoro.dictionary.model.data.DataModel
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

