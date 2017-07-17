package dsm.firealarm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private AQuery aQuery;
    private Button btnCancel, btnSignUp;
    private EditText inputId, inputPw, pwConfirm, inputCode, inputName, inputRno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        aQuery = new AQuery(getApplicationContext());

        inputId = (EditText) findViewById(R.id.inputId);
        inputPw = (EditText) findViewById(R.id.inputPw);
        pwConfirm = (EditText) findViewById(R.id.pwConfirm);
        inputCode = (EditText) findViewById(R.id.inputCode);
        inputName = (EditText) findViewById(R.id.inputName);
        inputRno = (EditText) findViewById(R.id.inputRno);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSignUp = (Button) findViewById(R.id.btnSignup);

        pwConfirm.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 비밀번호 일치 검사
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String password = inputPw.getText().toString();
                String confirm = pwConfirm.getText().toString();

                if (password.equals(confirm)) {
                    inputPw.setBackgroundColor(Color.GREEN);
                    pwConfirm.setBackgroundColor(Color.GREEN);
                } else {
                    inputPw.setBackgroundColor(Color.RED);
                    pwConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

                if (inputId.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "아이디를 입력하세요!", Toast.LENGTH_SHORT).show();
                    inputId.requestFocus();
                    return;
                } else if (inputPw.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    inputPw.requestFocus();
                    return;
                } else if (pwConfirm.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    pwConfirm.requestFocus();
                    return;
                } else if (!inputPw.getText().toString().equals(pwConfirm.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    inputPw.setText("");
                    pwConfirm.setText("");
                    pwConfirm.requestFocus();
                    return;
                }
                else if (inputCode.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "코드를 입력하세요!", Toast.LENGTH_SHORT).show();
                    inputCode.requestFocus();
                    return;
                } else if (inputName.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
                    inputPw.requestFocus();
                    return;
                } else if (inputRno.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "호실 번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    inputRno.requestFocus();
                    return;
                }

                aQuery = new AQuery(getApplicationContext());

                String id = inputId.getText().toString();
                String password = inputPw.getText().toString();
                String code = inputCode.getText().toString();
                String name = inputName.getText().toString();
                String rno = inputRno.getText().toString();

                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                params.put("password", password);
                params.put("code", code);
                params.put("name", name);
                params.put("rno", rno);
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
                        } else if (statusCode == 401) {
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "중복된 아이디입니다.", 3000).show();
                            inputId.setText("");
                        } else if (statusCode == 402) {
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "잘못된 코드입니다.", 3000).show();
                        }
                    }
                });

//                    Intent result = new Intent();
//                    result.putExtra("Id", inputId.getText().toString());
//
//                    setResult(RESULT_OK, result);
//                    finish();

            }
        });
    }
}