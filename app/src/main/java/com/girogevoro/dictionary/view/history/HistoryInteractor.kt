package com.girogevoro.dictionary.view.history

import com.girogevoro.dictionary.model.data.AppState
import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.repository.RepositoryLocal
import com.girogevoro.dictionary.model.repository.Repository
import com.girogevoro.dictionary.presenter.Interactor

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean):
            AppState{
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
