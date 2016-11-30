package com.bixspace.ciclodevida.data.remote.request;

import com.bixspace.ciclodevida.data.AccesToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by junior on 30/11/16.
 */

public interface LoginRequest {


    @FormUrlEncoded
    @POST("users/login")
    Call<AccesToken>  loginUser(@Field("email") String email,
                                @Field("password") String password);

}
