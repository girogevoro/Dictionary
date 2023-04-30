package com.girogevoro.dictionary.model.repository

import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.datasource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
