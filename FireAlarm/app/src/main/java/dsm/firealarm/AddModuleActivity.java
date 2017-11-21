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

public class AddModuleActivity extends AppCompatActivity {
    Retrofit mretrofit;
    ApiService mApiService;

    private EditText inputCode, inputPlace;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        inputCode = (EditText) findViewById(R.id.inputCode);
        inputPlace = (EditText) findViewById(R.id.inputPlace);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = inputCode.getText().toString();
                String place = inputPlace.getText().toString();
                String token = FirebaseInstanceId.getInstance().getToken();

                mretrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
                mApiService = mretrofit.create(ApiService.class);

                Call<Void> call = mApiService.add(code, place, token);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d(this.getClass().getName(), "모듈 추가입니다.");
                        int statusCode = response.code();
                        Log.i("statusCode-----", Integer.toString(statusCode));
                        if (response.code() == 200) {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "모듈추가 성공", 3000).show();
                            startActivityForResult(intent, 1000);
                        } else if(response.code()==400){
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "정확하게 입력해주세요.", 3000).show();
                        } else if(response.code()==403) {
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "이미 등록되어 있는 모듈입니다.", 3000).show();
                        } else if(response.code() == 404) {
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "라즈베리 등록 필요", 3000).show();
                        } else if(response.code()==500) {
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "서버 오류기 발생했습니다.", 3000).show();
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