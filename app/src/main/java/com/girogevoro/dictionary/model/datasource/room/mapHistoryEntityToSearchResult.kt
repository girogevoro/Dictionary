package com.girogevoro.dictionary.model.datasource.room

import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.data.Meanings
import com.girogevoro.dictionary.model.data.Translation

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModel> {
    val dataModel = ArrayList<DataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            val meanings = ArrayList<Meanings>()
            meanings.add(Meanings(Translation(entity.description), entity.urlImage))
            dataModel.add(DataModel(entity.word, meanings))
        }
    }
    return dataModel
}
