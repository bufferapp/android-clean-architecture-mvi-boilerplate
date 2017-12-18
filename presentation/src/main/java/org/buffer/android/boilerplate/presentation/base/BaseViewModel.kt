package org.buffer.android.boilerplate.presentation.base

import io.reactivex.Observable

interface BaseViewModel<I : BaseIntent, S : BaseViewState> {
    fun processIntents(intents: Observable<I>)

    fun states(): Observable<S>
}