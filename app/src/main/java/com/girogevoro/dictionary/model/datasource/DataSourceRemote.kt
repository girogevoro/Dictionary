package com.girogevoro.dictionary.model.datasource

import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.datasource.retrofit.RetrofitImplementation
import com.girogevoro.dictionary.model.datasource.room.RoomDataBaseImplementation
import io.reactivex.Observable


class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}