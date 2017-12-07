package dsm.firealarm;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;

/**
 * Created by dsm2016 on 2017-11-06.
 */

public class CustomDialog extends Dialog {
    Retrofit retrofit;
    ApiService apiService;

    public Activity activity;
    public EditText modify_place;
    public Button btnOk;

    String code;

    CustomDialog customDialog;
    SettingActivity settingActivitytivity;

    SettingActivity settingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingActivity = (SettingActivity) SettingActivity.settingActivity;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog);

//        settingActivity = new SettingActivity();

        modify_place = (EditText) findViewById(R.id.modify_place);
        btnOk = (Button) findViewById(R.id.btnOk);

        Log.d("before start", "log");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("after btnok click", "log");
                post();
                Log.d("finish post method", "====");
                dismiss();
            }
        });
    }

    public CustomDialog(Activity a) {
        super(a);
        this.activity = a;
    }

/*    public CustomDialog(@NonNull Context context) {
        super(context);
    }*/

    public void post() {
        //server parsing
        Log.d("test", "test");

        String token = FirebaseInstanceId.getInstance().getToken();
        String place = modify_place.getText().toString();
        String tmpCode = getCode();

        Log.d("code value", token);
        Log.d("code value", place);
//        Log.d("code value", tmpCode);

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        Call<Void> call = apiService.modify(token, place, tmpCode);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Log.d(getClass().getName(), "response code 200");

                } else if (response.code() == 400) {
                    Log.d(getClass().getName(), "response code 400");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
/*        try {
            String code = settingActivity.listViewItemList.get(settingActivity.getSelectedPos()).getCode().toString();
            Log.d("code value", code);

        } catch (NullPointerException e) {
            Log.d("code", "code");
        }*/
        Log.d(getClass().getName().toString(), "btnOk");


    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}