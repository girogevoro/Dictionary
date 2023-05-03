package com.girogevoro.dictionary.di

import com.girogevoro.dictionary.model.data.DataModel
import com.girogevoro.dictionary.model.datasource.retrofit.RetrofitImplementation
import com.girogevoro.dictionary.model.datasource.room.RoomDataBaseImplementation
import com.girogevoro.dictionary.model.repository.Repository
import com.girogevoro.dictionary.model.repository.RepositoryImplementation
import com.girogevoro.dictionary.view.main.MainInteractor
import com.girogevoro.dictionary.view.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object KoinModules {
    val application = module {
        single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
            RepositoryImplementation(RetrofitImplementation())
        }
        single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
            RepositoryImplementation(RoomDataBaseImplementation())
        }
    }

    val mainScreen = module {
        factory {
            MainInteractor(
                get(named(NAME_REMOTE)),
                get(named(NAME_LOCAL))
            )
        }
        factory { MainViewModel(get()) }
    }
}