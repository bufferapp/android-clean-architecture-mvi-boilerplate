package org.buffer.android.boilerplate.remote.mapper

import org.buffer.android.boilerplate.data.model.ArticleEntity
import org.buffer.android.boilerplate.data.model.BufferooEntity
import org.buffer.android.boilerplate.remote.model.ArticleModel
import org.buffer.android.boilerplate.remote.model.BufferooModel
import javax.inject.Inject

/**
 * Map a [ArticleModel] to and from a [ArticleEntity] instance when data is moving between
 * this later and the Data layer
 */
open class ArticleEntityMapper @Inject constructor() : EntityMapper<ArticleModel, ArticleEntity> {

    override fun mapFromRemote(article: ArticleModel): ArticleEntity {
        return ArticleEntity(
                ArticleEntity.Source(article.source?.id ?: "", article.source?.name?: ""),
                article.author?:"",
                article.title?:"",
                article.description?:"",
                article.url?:"",
                article.urlToImage?:"",
                article.publishedAt?:"",
                article.content?:"")
    }
}