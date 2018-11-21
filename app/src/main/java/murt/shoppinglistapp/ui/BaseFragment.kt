package murt.shoppinglistapp.ui

import dagger.android.support.DaggerFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

/**
 * Piotr Murtowski on 21.02.2018.
 */
abstract class BaseFragment: DaggerFragment(), KodeinAware {

    override val kodein by closestKodein()

}