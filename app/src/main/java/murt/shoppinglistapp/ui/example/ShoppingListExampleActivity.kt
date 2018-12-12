package murt.shoppinglistapp.ui.example

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shopping_list_details.*
import murt.cache.CacheServiceFactory
import murt.cache.CacheServiceImpl
import murt.data.model.ShoppingItem
import murt.data.repository.CacheService
import murt.data.repository.RemoteService
import murt.remote.NetworkServiceFactory
import murt.remote.RemoteServiceImpl
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.MyActivity
import org.kodein.di.generic.instance
import timber.log.Timber

class ShoppingListExampleActivity: MyActivity() {

    lateinit var viewModelFactory: ShoppingListExampleViewModelFactory
//    // INJECTION
//    private val viewModelFactory: ShoppingListExampleViewModelFactory
//            by instance(ShoppingListExampleViewModelFactory::class.java.simpleName)

    lateinit var mViewModel: ShoppingListExampleViewModel

    val mAdapter by lazy { createAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_example)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // View
        val adapter = createAdapter()

        // Services
        val remote = createRemoteService()
        val cache = createCacheService()
        viewModelFactory = ShoppingListExampleViewModelFactory(cache, remote)

        getViewModel()
        listenLiveData()
    }

    private fun getViewModel(){
        mViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ShoppingListExampleViewModel::class.java)
    }

    private fun listenLiveData(){
        mViewModel.getLiveDataShoppingList().observe(this, Observer {
            mAdapter.updateList(it.items)
        })
    }



    private fun createAdapter(): ShoppingListExampleAdapter {
        return ShoppingListExampleAdapter(saveItem = this::onClick)
    }

    private fun onClick(item: ShoppingItem) {
        Toast.makeText(this, "${item.title} clicked!", Toast.LENGTH_SHORT).show()
    }

    private fun createRemoteService(): RemoteService {
        val networkService = NetworkServiceFactory().createNetworkService(isDebug = true)
        return RemoteServiceImpl(networkService)
    }

    private fun createCacheService(): CacheService {
        // context may be injected
        val database = CacheServiceFactory().createCacheService(this)
        return CacheServiceImpl(database)
    }
}
