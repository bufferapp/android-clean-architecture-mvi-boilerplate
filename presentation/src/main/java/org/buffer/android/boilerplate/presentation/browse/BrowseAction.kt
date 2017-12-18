package org.buffer.android.boilerplate.presentation.browse

import org.buffer.android.boilerplate.presentation.base.BaseAction

sealed class BrowseAction : BaseAction {

    object LoadBufferoos : BrowseAction()

}