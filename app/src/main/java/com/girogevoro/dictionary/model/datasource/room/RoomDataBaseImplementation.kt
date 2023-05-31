package com.girogevoro.dictionary.model.datasource.room

import com.girogevoro.data.AppState
import com.girogevoro.data.DataModel
import com.girogevoro.dictionary.model.datasource.DataSourceLocal

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
