package org.buffer.android.boilerplate.data.source

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.buffer.android.boilerplate.data.model.ArticleEntity
import org.buffer.android.boilerplate.data.model.BufferooEntity
import org.buffer.android.boilerplate.data.repository.ArticleDataStore
import org.buffer.android.boilerplate.data.repository.ArticleRemote
import org.buffer.android.boilerplate.data.repository.BufferooDataStore
import org.buffer.android.boilerplate.data.repository.BufferooRemote
import javax.inject.Inject

/**
 * Implementation of the [ArticleDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class ArticleRemoteDataStore @Inject constructor(private val articleRemote: ArticleRemote) :
        ArticleDataStore {

    override fun clearArticles(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveArticles(articles: List<ArticleEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getArticles(): Flowable<List<ArticleEntity>> {
        return articleRemote.getArticles()
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

}