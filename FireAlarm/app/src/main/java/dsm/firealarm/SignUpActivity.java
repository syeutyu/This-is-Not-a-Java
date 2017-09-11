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

public class SignUpActivity extends AppCompatActivity {

    private AQuery aQuery;
    private Button btnCancel, btnSignUp;
    private EditText inputName, inputCode, inputPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputName = (EditText) findViewById(R.id.inputName);
        inputCode = (EditText) findViewById(R.id.inputCode);
        inputPlace = (EditText) findViewById(R.id.inputPlace);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             if (inputName.getText().toString().length() == 0) {
                                                 Toast.makeText(SignUpActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
                                                 inputName.requestFocus();
                                                 return;
                                             } else if (inputCode.getText().toString().length() == 0) {
                                                 Toast.makeText(SignUpActivity.this, "기기 번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                                                 inputCode.requestFocus();
                                                 return;
                                             } else if (inputPlace.getText().toString().length() == 0) {
                                                 Toast.makeText(SignUpActivity.this, "설치 위치를 입력하세요!", Toast.LENGTH_SHORT).show();
                                                 inputPlace.requestFocus();
                                                 return;
                                             }

                                             aQuery = new AQuery(getApplicationContext());

                                             String name = inputName.getText().toString();
                                             String code = inputCode.getText().toString();
                                             String place = inputPlace.getText().toString();

                                             Map<String, String> params = new HashMap<>();

                                             params.put("name", name);
                                             params.put("code", code);
                                             params.put("place", place);
                                             Log.d("send data", params.toString());

                                             aQuery.ajax("http://10.156.145.113:3000/auth/signup", params, String.class, new AjaxCallback<String>() {
                                                 @Override
                                                 public void callback(String url, String response, AjaxStatus status) {

                                                     int statusCode = status.getCode();
                                                     Log.d("statusCode", Integer.toString(statusCode));

                                                     if (statusCode == 200) {
                                                         finish();
                                                         Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                                         startActivityForResult(intent, 1000);
                                                     } else if (statusCode == 400) {
                                                         // 400 : 기타 오류 Ex) 자체적인 서버 오류 등
                                                         SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "일시적으로 오류가 발생하였습니다. 다시 시도해 주십시오.", 3000).show();
                                                     } else if (statusCode == 402) {
                                                         SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "잘못된 기기 번호입니다.", 3000).show();
                                                         inputCode.setText("");
                                                     }
                                                 }
                                             });

/*
                    Intent result = new Intent();
                    result.putExtra("Id", inputId.getText().toString());

                    setResult(RESULT_OK, result);
                    finish();
*/

                                         }
                                     }
        );
    }
}