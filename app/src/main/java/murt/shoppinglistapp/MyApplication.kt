package murt.shoppinglistapp

import android.support.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * Piotr Murtowski on 20.02.2018.
 */
class MyApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}