package murt.shoppinglistapp

import android.app.Activity
import android.content.Context
import android.support.multidex.MultiDex
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import murt.shoppinglistapp.injection.DaggerAppComponent
import murt.shoppinglistapp.ui.utils.SystemTools
import timber.log.Timber

/**
 * Piotr Murtowski on 20.02.2018.
 */
class AndroidApplication: DaggerApplication(){

    override fun attachBaseContext(base: Context?) {
        super<DaggerApplication>.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
//        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()


        AndroidThreeTen.init(this)

        if (SystemTools.isDebugMode) {
            Timber.plant(Timber.DebugTree())
        }
    }
}