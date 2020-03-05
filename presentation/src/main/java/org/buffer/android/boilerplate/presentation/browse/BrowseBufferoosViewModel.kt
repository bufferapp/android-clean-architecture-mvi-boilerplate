package org.buffer.android.boilerplate.presentation.browse

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import org.buffer.android.boilerplate.presentation.base.BaseIntent
import org.buffer.android.boilerplate.presentation.base.BaseViewModel
import org.buffer.android.boilerplate.presentation.base.model.TaskStatus
import org.buffer.android.boilerplate.presentation.browse.mapper.ArticleMapper
import org.buffer.android.boilerplate.presentation.browse.mapper.BufferooMapper
import javax.inject.Inject

open class BrowseBufferoosViewModel @Inject internal constructor(
        private val browseProcessor: BrowseProcessor,
        private val articleMapper: ArticleMapper)
    : ViewModel(), BaseViewModel<BrowseIntent, BrowseUiModel> {

    private var intentsSubject: PublishSubject<BrowseIntent> = PublishSubject.create()
    private val intentFilter: ObservableTransformer<BrowseIntent, BrowseIntent> =
            ObservableTransformer<BrowseIntent, BrowseIntent> {
                it.publish {
                    Observable.merge(it.ofType(BrowseIntent.InitialIntent::class.java).take(1),
                            it.filter({ intent -> intent !is BrowseIntent.InitialIntent }))
                }
            }
    private val reducer: BiFunction<BrowseUiModel, BrowseResult, BrowseUiModel> =
            BiFunction<BrowseUiModel, BrowseResult, BrowseUiModel> { previousState, result ->
                when (result) {
                    is BrowseResult.LoadArticleTask -> {
                        when {
                            result.status == TaskStatus.SUCCESS -> BrowseUiModel.Success(
                                    result.articles?.map { articleMapper.mapToView(it) })
                            result.status == TaskStatus.FAILURE -> BrowseUiModel.Failed
                            result.status == TaskStatus.IN_FLIGHT -> BrowseUiModel.InProgress
                            else -> BrowseUiModel.Idle()
                        }
                    }
                }
            }
    private val statesSubject: Observable<BrowseUiModel> = compose()

    override fun processIntents(intents: Observable<BrowseIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<BrowseUiModel> {
        return statesSubject
    }

    private fun compose(): Observable<BrowseUiModel> {
        return intentsSubject
                .compose(intentFilter)
                .map { this.actionFromIntent(it) }
                .compose(browseProcessor.actionProcessor)
                .scan<BrowseUiModel>(BrowseUiModel.Idle(), reducer)
                .replay(1)
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: BaseIntent): BrowseAction {
        return when (intent) {
            is BrowseIntent.LoadBufferoosIntent -> BrowseAction.LoadBufferoos
            is BrowseIntent.RefreshBufferoosIntent -> BrowseAction.LoadBufferoos
            is BrowseIntent.InitialIntent -> BrowseAction.LoadBufferoos
            else -> throw UnsupportedOperationException(
                    "Oops, that looks like an unknown intent: " + intent)
        }
    }

}