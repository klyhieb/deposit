package kh.com.acleda.deposits.core.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface APIInterface {
    @GET("group/{id}/users")
    fun groupList(@Path("id") groupId: Int): Call<List<String?>?>?
}