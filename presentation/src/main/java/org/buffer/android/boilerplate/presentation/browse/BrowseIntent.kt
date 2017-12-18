package org.buffer.android.boilerplate.presentation.browse

import org.buffer.android.boilerplate.presentation.base.BaseIntent

sealed class BrowseIntent : BaseIntent {

    object InitialIntent : BrowseIntent()

    object LoadBufferoosIntent : BrowseIntent()

    object RefreshBufferoosIntent : BrowseIntent()

}