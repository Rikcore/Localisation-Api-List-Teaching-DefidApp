package com.waxym.defibapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {

    @GET("/api/records/1.0/search/?dataset=defibrillateurs&rows=100 ")
    Call<ResultModel> getResult();
}
