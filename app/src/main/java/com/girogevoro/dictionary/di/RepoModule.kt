package com.girogevoro.dictionary.di

import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.datasource.DataSource
import com.girogevoro.dictionary.model.datasource.retrofit.RetrofitImplementation
import com.girogevoro.dictionary.model.datasource.room.RoomDataBaseImplementation
import com.girogevoro.dictionary.model.repository.Repository
import com.girogevoro.dictionary.model.repository.RepositoryImplementation
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepoRemote(
        @Named(NAME_REMOTE) dataSourceRemote: DataSource<List<DataModel>>
    ): Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepoLocal(
        @Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>
    ): Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> =
        RoomDataBaseImplementation()
}
