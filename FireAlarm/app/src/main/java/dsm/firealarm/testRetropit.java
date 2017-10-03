package dsm.firealarm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dsm2016 on 2017-10-02.
 */

public interface testRetropit {

    @FormUrlEncoded
    @POST("/auth/test")
    Call<Void> test (@Field("bool") Boolean bool, @Field("token") String key);

}
