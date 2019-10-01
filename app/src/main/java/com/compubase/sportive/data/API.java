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
            @Field("des") String des,
            @Field("history") String history,
            @Field("img_1") String img_1,
            @Field("img_2") String img_2,
            @Field("img_3") String img_3,
            @Field("img_4") String img_4
    );

    @FormUrlEncoded
    @POST("login_user")
    Call<ResponseBody>UserLogin(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("insert_join")
    Call<ResponseBody>Join(
            @Field("id_center") String idcenter,
            @Field("id_user") String iduser,
            @Field("game_name") String gamename
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

    @FormUrlEncoded
    @POST("insert_game")
    Call<ResponseBody>InsertGame(
            @Field("id_center") String id_center,
            @Field("name_game") String name_game,
            @Field("coach") String coach
    );

    @FormUrlEncoded
    @POST("select_game")
    Call<ResponseBody>ListOfGames(
            @Field("id_center") String id_center
    );

    @GET("select_all_center_famous")
    Call<ResponseBody>ListOfFamous();

    @FormUrlEncoded
    @POST("insert_activites")
    Call<ResponseBody>InsertActiv(
            @Field("id_send") String id_send,
            @Field("id_recived") Integer id_recived,
            @Field("message") String message,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("select_join_center")
    Call<ResponseBody>UsersJoin(
            @Field("id_center") String id_center
    );

    @FormUrlEncoded
    @POST("select_join_iduser")
    Call<ResponseBody>CentersJoin(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("update_center")
    Call<ResponseBody>UpdateProfile(
            @Field("des") String des,
            @Field("history") String history,
            @Field("img_1") String img_1,
            @Field("img_2") String img_2,
            @Field("img_3") String img_3,
            @Field("img_4") String img_4,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("sendmss")
    Call<ResponseBody>SendSMS(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("entercode")
    Call<ResponseBody>EnterCode(
            @Field("enter_code") String code
    );

    @GET("select_all_trainer")
    Call<ResponseBody>ListOfTrainers();

    @FormUrlEncoded
    @POST("insert_sportive_comment")
    Call<ResponseBody>insertComment(
            @Field("id_user") String id_user,
            @Field("id_center_or_teriner") String id_center,
            @Field("Comment") String comment,
            @Field("Rate") String rate
    );

    @FormUrlEncoded
    @POST("select_comment_by_id")
    Call<ResponseBody>ViewComment(
            @Field("id") String id
    );
}
