/*
 * Copyright (c) 2020.
 * PT. Sampingan Mitra Indonesia
 */

package org.buffer.android.boilerplate.remote

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class RetrofitAuthenticator(private val token: String) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        return response.request.newBuilder()
            .header(HEADER_TOKEN_FIELD, "$TOKEN_PREFIX $token")
            .build()
    }
}

const val HEADER_TOKEN_FIELD = "Authorization"
const val TOKEN_PREFIX = "Bearer"