package mx.com.cdcs.yoconstruyo.data.service;

import java.util.List;

import io.reactivex.Single;
import mx.com.cdcs.yoconstruyo.model.LoginResponse;
import mx.com.cdcs.yoconstruyo.model.Module;
import mx.com.cdcs.yoconstruyo.model.SignUpResponse;
import mx.com.cdcs.yoconstruyo.model.Submodule;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YoConstruyoService {

    @GET("modules")
    Single<List<Module>> getModules(@Query("token") String token);

    @GET("submodules/{moduleId}")
    Single<List<Submodule>> getSubmodules(@Path("moduleId") String moduleId,
                                          @Query("token") String token);

    @FormUrlEncoded
    @POST("register")
    Single<SignUpResponse> signUp(@Field("name") String name,
                                  @Field("last_name") String lastName,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("password_confirmation") String passwordConfirmation,
                                  @Field("gender") String gender,
                                  @Field("dob") String dob,
                                  @Field("country") String country,
                                  @Field("state") String state,
                                  @Field("city") String city,
                                  @Field("education") String education);

    @FormUrlEncoded
    @POST("authenticate")
    Single<LoginResponse> login(@Field("email") String email,
                                @Field("password") String password);


}
