package org.buffer.android.boilerplate.presentation.browse

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.buffer.android.boilerplate.domain.interactor.browse.GetArticles
import javax.inject.Inject

class BrowseProcessor @Inject constructor(private val getArticles: GetArticles) {

    private val conversationsProcessor: ObservableTransformer<
            BrowseAction.LoadBufferoos, BrowseResult> = ObservableTransformer {
        it.switchMap {
            getArticles.execute()
                    .map {
                        BrowseResult.LoadArticleTask.success(it)
                    }
                    .onErrorReturn {
                        BrowseResult.LoadArticleTask.failure()
                    }
                    .toObservable()
                    .startWith(BrowseResult.LoadArticleTask.inFlight())
        }
    }

    var actionProcessor: ObservableTransformer<BrowseAction, BrowseResult>

    init {
        this.actionProcessor = ObservableTransformer {
            it.publish {
                it.ofType(BrowseAction.LoadBufferoos::class.java)
                        .compose(conversationsProcessor)
                        .mergeWith(it.filter({ it !is BrowseAction.LoadBufferoos })
                                .flatMap {
                                    Observable.error<BrowseResult>(
                                            IllegalArgumentException("Unknown Action type"))
                                })
            }
        }
    }

}