package dsm.firealarm;


import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import static dsm.firealarm.R.layout.fragment_find_dialog;


/**
 * Created by 10107박소현 on 2017-07-11.
 */

public class FindDialog extends DialogFragment {

    private int i;
    private final int FIND_ID_DIALOG = 1;
    private final int FIND_PW_DIALOG = 2;
    private AQuery aQuery;

    // 각종 뷰 변수 선언

    public FindDialog() {

    }

    public FindDialog(int i) {
        this.i = i;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(fragment_find_dialog, container);
        final TextView inputTv = (TextView) view.findViewById(R.id.info);
        final EditText inputCode = (EditText) view.findViewById(R.id.inputCode);
        Button okBtn = (Button) view.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
        aQuery = new AQuery(getActivity());

        FindDialog.this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (i == 1) {
            inputTv.setText("사용자의 고유 코드를 입력해주세요.");
            inputCode.setHint("Code");
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputCode.getText().toString().length() == 0) {
                        Toast.makeText(getActivity(), "코드를 입력해주세요!", Toast.LENGTH_LONG).show();
                        inputCode.requestFocus();
                    }
                    String code = inputCode.getText().toString();

                    Map<String, String> params = new HashMap<>();

                    params.put("code", code);

                    aQuery.ajax("http://10.156.145.113:3000/auth/findid", params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String response, AjaxStatus status) {

                            int statusCode = status.getCode();
                            Log.d("statusCode", Integer.toString(statusCode));

                            if (statusCode == 200) {
                                /** 코드에 따른 아이디 받아와야 함 받아온 아이디는 ShowDialog에서 출력 */
                                dismiss();
                                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                                ShowDialog showDialog = new ShowDialog(FIND_ID_DIALOG);
                                showDialog.show(fm, "dialog");
                            } else if (statusCode == 400) {
                                // 400 : 아이디가 없거나 기타 오류 Ex) 자체적인 서버 오류 등
//                                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "일시적으로 오류가 발생하였습니다. 다시 시도해 주십시오.", 3000).show();
                            }
                        }
                    });

                    /**서버로 코드 전송 뒤 일치 된 코드가 없을 경우 *//*else if () {

                    }*/
                }
            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            inputTv.setText("사용자의 ID를 입력해주세요.");
            inputCode.setHint("Id");
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputCode.getText().toString().length() == 0) {
                        Toast.makeText(getActivity(), "아이디를 입력해주세요!", Toast.LENGTH_LONG).show();
                        inputCode.requestFocus();
//                    } else if(/**서버로 아이디 전송 뒤 비밀번호 리턴받아야 함 */) {

                    }

                    dismiss();
                    android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                    ShowDialog showDialog = new ShowDialog(FIND_PW_DIALOG);
                    showDialog.show(fm, "dialog");
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        // 레이아웃 XML과 뷰 변수 연결

        return view;
    }

}