package dsm.firealarm;

import com.google.firebase.iid.FirebaseInstanceId;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {
    private Retrofit mretrofit;
    private ApiService mApiService;

    public static boolean DEBUG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.DEBUG = isDebuggable(this);

        if (FirebaseInstanceId.getInstance().getToken() != null) {
            Dlog.d("token-----" + FirebaseInstanceId.getInstance().getToken());
        } else
            Dlog.d("token data is null. try again.");

        Handler handler = new Handler();
        Dlog.d("before handler");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mretrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
                mApiService = mretrofit.create(ApiService.class);

                if (FirebaseInstanceId.getInstance().getToken() != null) {
                    Dlog.d("token-----" + FirebaseInstanceId.getInstance().getToken());
                } else
                    Dlog.d("token data is null. try again.");

                Dlog.d("before send token data");
                Call<Void> call = mApiService.signin(FirebaseInstanceId.getInstance().getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Dlog.d("");

                        int statusCode = response.code();
                        Dlog.d("statusCode-----" + String.valueOf(statusCode));

                        if (response.code() == 400) {
                            /** 비회원 */
                            Dlog.d("not member");
                            Intent intent = new Intent(getApplication(), SignUpActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (response.code() == 200) {
                            /** 회원 */
                            Dlog.d("member");
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "일시적으로 서버 오류가 발생하였습니다.", Toast.LENGTH_SHORT);
                    }
                });
            }
        }, 1000);
    }

    /**
     * 현재 디버그모드여부를 리턴
     *
     * @param context
     * @return
     */
    private boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
        /* debuggable variable will remain false */
        }

        return debuggable;
    }

}