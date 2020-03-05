package org.buffer.android.boilerplate.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.buffer.android.boilerplate.data.model.ArticleEntity

interface ArticleCache {

    /**
     * Clear all Articles from the cache.
     */
    fun clearArticles(): Completable

    /**
     * Save a given list of Articles to the cache.
     */
    fun saveArticles(articles: List<ArticleEntity>): Completable

    /**
     * Retrieve a list of Articles, from the cache.
     */
    fun getArticles(): Flowable<List<ArticleEntity>>

    /**
     * Check whether there is a list of Articles stored in the cache.
     *
     * @return true if the list is cached, otherwise false
     */
    fun isCached(): Single<Boolean>

    /**
     * Set a point in time at when the cache was last updated.
     *
     * @param lastCache the point in time at when the cache was last updated
     */
    fun setLastCacheTime(lastCache: Long)

    /**
     * Check if the cache is expired.
     *
     * @return true if the cache is expired, otherwise false
     */
    fun isExpired(): Boolean

}