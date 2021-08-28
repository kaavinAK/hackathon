package com.example.four;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIcaller {
@GET("weather")
Call<location> getlocation(@Query("lat") String lat,@Query("lon") String lon,@Query("appid") String appid);

}
