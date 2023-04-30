package com.girogevoro.dictionary.view.main

import com.girogevoro.dictionary.di.NAME_LOCAL
import com.girogevoro.dictionary.di.NAME_REMOTE
import com.girogevoro.dictionary.model.data.AppState
import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.repository.Repository
import com.girogevoro.dictionary.presenter.Interactor
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}
