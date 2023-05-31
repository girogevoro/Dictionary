package com.girogevoro.dictionary.model.datasource.room

import com.girogevoro.dictionary.model.data.AppState

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {

    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(searchResult[0].text!!,
                    searchResult[0].meanings?.firstOrNull()?.translation?.translation,
                    searchResult[0].meanings?.firstOrNull()?.imageUrl)
            }
        }
        else -> null
    }
}