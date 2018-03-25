package murt.shoppinglistapp.ui.shoppingListDetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import murt.data.model.ShoppingItem
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import murt.shoppinglistapp.ui.MyViewModel
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import javax.inject.Inject

/**
 * Piotr Murtowski on 21.02.2018.
 */
class ShoppingListDetailsViewModel @Inject constructor(
    private val cacheService: CacheService
): MyViewModel() {

    /**
     * Shopping List
     * */
    fun createNewShoppingList(){
        val emptyShoppingList = ShoppingList.empty()
        cacheService.createShoppingList(emptyShoppingList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                shoppingListLiveData.value = it
            }, onError = {
                Timber.e(it, "Cannot create Empty Shopping List")
            })

    }

    fun getShoppingList(id: Long){
        cacheService.getShoppingList(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                shoppingListLiveData.value = it
            }, onError = {
                Timber.e(it, "Cannot Get Shopping list for ID:$id")
            }).addTo(disposable)
    }

    fun updateShoppingListTitle(shoppingList: ShoppingList){
        shoppingList.updatedAt = LocalDateTime.now()
        cacheService.updateShoppingListAndItems(shoppingList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onComplete = {
                Timber.i("Shopping Title updated")
            }, onError = {
                Timber.e(it, "Error while updating Title")
            })
    }

    /**
     * Shopping Item
     * */

    fun createShoppingItem(shoppingItem: ShoppingItem, shoppingListId: Long){
        shoppingItem.updatedAt = LocalDateTime.now()
        cacheService.createShoppingItem(shoppingItem, shoppingListId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                shoppingItemLiveData.value = it
                Timber.i("New Shopping Item created")
            }, onError = {
                Timber.e(it, "Error while creating new Shopping Item")
            })
    }

    fun updateShoppingItem(shoppingItem: ShoppingItem, shoppingListId: Long){
        shoppingItem.updatedAt = LocalDateTime.now()
        cacheService.updateShoppingItem(shoppingItem, shoppingListId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onComplete = {
                Timber.i("Shopping item created")
            }, onError = {
                Timber.e(it, "Error while updating Shopping Item")
            })
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem, shoppingListId: Long){
        cacheService.deleteShoppingItem(shoppingItem, shoppingListId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onComplete = {
                Timber.i("Shopping Item deleted")
            }, onError = {
                Timber.e(it, "Error while deleting Shopping Item")
            })
    }

    val shoppingItemLiveData = MutableLiveData<ShoppingItem>()
    val shoppingListLiveData = MutableLiveData<ShoppingList>()

}