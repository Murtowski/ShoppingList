package murt.shoppinglistapp.injection

import dagger.Module
import dagger.Provides
import murt.data.repository.RemoteService
import murt.remote.NetworkService
import murt.remote.NetworkServiceFactory
import murt.remote.RemoteServiceImpl
import murt.shoppinglistapp.ui.utils.SystemTools
import javax.inject.Singleton

/**
 * Piotr Murtowski on 25.02.2018.
 */
@Module
open class RemoteModule {

    @Singleton
    @Provides
    fun provideNetworkService(): NetworkService {
        val isDebug = SystemTools.isDebugMode
        return NetworkServiceFactory().createNetworkService(isDebug)
    }

    @Singleton
    @Provides
    fun provideRemoteService(networkService: NetworkService): RemoteService {
        return RemoteServiceImpl(networkService)
    }
}