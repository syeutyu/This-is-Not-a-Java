package dsm.firealarm;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 10107PSH on 2017-09-26.
 */

public interface ApiService2 {
    public static final String API_URL = "https://daejava.herokuapp.com/";

    @FormUrlEncoded
    @POST("signup")
    Call<Void> signup (@Field("name") String name, @Field("code") String code, @Field("place") String place, @Field("token") String token);

}

