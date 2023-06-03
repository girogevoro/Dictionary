package com.girogevoro.dictionary.di

import android.content.Context
import androidx.room.Room
import com.girogevoro.data.DataModel
import com.girogevoro.dictionary.model.datasource.retrofit.RetrofitImplementation
import com.girogevoro.dictionary.model.datasource.room.HistoryDao
import com.girogevoro.dictionary.model.datasource.room.HistoryDataBase
import com.girogevoro.dictionary.model.datasource.room.RoomDataBaseImplementation
import com.girogevoro.dictionary.model.repository.Repository
import com.girogevoro.dictionary.model.repository.RepositoryImplementation
import com.girogevoro.dictionary.model.repository.RepositoryImplementationLocal
import com.girogevoro.dictionary.model.repository.RepositoryLocal
import com.girogevoro.dictionary.view.history.HistoryFragment
import com.girogevoro.dictionary.view.history.HistoryInteractor
import com.girogevoro.dictionary.view.history.HistoryViewModel
import com.girogevoro.dictionary.view.main.MainActivity
import com.girogevoro.dictionary.view.main.MainInteractor
import com.girogevoro.dictionary.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object KoinModules {
    val application = module {
        single<HistoryDataBase> {
            Room.databaseBuilder(
                get<Context>(), HistoryDataBase::class.java,
                "HistoryDB"
            ).build()
        }

        single<HistoryDao> { get<HistoryDataBase>().historyDao() }

        single<Repository<List<DataModel>>>() {
            RepositoryImplementation(RetrofitImplementation())
        }
        single<RepositoryLocal<List<DataModel>>>() {
            RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
        }
    }

    val mainScreen = module {

        scope(named<MainActivity>()) {
            scoped {
                MainInteractor(
                    remoteRepository = get(),
                    localRepository = get())
            }
            viewModel { MainViewModel(interactor = get()) }
        }

    }

    val historyScreen = module {
        scope(named<HistoryFragment>()) {
            scoped {
                HistoryInteractor(
                    repositoryRemote = get(),
                    repositoryLocal = get())
            }
            viewModel { HistoryViewModel(interactor = get()) }
        }
    }
}