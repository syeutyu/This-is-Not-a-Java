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
    // Call<JsonObject> search (@Field("token")String token);
    Call<JsonObject> search (@Field("token")String token, @Field("category")String category,@Field("place")String place);

    @FormUrlEncoded
    @POST("/auth/test")
    Call<Void> test (@Field("bool") Boolean bool, @Field("token") String key);

    @FormUrlEncoded
    @POST("mypage/init")
    Call<JsonObject> settings (@Field("token")String token);

    @FormUrlEncoded
    @POST("mypage/add")
    Call<Void> add (@Field("code") String code, @Field("place") String place, @Field("token") String token);

    @FormUrlEncoded
    @POST("/mypage/delete")
    Call<Void> delete (@Field("token")String token, @Field("code")String code);

    @FormUrlEncoded
    @POST("/mypage/modify")
    Call<Void> modify (@Field("token")String token, @Field("place")String place,@Field("code")String code);
}
