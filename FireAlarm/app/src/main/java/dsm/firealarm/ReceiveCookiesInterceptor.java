package dsm.firealarm;

import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by 10107PSH on 2017-09-21.
 * Response로부터 쿠키정보를 가져와서 Preference에 저장하는 클래스.
 */

public class ReceiveCookiesInterceptor implements Interceptor {

    private SharedPreferenceBase mSharedPreferenceBase;

    public ReceiveCookiesInterceptor(Context context) {
        mSharedPreferenceBase = SharedPreferenceBase.getInstanceOf(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            mSharedPreferenceBase.putSharedPreference(SharedPreferenceBase.SHARED_PREFERENCE_NAME_COOKIE, cookies);
        }
        return originalResponse;
    }
}
