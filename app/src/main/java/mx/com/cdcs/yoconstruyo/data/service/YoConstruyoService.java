package mx.com.cdcs.yoconstruyo.data.service;

import java.util.List;

import io.reactivex.Single;
import mx.com.cdcs.yoconstruyo.model.Module;
import mx.com.cdcs.yoconstruyo.model.Submodule;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface YoConstruyoService {

    @GET("modules")
    Single<List<Module>> getModules();

    @GET("submodules/{id}/")
    Single<List<Submodule>> getSubmodules(@Path("id") String id);

    /*
    @FormUrlEncoded
    @POST("signup.json")
    Single<SignUpResponse> signUp(@Field("email") String email,
                                  @Field("password") String password);
    */

}
