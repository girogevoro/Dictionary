package com.girogevoro.dictionary.di

import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.repository.Repository
import com.girogevoro.dictionary.view.main.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repoRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repoLocal: Repository<List<DataModel>>
    ) = MainInteractor(repoRemote, repoLocal)
}
