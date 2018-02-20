package murt.remote

import io.reactivex.Single
import murt.data.model.ShoppingItem
import murt.data.repository.RemoteService
import murt.data.responseModel.RemoteError
import retrofit2.Response
import javax.inject.Inject

/**
 * Piotr Murtowski on 20.02.2018.
 */
class RemoteServiceImpl @Inject constructor(private val networkService: NetworkService):
    RemoteService {

    override fun getShoppingList(): Single<List<ShoppingItem>> {
        return networkService.getShoppingList()
            .mapResponseToBody()
    }


}

fun <T> Single<Response<T>>.mapResponseToBody(): Single<T>{
    return this
        .flatMap {
            if(it.isSuccessful && it.body() != null)
                Single.just(it.body()!!)
            else
                Single.error(RemoteError(it.code()))
        }
}