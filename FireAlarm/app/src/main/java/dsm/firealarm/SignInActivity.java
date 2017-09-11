package dsm.firealarm;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private AQuery aQuery;
    private TextView idInput, pwInput, findId, findPw, tvSignUp;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        idInput = (EditText) findViewById(R.id.idInput);
        pwInput = (EditText) findViewById(R.id.pwInput);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        findId = (TextView) findViewById(R.id.findId);
        findPw = (TextView) findViewById(R.id.findPw);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 1000);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aQuery = new AQuery(getApplicationContext());

                String id = idInput.getText().toString();
                String password = pwInput.getText().toString();

                if (!id.isEmpty() && !password.isEmpty()) {
                    Map<String, String> params = new HashMap<>();

                    params.put("id", id);
                    params.put("password", password);

                    aQuery.ajax("http://10.156.145.113:3000/auth/signin", params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String response, AjaxStatus status) {
                            int statusCode = status.getCode();
                            Log.d("statusCode", Integer.toString(statusCode));
                            if (statusCode == 200) {
                                finish();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivityForResult(intent, 1000);
                                //SnackbarManager.createCancelableSnackbar(MainActivity.class, "로그인 성공", 3000).show();
                            } else {
                                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "아이디나 비밀번호를 확인하세요.", 3000).show();
                            }
                        }
                    });
                } else {
                    if (id.length() == 0)
                        Toast.makeText(SignInActivity.this, "아이디를 입력하세요!", Toast.LENGTH_SHORT).show();
                    else if (password.length() == 0)
                        Toast.makeText(SignInActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FindDialog dialog = new FindDialog(1);
                dialog.show(fm, "fragment_dialog_test");
            }
        });

        findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FindDialog dialog = new FindDialog(2);
                dialog.show(fm, "dialog");
            }
        });

    }

/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if(requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(SignInActivity.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
            idInput.setText(data.getStringExtra("inputId"));
        }
    }
*/

}