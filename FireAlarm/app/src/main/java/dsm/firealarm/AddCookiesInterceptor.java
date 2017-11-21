package dsm.firealarm;

import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 10107PSH on 2017-09-23.
 * Request마다 Preference에 저장되어있는 쿠키값을 함께 Header에 넣어주는 클래스.
 */

public class AddCookiesInterceptor implements Interceptor{

                SharedPreferenceBase mSharedPreferenceBase;

    public AddCookiesInterceptor (Context context) {
                    mSharedPreferenceBase = SharedPreferenceBase.getInstanceOf(context);
                }

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();

                    //Preference에서 cookies를 가져오는 작업을 수행
                    HashSet<String> cookies = (HashSet<String>) mSharedPreferenceBase.getSharedPreferences(
                            SharedPreferenceBase.SHARED_PREFERENCE_NAME_COOKIE,new HashSet<String>());

                    for(String cookie : cookies) {
                        builder.addHeader("Cookie", cookie);
        }

        //Web, Android, ios 구분을 위해 User-Agent세팅
        builder.removeHeader("User-Agent").addHeader("User-Agent","Android");

        return chain.proceed(builder.build());
    }

}
