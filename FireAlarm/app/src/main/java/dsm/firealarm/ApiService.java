package dsm.firealarm;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 10107PSH on 2017-09-17.
 */

public interface ApiService {
    public static final String API_URL = "http://10.156.145.113:3000/auth/";

    @FormUrlEncoded
    @POST("signin")
    Call<ResponseBody>postComment(@Field("token") String token);

}
