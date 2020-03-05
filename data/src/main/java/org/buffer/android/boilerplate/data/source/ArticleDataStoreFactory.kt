package org.buffer.android.boilerplate.data.source

import org.buffer.android.boilerplate.data.repository.ArticleCache
import org.buffer.android.boilerplate.data.repository.ArticleDataStore
import javax.inject.Inject

/**
 * Create an instance of a ArticleDataStore
 */
open class ArticleDataStoreFactory @Inject constructor(
        private val articleCache: ArticleCache,
        private val articleCacheDataStore: ArticleCacheDataStore,
        private val articleRemoteDataStore: ArticleRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): ArticleDataStore {
        if (isCached && !articleCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): ArticleDataStore {
        return articleCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): ArticleDataStore {
        return articleRemoteDataStore
    }

}