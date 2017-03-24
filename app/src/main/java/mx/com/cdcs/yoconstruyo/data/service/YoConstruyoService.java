package mx.com.cdcs.yoconstruyo.data.service;

import java.util.List;

import io.reactivex.Single;
import mx.com.cdcs.yoconstruyo.model.Module;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface YoConstruyoService {


    @GET("modules.json")
    Single<List<Module>> getModules();

    /*
    @FormUrlEncoded
    @POST("signup.json")
    Single<SignUpResponse> signUp(@Field("email") String email,
                                  @Field("password") String password);
    */

}
