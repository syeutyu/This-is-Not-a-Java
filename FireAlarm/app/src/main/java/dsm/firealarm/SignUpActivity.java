package dsm.firealarm;

import com.google.firebase.iid.FirebaseInstanceId;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    Retrofit mretrofit;
    ApiService mApiService;

    private EditText inputName, inputCode, inputPlace;
    private Button btnCancel, btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Log.i("token-----", FirebaseInstanceId.getInstance().getToken());

        inputName = (EditText) findViewById(R.id.inputName);
        inputCode = (EditText) findViewById(R.id.inputCode);
        inputPlace = (EditText) findViewById(R.id.inputPlace);
        // inputPhoneNum = (TextView) findViewById(R.id.inputPhoneNum);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String code = inputCode.getText().toString();
                String place = inputPlace.getText().toString();
                String token = FirebaseInstanceId.getInstance().getToken();

                final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String tel = telephonyManager.getLine1Number();
                if (tel.startsWith("+82")) {
                    tel = tel.replace("+82", "0");
                }

/*                if(code != null) {
                    Intent intent1 = new Intent(getApplicationContext(), TestActivity.class);
                    intent1.putExtra("code",code);
                    startActivity(intent1);
                } else {
                    Log.d(this.getClass().getName(), "잘못된 코드 전달");
                }*/

                mretrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
                mApiService = mretrofit.create(ApiService.class);

                Call<Void> call = mApiService.signup(name, code, place, token, tel);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d(this.getClass().getName(), "로그인 입니다.");
                        int statusCode = response.code();
                        Log.i("statusCode-----", Integer.toString(statusCode));
                        if (response.code() == 201) {
                            finish();
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "회원가입 성공", 3000).show();
                            startActivityForResult(intent, 1000);
                        } else {
                            // TODO : response.code() == 403 서버에서 회원가입 에러 발생 시
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "잘못된 정보입니다.", 3000).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "서버 오류가 발생하였습니다.", 3000).show();
                    }
                });
            }
        });
    }
}