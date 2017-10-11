package dsm.firealarm;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 10107PSH on 2017-09-26.
 */

public interface ApiService2 {
    public static final String API_URL = "https://daejava.herokuapp.com/";

    @FormUrlEncoded
    @POST("auth/signup")
    Call<Void> signup (@Field("name") String name, @Field("code") String code, @Field("place") String place, @Field("token") String token, @Field("tel")String tel);

    @FormUrlEncoded
    @POST("auth/search")
    Call<Void> search (@Field("token")String token);

    @GET("auth/search")
    Call<JsonObject> getSearch();
}

