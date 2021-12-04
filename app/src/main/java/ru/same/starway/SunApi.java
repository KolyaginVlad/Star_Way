package ru.same.starway;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.same.starway.api.SunData;

public interface SunApi {
    @GET("json")
    Call<SunData>getData(@Query("lat")String lat,@Query("lng")String lng);
}
