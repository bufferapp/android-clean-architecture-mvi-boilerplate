package org.buffer.android.boilerplate.data.mapper

import org.buffer.android.boilerplate.data.model.ArticleEntity
import org.buffer.android.boilerplate.data.model.BufferooEntity
import org.buffer.android.boilerplate.domain.model.Article
import org.buffer.android.boilerplate.domain.model.Bufferoo
import javax.inject.Inject

open class ArticleMapper @Inject constructor() : Mapper<ArticleEntity, Article> {

    /**
     * Map a [BufferooEntity] instance to a [Bufferoo] instance
     */
    override fun mapFromEntity(article: ArticleEntity): Article {
        return Article(Article.Source(article.source.id, article.source.name), article.author, article.title, article.description, article.url, article.urlToImage, article.publishedAt, article.content)
    }

    /**
     * Map a [Bufferoo] instance to a [BufferooEntity] instance
     */
    override fun mapToEntity(article: Article): ArticleEntity {
        return ArticleEntity(ArticleEntity.Source(article.source.id, article.source.name), article.author, article.title, article.description, article.url, article.urlToImage, article.publishedAt, article.content)
    }


}