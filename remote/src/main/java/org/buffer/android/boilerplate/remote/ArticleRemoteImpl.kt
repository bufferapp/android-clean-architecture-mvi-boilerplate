package org.buffer.android.boilerplate.remote

import io.reactivex.Flowable
import org.buffer.android.boilerplate.data.model.ArticleEntity
import org.buffer.android.boilerplate.data.repository.ArticleRemote
import org.buffer.android.boilerplate.remote.mapper.ArticleEntityMapper
import java.lang.reflect.Field
import javax.inject.Inject

/**
 * Remote implementation for retrieving Article instances. This class implements the
 * [ArticleRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class ArticleRemoteImpl @Inject constructor(private val articleService: ArticleService,
                                            private val entityMapper: ArticleEntityMapper) :
        ArticleRemote {
    override fun getArticles(): Flowable<List<ArticleEntity>> {
        return articleService.getArticles()
                .map {
                    it.articles
                }
                .map {
                    val entities = mutableListOf<ArticleEntity>()
                    it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                    entities
                }
    }

}