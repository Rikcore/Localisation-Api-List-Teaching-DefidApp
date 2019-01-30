package com.waxym.defibapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/api/records/1.0/search")
    fun getResult(@Query("dataset") dataset: String, @Query("rows") rows: Int): Call<ResultModel>
}
