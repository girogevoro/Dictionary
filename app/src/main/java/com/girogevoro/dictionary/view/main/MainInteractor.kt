package com.girogevoro.dictionary.view.main

import com.girogevoro.dictionary.model.data.AppState
import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.repository.Repository
import com.girogevoro.dictionary.presenter.Interactor

class MainInteractor constructor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return if (fromRemoteSource) {
            AppState.Success(remoteRepository.getData(word))
        } else {
            AppState.Success(localRepository.getData(word))
        }
    }
}
