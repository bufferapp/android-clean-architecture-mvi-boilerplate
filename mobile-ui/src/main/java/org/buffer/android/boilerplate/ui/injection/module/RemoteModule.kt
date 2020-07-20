package org.buffer.android.boilerplate.ui.injection.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import org.buffer.android.boilerplate.data.repository.ArticleRemote
import org.buffer.android.boilerplate.data.repository.BufferooRemote
import org.buffer.android.boilerplate.remote.*
import org.buffer.android.boilerplate.ui.BuildConfig

/**
 * Module that provides all dependencies from the repository package/layer.
 */
@Module
abstract class RemoteModule {

    /**
     * This companion object annotated as a module is necessary in order to provide dependencies
     * statically in case the wrapping module is an abstract class (to use binding)
     */
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideBufferooService(): BufferooService {
            return BufferooServiceFactory.makeBuffeoorService(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun provideArticleService(): ArticleService {
            return ArticleServiceFactory.makeArticleService(
                BuildConfig.DEBUG,
                BuildConfig.API_KEY,
                BuildConfig.BASE_URL
            )
        }
    }

    @Binds
    abstract fun bindBufferooRemote(bufferooRemoteImpl: BufferooRemoteImpl): BufferooRemote

    @Binds
    abstract fun bindArticleRemote(articleRemoteImpl: ArticleRemoteImpl): ArticleRemote
}