package com.girogevoro.dictionary.presenter

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}

