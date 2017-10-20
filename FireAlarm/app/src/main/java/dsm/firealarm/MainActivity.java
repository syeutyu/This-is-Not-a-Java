package dsm.firealarm;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView record, test, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "권한 확인이 되었습니다", 3000).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "권한 거부가 되었습니다.", 3000).show();
                Log.d("권한 거부 목록: ",deniedPermissions.toString());
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleTitle(R.string.rationale_title)
                .setRationaleMessage("서비스를 이용하시려면 문자 접근 권한 수락이 필요합니다.")
                .setRationaleConfirmText("확인")

                .setDeniedMessage(
                        "권한 거부로 인하여 원활한 서비스 이용이 불가능합니다.\n\n[설정] > [권한]에서 권한을 허용할 수 있습니다.")
                // TODO: 다시 묻지 않기를 선택하고 거절한 후, 실행하면 .setRationMessage와 .setDeniedMessage순으로 실행한 뒤 종료됨

                .setGotoSettingButtonText("확인")
                // TODO: 확인 버튼 눌렀을 때, 앱 종료됨.
                .setPermissions(Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE)
                .check();

        String token = FirebaseInstanceId.getInstance().getToken();
        if(token != null) {
            Log.i("token-----", token);
        } else
            Log.i( "nullCheck: ", "null입니다.");

        record = (TextView) findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        test = (TextView) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

/*      settings = (TextView) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(intent, 1000);
            }
        });*/
    }
}