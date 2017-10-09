package dsm.firealarm;

import com.google.firebase.iid.FirebaseInstanceId;

import com.androidquery.AQuery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    Retrofit mRetrofit;
    ApiService2 mApiService2;

    private AQuery aQuery;
    private EditText inputName, inputCode, inputPlace;
    private Button btnCancel, btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Log.d("xxx", FirebaseInstanceId.getInstance().getToken());

        inputName = (EditText) findViewById(R.id.inputName);
        inputCode = (EditText) findViewById(R.id.inputCode);
        inputPlace = (EditText) findViewById(R.id.inputPlace);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String code= inputCode.getText().toString();
                String place= inputPlace.getText().toString();
                String token= FirebaseInstanceId.getInstance().getToken();

                mRetrofit = new Retrofit.Builder().baseUrl(ApiService2.API_URL).build();
                mApiService2 = mRetrofit.create(ApiService2.class);

                Call<Void> call = mApiService2.signup(name, code, place, token);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d(this.getClass().getName(), "로그인 입니다");
                        int ccode = response.code();
                        Log.d("statusCodeㅅㅅㅅㅅㅅㅅ", Integer.toString(ccode));
                        if (response.code() == 201) {
                            finish();
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivityForResult(intent, 1000);
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "로그인 성공", 3000).show();
                        } else {
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "잘못된 정보입니다.", 3000).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버 오류가 발생하였습니다.", Toast.LENGTH_LONG);
                    }
                });
            }
            });




//        inputName = (EditText) findViewById(R.id.inputName);
//        inputCode = (EditText) findViewById(R.id.inputCode);
//        inputPlace = (EditText) findViewById(R.id.inputPlace);
//        btnCancel = (Button) findViewById(R.id.btnCancel);
//        btnOk = (Button) findViewById(R.id.btnOk);
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplication(), SplashActivity.class);
//                startActivityForResult(intent, 1000);
//            }
//        });
//
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                aQuery = new AQuery(getApplicationContext());
//
//                String name = inputName.getText().toString();
//                String code = inputCode.getText().toString();
//                String place = inputPlace.getText().toString();
//                String token = "AIzaSyCtjT141zecUihrV1AZVFsBVdShw84iWz0";
//
//                if (!name.isEmpty() && !code.isEmpty()) {
//                    Map<String, String> params = new HashMap<>();
//
//                    params.put("name", name);
//                    params.put("code", code);
//                    params.put("place", place);
//                    params.put("token",token);
//                    Log.d("send data", params.toString());
//
//                    aQuery.ajax("http://http://13.125.19.201:3000/auth/signup", params, String.class, new AjaxCallback<String>() {
//                        @Override
//                        public void callback(String url, String response, AjaxStatus status) {
//                            int statusCode = status.getCode();
//                            Log.d("statusCode아ㅏㅏㅏㅏㅏㅏ", Integer.toString(statusCode));
//                            if (statusCode == 200) {
//                                finish();
//                                Intent intent = new Intent(getApplication(), MainActivity.class);
//                                startActivityForResult(intent, 1000);
//                                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "로그인 성공", 3000).show();
//                            } else {
//                                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "잘못된 정보입니다.", 3000).show();
//                            }
//                        }
//                    });
//                } else {
//                    if (name.length() == 0)
//                        Toast.makeText(SignUpActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
//                    else if (code.length() == 0)
//                        Toast.makeText(SignUpActivity.this, "기기 번호를 입력하세요!", Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(SignUpActivity.this, "설치 위치를 입력하세요!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}