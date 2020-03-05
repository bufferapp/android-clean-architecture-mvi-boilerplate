package org.buffer.android.boilerplate.presentation.browse.mapper

import org.buffer.android.boilerplate.domain.model.Article
import org.buffer.android.boilerplate.domain.model.Bufferoo
import org.buffer.android.boilerplate.presentation.browse.model.ArticleView
import org.buffer.android.boilerplate.presentation.browse.model.BufferooView
import org.buffer.android.boilerplate.presentation.mapper.Mapper
import javax.inject.Inject

/**
 * Map a [BufferooView] to and from a [Bufferoo] instance when data is moving between
 * this layer and the Domain layer
 */
open class ArticleMapper @Inject constructor() : Mapper<ArticleView, Article> {

    override fun mapToView(article: Article): ArticleView {
        return ArticleView(article.author, article.title, article.description, article.url, article.urlToImage, article.publishedAt, article.content)
    }
}