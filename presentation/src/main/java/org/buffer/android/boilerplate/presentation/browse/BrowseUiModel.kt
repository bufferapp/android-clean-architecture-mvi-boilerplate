package org.buffer.android.boilerplate.presentation.browse

import org.buffer.android.boilerplate.presentation.base.BaseViewState
import org.buffer.android.boilerplate.presentation.browse.model.ArticleView
import org.buffer.android.boilerplate.presentation.browse.model.BufferooView

sealed class BrowseUiModel(val inProgress: Boolean = false,
                           val bufferoos: List<ArticleView>? = null)
    : BaseViewState {

    object InProgress : BrowseUiModel(true, null)

    object Failed : BrowseUiModel()

    data class Success(private val result: List<ArticleView>?) : BrowseUiModel(false, result)

    class Idle : BrowseUiModel(false, null)

}