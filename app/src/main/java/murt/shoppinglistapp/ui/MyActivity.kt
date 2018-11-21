package murt.shoppinglistapp.ui

import dagger.android.support.DaggerAppCompatActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein


/**
 * Piotr Murtowski on 20.02.2018.
 */
abstract class MyActivity: DaggerAppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

}