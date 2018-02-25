package murt.shoppinglistapp

import android.support.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import murt.shoppinglistapp.injection.AppComponent
import murt.shoppinglistapp.injection.DaggerAppComponent

/**
 * Piotr Murtowski on 20.02.2018.
 */
class MyApplication: MultiDexApplication() {

//    val appComponent: AppComponent by lazy {
//        DaggerAppComponent.builder()
//    }

    override fun onCreate() {
        super.onCreate()


//        val component: AppComponent by lazy {
//            DaggerAppComponent
//                .builder()
//                .appModule(AppModule(this))
//                .build()
//        }

        AndroidThreeTen.init(this)
    }
}