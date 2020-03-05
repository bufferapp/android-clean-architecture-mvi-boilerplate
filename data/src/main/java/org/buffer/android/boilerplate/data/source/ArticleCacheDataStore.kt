package org.buffer.android.boilerplate.data.source

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.buffer.android.boilerplate.data.model.ArticleEntity
import org.buffer.android.boilerplate.data.model.BufferooEntity
import org.buffer.android.boilerplate.data.repository.ArticleCache
import org.buffer.android.boilerplate.data.repository.ArticleDataStore
import org.buffer.android.boilerplate.data.repository.BufferooCache
import org.buffer.android.boilerplate.data.repository.BufferooDataStore
import javax.inject.Inject

/**
 * Implementation of the [ArticleDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class ArticleCacheDataStore @Inject constructor(private val articleCache: ArticleCache) :
        ArticleDataStore {

    override fun clearArticles(): Completable {
        return articleCache.clearArticles()
    }

    override fun saveArticles(articles: List<ArticleEntity>): Completable {
        return articleCache.saveArticles(articles)
                .doOnComplete {
                    articleCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    override fun getArticles(): Flowable<List<ArticleEntity>> {
        return articleCache.getArticles()
    }

    /**
     * Retrieve a list of [ArticleEntity] instance from the cache
     */
    override fun isCached(): Single<Boolean> {
        return articleCache.isCached()
    }

}