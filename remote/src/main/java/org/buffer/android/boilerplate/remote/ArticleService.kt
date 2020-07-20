package org.buffer.android.boilerplate.remote

import io.reactivex.Flowable
import org.buffer.android.boilerplate.remote.model.ArticleModel
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the Article API
 */
interface ArticleService {

    @GET("top-headlines?country=id")
    fun getArticles(): Flowable<ArticleResponse>

    data class ArticleResponse(
            val status: String? = null,
            val totalResults: Int? = null,
            val articles: List<ArticleModel> = listOf()
    )
}