package com.girogevoro.dictionary.presenter

import com.girogevoro.dictionary.model.data.AppState
import com.girogevoro.dictionary.view.base.View

interface Presenter<T : AppState, V : View> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word:String, idOnline:Boolean)
}
