package com.compubase.sportive.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> UserRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("type") String type,
            @Field("lang") Double lang,
            @Field("lat") Object lat,
            @Field("images") String images,
            @Field("famous") String famous,
            @Field("des") String des
    );

    @FormUrlEncoded
    @POST("login_user")
    Call<ResponseBody>UserLogin(
            @Field("email") String email,
            @Field("pass") String pass
    );


    @GET("select_all_center")
    Call<ResponseBody>ListOfCenters();

    @FormUrlEncoded
    @POST("select_all_active")
    Call<ResponseBody>ListOfActivities(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("update_user_or_center")
    Call<ResponseBody> UpdateData(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("lang") Double lang,
            @Field("lat") Object lat,
            @Field("images") String images,
            @Field("famous") String famous,
            @Field("des") String des,
            @Field("id") String id
    );
}
