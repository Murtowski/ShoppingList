package murt.remote

import io.reactivex.Single
import murt.data.model.ShoppingItem
import retrofit2.Response
import retrofit2.http.GET

/**
 * Piotr Murtowski on 20.02.2018.
 */
interface NetworkService {

    @GET("exampleGET")
    fun getShoppingList(): Single<Response<List<ShoppingItem>>>
}