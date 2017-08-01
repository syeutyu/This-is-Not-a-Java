package dsm.firealarm;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 10107박소현 on 2017-07-11.
 */

public class ShowDialog extends DialogFragment {
    private int i;
    // 각종 뷰 변수 선언
    private Button cancelBtn, okBtn;

    public ShowDialog() {
    }

    public ShowDialog(int i) {
        this.i = i;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_dialog, container);

        TextView showInfo = (TextView) view.findViewById(R.id.showInfo);

        okBtn = (Button) view.findViewById(R.id.okBtn);
        cancelBtn = (Button) view.findViewById(R.id.cancelBtn);

        ShowDialog.this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (i == 1) {
            showInfo.setText("사용자의 아이디는");
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    dismiss();
                }
            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FindDialog.class);
                    startActivity(intent);
                }
            });
        } /*else {
            showInfo.setText("사용자의 비밀번호는");
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    dismiss();
                }
            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
*//*

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), FindDialog.class);
                    startActivity(intent);
                }
오류 발생생
*//*
           });
        }*/

        // 레이아웃 XML과 뷰 변수 연결

        return view;
    }
}
