package org.buffer.android.boilerplate.ui.mapper

import org.buffer.android.boilerplate.presentation.browse.model.ArticleView
import org.buffer.android.boilerplate.ui.model.ArticleIntent
import javax.inject.Inject

/**
 * Map a [ArticleView] to and from a [ArticleIntent] instance when data is moving between
 * this layer and the Domain layer
 */
open class ArticleMapper @Inject constructor() : Mapper<ArticleIntent, ArticleView> {

    override fun mapToViewModel(article: ArticleView): ArticleIntent {
        return ArticleIntent(
                article.author?:"",
                article.title?:"",
                article.description?:"",
                article.url?:"",
                article.urlToImage?:"",
                article.publishedAt?:"",
                article.content?:"")
    }
}