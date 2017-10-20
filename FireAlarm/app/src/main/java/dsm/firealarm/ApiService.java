package dsm.firealarm;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 10107PSH on 2017-09-17.
 */

public interface ApiService {
    public static final String API_URL = "https://daejava.herokuapp.com/";

    @FormUrlEncoded
    @POST("auth/signin")
    Call<Void> signin(@Field("token") String token);

    @FormUrlEncoded
    @POST("auth/signup")
    Call<Void> signup (@Field("name") String name, @Field("code") String code, @Field("place") String place, @Field("token") String token, @Field("tel")String tel);

    @FormUrlEncoded
    @POST("auth/search")
    Call<JsonObject> search (@Field("token")String token);

    @FormUrlEncoded
    @POST("/auth/test")
    Call<Void> test (@Field("bool") Boolean bool, @Field("token") String key);
}
