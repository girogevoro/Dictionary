package com.girogevoro.dictionary.model.datasource.room

import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.datasource.DataSource
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented") // To change body of created functions use File
        // | Settings | File Templates.
    }
}
