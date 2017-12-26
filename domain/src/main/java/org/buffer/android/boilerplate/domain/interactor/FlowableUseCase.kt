package org.buffer.android.boilerplate.domain.interactor

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.buffer.android.boilerplate.domain.executor.ThreadExecutor



/**
 * Abstract class for a UseCase that returns an instance of a [Flowable].
 */
abstract class FlowableUseCase<T, in Params> constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread) {

    /**
     * Builds a [Flowable] which will be used when the current [FlowableUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params? = null): Flowable<T>

    /**
     * Executes the current use case.
     */
    open fun execute(params: Params? = null): Flowable<T> {
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
    }

}