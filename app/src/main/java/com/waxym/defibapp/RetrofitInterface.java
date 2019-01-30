package com.waxym.defibapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("/api/records/1.0/search")
    Call<ResultModel> getResult(@Query("dataset") String dataset, @Query("rows") int rows);
}
