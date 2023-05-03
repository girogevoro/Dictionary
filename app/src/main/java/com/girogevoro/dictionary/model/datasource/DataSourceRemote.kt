package com.girogevoro.dictionary.model.datasource

import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.datasource.retrofit.RetrofitImplementation
import com.girogevoro.dictionary.model.datasource.room.RoomDataBaseImplementation


class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}