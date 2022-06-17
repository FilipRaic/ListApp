package hr.tvz.android.listapp.backendData

import hr.tvz.android.listapp.model.ItemModel
import retrofit2.Call
import retrofit2.http.GET

interface ServiceInterface {
    @GET("items")
    fun fetchModel(): Call<MutableList<ItemModel>>
}