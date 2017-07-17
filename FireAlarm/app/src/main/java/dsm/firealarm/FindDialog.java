package dsm.firealarm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static dsm.firealarm.R.layout.fragment_find_dialog;


/**
 * Created by 10107박소현 on 2017-07-11.
 */

public class FindDialog extends DialogFragment {

    private int i;
    private final int FIND_ID_DIALOG = 1;
    private final int FIND_PW_DIALOG = 2;

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

        if (i == 1) {
            inputTv.setText("사용자의 고유 코드를 입력해주세요.");
            inputCode.setHint("Code");
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(inputCode.getText().toString().length()==0) {
                        Toast.makeText(FindDialog.this,"코드를 입력해주세요!",Toast.LENGTH_SHORT).show();
                        inputCode.requestFocus();
                    } else if(/**서버로 코드 전송 뒤 일치 된 코드가 없을 경우 */) {

                    }

                    dismiss();
                    android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                    ShowDialog showDialog = new ShowDialog(FIND_ID_DIALOG);
                    showDialog.show(fm, "dialog");
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
                    if(inputCode.getText().toString().length()==0) {
                        Toast.makeText(FindDialog.this,"아이디를 입력해주세요!",Toast.LENGTH_SHORT).show();
                        inputCode.requestFocus();
                    } else if(/**서버로 아이디 전송 뒤 비밀번호 리턴받아야 함 */) {

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