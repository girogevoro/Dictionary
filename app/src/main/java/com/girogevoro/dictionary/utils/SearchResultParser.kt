package com.girogevoro.dictionary.utils

import com.girogevoro.data.AppState
import com.girogevoro.data.DataModel
import com.girogevoro.data.Meanings



fun parseLocalSearchResults(appState: AppState): AppState {
    return AppState.Success(mapResult(appState, false))
}

private fun mapResult(
    appState: AppState,
    isOnline: Boolean,
): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    when (appState) {
        is AppState.Success -> {
            getSuccessResultData(appState, newSearchResults)
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    appState: AppState.Success,
    newDataModels: ArrayList<DataModel>,
) {
    val dataModels: List<DataModel> = appState.data as List<DataModel>
    if (dataModels.isNotEmpty()) {
        for (searchResult in dataModels) {
            parseOnlineResult(searchResult, newDataModels)
        }
    }
}

private fun parseOnlineResult(
    dataModel: DataModel,
    newDataModels:
    ArrayList<DataModel>,
) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings!!) {
            if (meaning.translation != null &&
                !meaning.translation!!.translation.isNullOrBlank()
            ) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.text, newMeanings))
        }
    }
}

