package dsm.firealarm;

import com.google.firebase.iid.FirebaseInstanceId;

import com.androidquery.AQuery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {
    Retrofit mretrofit;
    ApiService mApiService;
    private AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Log.d(this.getClass().getName(), "SplashActivity income!");
//
//        aQuery = new AQuery(getApplicationContext());
//
//        String token = "AIzaSyCtjT141zecUihrV1AZVFsBVdShw84iWz0";
//
//        Map<String, String> params = new HashMap<>();
//
//        params.put("token", token);
//        Log.d("send data야ㅑㅑㅑㅑㅑ", params.toString());
//
//        aQuery.ajax("http://13.124.35.162:3000", params, String.class, new AjaxCallback<String>() {
//           @Override
//            public void callback(String url, String response, AjaxStatus status) {
//               Log.d(this.getClass().getName(), "실행ㅅㅅㅅㅅㅅ");
//               int statusCode = status.getCode();
//               Log.d("statusCodeㅅㅅㅅㅅㅅㅅ", Integer.toString(statusCode));
//               if(statusCode == 400) {
//                   Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
//               } else if(statusCode==200) {
//                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//               } else {
//                   SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(),"잘못된 코드입니다.",3000).show();
//               }
//           }
//        });


        Handler handler = new Handler();
        Log.d(this.getClass().getName(), "되라되라되라");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mretrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
                mApiService = mretrofit.create(ApiService.class);
                Log.d(this.getClass().getName(), "되라되라되라");
                Call<Void> call = mApiService.signin(FirebaseInstanceId.getInstance().getToken());
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, retrofit2.Response response) {
                        Log.d(this.getClass().getName(), "실행ㅅㅅㅅㅅㅅ");
                        int ccode = response.code();
                        Log.d("statusCodeㅅㅅㅅㅅㅅㅅ", Integer.toString(ccode));
                        if (response.code() == 400) {
                            /**회원이 아닐 경우*/
                            Log.d(this.getClass().getName(),"회원이 아닐 경우");
                            Intent intent = new Intent(getApplication(), LogInActivity.class);
                            Log.d(this.getClass().getName(),"자ㅏㅏㅏㅏㅏ");
                            startActivity(intent);
                        } else if (response.code() == 200) {
                            /**회원일 경우, 자동로그인*/
                            /**쿠키 처리*/
                            int ccodee = response.code();
                            Log.d("메인화면 코드다", Integer.toString(ccode));
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버 오류가 발생하였습니다.", Toast.LENGTH_LONG);
                    }
                });


            }
        }, 3000);


    }
}