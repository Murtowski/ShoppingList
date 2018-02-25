package murt.shoppinglistapp

import android.support.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import murt.shoppinglistapp.injection.CacheModule
import murt.shoppinglistapp.injection.ContextModule
import murt.shoppinglistapp.injection.DaggerAppComponent
import murt.shoppinglistapp.ui.utils.SystemTools
import timber.log.Timber

/**
 * Piotr Murtowski on 20.02.2018.
 */
class AndroidApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .application(this)
//            .contextModule(ContextModule(this))
//            .cacheModule(CacheModule(this))
            .build()
            .inject(this)

//        val component: AppComponent by lazy {
//            DaggerAppComponent
//                .builder()
//                .appModule(AppModule(this))
//                .build()
//        }

        AndroidThreeTen.init(this)

        if (SystemTools.isDebugMode) {
            Timber.plant(Timber.DebugTree())
        }
    }
}