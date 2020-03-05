package org.buffer.android.boilerplate.domain.interactor.browse

import io.reactivex.Flowable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.buffer.android.boilerplate.domain.executor.ThreadExecutor
import org.buffer.android.boilerplate.domain.interactor.FlowableUseCase
import org.buffer.android.boilerplate.domain.model.Article
import org.buffer.android.boilerplate.domain.model.Bufferoo
import org.buffer.android.boilerplate.domain.repository.ArticleRepository
import org.buffer.android.boilerplate.domain.repository.BufferooRepository
import javax.inject.Inject

/**
 * Use case used for retreiving a [List] of [Article] instances from the [ArticleRepository]
 */
open class GetArticles @Inject constructor(val articleRepository: ArticleRepository,
                                           threadExecutor: ThreadExecutor,
                                           postExecutionThread: PostExecutionThread):
        FlowableUseCase<List<Article>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<Article>> {
        return articleRepository.getArticles()
    }

}