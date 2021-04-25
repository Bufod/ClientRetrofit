package com.example.samsa;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @GET("/{api}")
    Call<User> getUserById(@Path("api") String api,
                           @Query("id") Integer id);
}
