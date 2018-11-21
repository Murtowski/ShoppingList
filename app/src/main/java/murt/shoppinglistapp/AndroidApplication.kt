package murt.shoppinglistapp

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDex
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import murt.cache.CacheServiceFactory
import murt.cache.CacheServiceImpl
import murt.cache.RoomDatabaseCache
import murt.data.repository.CacheService
import murt.data.repository.RemoteService
import murt.remote.NetworkService
import murt.remote.NetworkServiceFactory
import murt.remote.RemoteServiceImpl
import murt.shoppinglistapp.injection.DaggerAppComponent
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsViewModelFactory
import murt.shoppinglistapp.ui.shoppingListsArchived.ShoppingListArchivedViewModelFactory
import murt.shoppinglistapp.ui.shoppingListsCurrent.ShoppingListCurrentViewModelFactory
import murt.shoppinglistapp.ui.shoppingListsCurrent.ShoppingListCurrentViewModelFactory_Factory
import murt.shoppinglistapp.ui.utils.SystemTools
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import timber.log.Timber

/**
 * Piotr Murtowski on 20.02.2018.
 */
class AndroidApplication: DaggerApplication(), KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@AndroidApplication))
        import(remoteRepositoryModule)
        import(cacheRepositoryModule)
        import(viewModelModules)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
//        return DaggerAppComponent.builder().create(this)
    }

    override fun attachBaseContext(base: Context?) {
        super<DaggerApplication>.attachBaseContext(base)
        MultiDex.install(this)
    }



    override fun onCreate() {
        super.onCreate()

        Timber.i("$kodein")

        AndroidThreeTen.init(this)

        if (SystemTools.isDebugMode) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

/**
 * Remote Repository
 * */
val remoteRepositoryModule = Kodein.Module("remote"){
    bind <RemoteService>() with provider {
        RemoteServiceImpl(instance())
    }

    bind<NetworkService>() with singleton {
        NetworkServiceFactory().createNetworkService(isDebug = SystemTools.isDebugMode)
    }
}

/**
 * Cache Repository
 * */
val cacheRepositoryModule = Kodein.Module("cache"){
    bind<CacheService>() with provider {
        CacheServiceImpl(instance())
    }

    bind<RoomDatabaseCache>() with singleton {
        CacheServiceFactory().createCacheService(instance())
    }
}

/**
 * View Model
 * */
val viewModelModules = Kodein.Module("viewModel"){
    bind<ShoppingListArchivedViewModelFactory>(tag = ShoppingListArchivedViewModelFactory::class.java.simpleName) with provider {
        ShoppingListArchivedViewModelFactory(instance())
    }

    bind<ShoppingListCurrentViewModelFactory>(tag = ShoppingListCurrentViewModelFactory::class.java.simpleName) with provider {
        ShoppingListCurrentViewModelFactory(instance())
    }

    bind<ShoppingListDetailsViewModelFactory>(tag = ShoppingListDetailsViewModelFactory::class.java.simpleName) with provider {
        ShoppingListDetailsViewModelFactory(instance())
    }
}
