package dsm.firealarm;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private AQuery aQuery;
    private EditText inputName, inputCode, inputPlace;
    private Button btnCancel, btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inputName = (EditText) findViewById(R.id.inputName);
        inputCode = (EditText) findViewById(R.id.inputCode);
        inputPlace = (EditText) findViewById(R.id.inputPlace);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SplashActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aQuery = new AQuery(getApplicationContext());

                String name = inputName.getText().toString();
                String code = inputCode.getText().toString();
                String place = inputPlace.getText().toString();

                if (!name.isEmpty() && !code.isEmpty()) {
                    Map<String, String> params = new HashMap<>();

                    params.put("name", name);
                    params.put("code", code);
                    params.put("place", place);
                    Log.d("send data", params.toString());

                    aQuery.ajax("http://10.156.145.113:3000/auth/signin", params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String response, AjaxStatus status) {
                            int statusCode = status.getCode();
                            Log.d("statusCode", Integer.toString(statusCode));
                            if (statusCode == 200) {
                                finish();
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivityForResult(intent, 1000);
                                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "로그인 성공", 3000).show();
                            } else {
                                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "잘못된 정보입니다.", 3000).show();
                            }
                        }
                    });
                } else {
                    if (name.length() == 0)
                        Toast.makeText(LogInActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
                    else if (code.length() == 0)
                        Toast.makeText(LogInActivity.this, "기기 번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(LogInActivity.this, "설치 위치를 입력하세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}