package org.buffer.android.boilerplate.data

import io.reactivex.Completable
import io.reactivex.Flowable
import org.buffer.android.boilerplate.data.mapper.ArticleMapper
import org.buffer.android.boilerplate.data.model.ArticleEntity
import org.buffer.android.boilerplate.data.source.ArticleDataStoreFactory
import org.buffer.android.boilerplate.domain.model.Article
import org.buffer.android.boilerplate.domain.repository.ArticleRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [ArticleRepository] interface for communicating to and from
 * data sources
 */
class ArticleDataRepository @Inject constructor(private val factory: ArticleDataStoreFactory,
                                                private val articleMapper: ArticleMapper):
        ArticleRepository {

    override fun clearArticle(): Completable {
        return factory.retrieveCacheDataStore().clearArticles()
    }

    override fun saveArticles(articles: List<Article>): Completable {
        val articlesEntities = mutableListOf<ArticleEntity>()
        articles.map { articlesEntities.add(articleMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().saveArticles(articlesEntities)
    }

    override fun getArticles(): Flowable<List<Article>> {
        return factory.retrieveCacheDataStore().isCached()
                .flatMapPublisher {
                    factory.retrieveDataStore(it).getArticles()
                }
                .flatMap {
                    Flowable.just(it.map { articleMapper.mapFromEntity(it) })
                }
                .flatMap {
                    saveArticles(it).toSingle { it }.toFlowable()
                }
    }

}