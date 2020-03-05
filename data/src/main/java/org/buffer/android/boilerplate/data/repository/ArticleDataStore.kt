package org.buffer.android.boilerplate.data.repository

import org.buffer.android.boilerplate.data.model.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to Articles.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface ArticleDataStore {

    fun clearArticles(): Completable

    fun saveArticles(articles: List<ArticleEntity>): Completable

    fun getArticles(): Flowable<List<ArticleEntity>>

    fun isCached(): Single<Boolean>

}