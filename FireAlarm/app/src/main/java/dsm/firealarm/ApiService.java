package dsm.firealarm;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 10107PSH on 2017-09-17.
 */

public interface ApiService {

    public static final String API_URL = "http://13.125.19.201:3000/auth/";

    @FormUrlEncoded
    @POST("signin")
    Call<Void> signin(@Field("token") String token);



}
