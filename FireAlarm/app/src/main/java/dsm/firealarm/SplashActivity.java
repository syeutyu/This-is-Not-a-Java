package dsm.firealarm;

import com.google.firebase.iid.FirebaseInstanceId;

import android.content.Intent;
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
    Retrofit mretrofit;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(FirebaseInstanceId.getInstance().getToken() != null) {
            Log.i("token-----", FirebaseInstanceId.getInstance().getToken());
        } else
            Log.i("SplashActivity", "null값, 재실행하세요");

        Handler handler = new Handler();
        Log.d(this.getClass().getName(), "핸들러 실행 전");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mretrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
                mApiService = mretrofit.create(ApiService.class);

                if(FirebaseInstanceId.getInstance().getToken() != null) {
                    Log.i("token-----", FirebaseInstanceId.getInstance().getToken());
                } else
                    Log.i("SplashActivity", "null값, 재실행하세요");

                Log.d(this.getClass().getName(), "토큰 보내기 전");
                Call<Void> call = mApiService.signin(FirebaseInstanceId.getInstance().getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d(this.getClass().getName(), "응답 실행");
                        int statusCode = response.code();
                        Log.i("statusCode-----", String.valueOf(statusCode));

                        if (response.code() == 400) {
                            /** 비회원 */
                            Log.i(this.getClass().getName(), "비회원");
                            Intent intent = new Intent(getApplication(), SignUpActivity.class);
                            startActivity(intent);
                        } else if (response.code() == 200) {
                            /** 회원 */
                            Log.i(this.getClass().getName(), "회원");
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
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
}