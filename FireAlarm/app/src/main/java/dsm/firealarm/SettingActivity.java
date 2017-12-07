package dsm.firealarm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static dsm.firealarm.R.id.modify_place;

public class SettingActivity extends AppCompatActivity {
    public static Activity settingActivity;

    Retrofit mRetrofit;
    ApiService apiService;
    ListView listView = null;
    ListViewAdapter adapter;

    private TextView modify;
    private TextView delete;
    private TextView code;
    private TextView place;
    private RadioButton radioBtn;
    private FloatingActionButton fab;
    CustomDialog customDialog;

    private int selectedPos;

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList. (원본 데이터 리스트)
    public ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        settingActivity = SettingActivity.this;

        // adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.listview2);
        // listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        // listView.setAdapter(adapter);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = mRetrofit.create(ApiService.class);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("token-----", token);

        final Call<JsonObject> call = apiService.settings(token);
        Log.d(this.getClass().getName(), "call 실행 전");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(this.getClass().getName(), "응답 실행");
                Log.e("data : ", "onResponse: " + response.body().toString());
                int code = response.code();
                Log.d("상태코드", Integer.toString(code));
                if (response.code() == 200) {
                    JsonArray jsonArray = response.body().getAsJsonArray("key");
                    JsonArray jsonElement = jsonArray.getAsJsonArray();
                    listViewItemList = getArrayList(jsonElement);
                    adapter = new ListViewAdapter(getApplicationContext(), listViewItemList);
                    listView.setAdapter(adapter);
                } else if (response.code() == 404) {
                    SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "서버 오류입니다.", 3000).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(this.getClass().toString(), "retrofit Failure");
            }
        });
        Log.d(this.getClass().toString(), "retrofit 오류");
        // Adapter 생성
        adapter = new ListViewAdapter();

        for (int i = 0; i < listViewItemList.size(); i++) {
            adapter.addItem(listViewItemList.get(i).getCode(), listViewItemList.get(i).getPlace());
        }
        /** 파싱한 결과 */
        // 리스트뷰 참조 및 Adapter달기
        listView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), AddModuleActivity.class);
                startActivity(intent);
            }
        });
        // final int selectedPos = -1;

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;

                Log.d("onItemLongClick: ", "실행");
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(SettingActivity.this);
                alert_confirm.setMessage("삭제하시겠어요?");
                alert_confirm.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(getClass().getName().toString(), String.valueOf(selectedPos));
                        try {
/*                            if (selectedPos == 0) {
                                code = listViewItemList.get(selectedPos).getCode().toString();
                            } else if (selectedPos >= 1) {
                                code = listViewItemList.get(selectedPos - 1).getCode().toString();
                            }*/
                            String token = FirebaseInstanceId.getInstance().getToken();
                            String code;
                            code = listViewItemList.get(selectedPos).getCode().toString();
                            Log.d("code", code);

                            Intent intent = new Intent();
                            intent.putExtra("code", code);
                            setResult(0, intent);

                            Call<Void> call1 = apiService.delete(token, code);
                            call1.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() == 204) {
                                        SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "삭제가 완료되었습니다.", 3000).show();
                                    } else if (response.code() == 400) {
                                        SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "일시적인 서버 오류입니다.", 3000).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.d("retrofit fail", "onFailure: ");
                                }
                            });
                            listViewItemList.remove(selectedPos);
                            adapter.notifyDataSetChanged();
                        } catch (IndexOutOfBoundsException e) {
                            Log.d("indexoutof", "onClick: ");
                        }
                    }
                });
                AlertDialog alertDialog = alert_confirm.create();
                alertDialog.show();
                return true; // 다음 이벤트 계속 진행 false, 이벤트 완료 true
            }
        });

        final Activity activity = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customDialog = new CustomDialog(activity);
                Log.d("log log log show", listViewItemList.get(selectedPos).getCode().toString());
                customDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        customDialog.setCode(listViewItemList.get(selectedPos).getCode().toString());
                        Log.d("log log log show", listViewItemList.get(selectedPos).getCode().toString());
                    }
                });
                if (customDialog == null) {
                    Log.d(getApplication().getClass().getName(), "customDialog null");
                } else if (customDialog != null) {
                    Log.d(getApplication().getClass().getName(), "customDialog not null");
                }
                Log.d(getApplication().getClass().getName(), "test log");
                customDialog.show();
            }
        });

/*        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);
                // String code = item.getCode();
                Toast.makeText(getApplicationContext(), listView.getItemAtPosition(position).toString() + "/" + Long.toString(position), Toast.LENGTH_SHORT).show();
            }
        });*/

/*        modify = (TextView) findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        delete = (TextView) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*int pos = listView.getCheckedItemPosition();
                if (pos != ListView.INVALID_POSITION) {
                    listViewItemList.remove(pos);
                    listView.clearChoices();
                    adapter.notifyDataSetChanged();
                }*//*
                Log.d("onClick: ","삭제 실행");
                int count, checked;
                count = adapter.getCount();
                if(count > 0) {
                    checked = lastCheckPosition;
                    if(checked>-1 && checked<count) {
                        listViewItemList.remove(checked);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });*/
        //finish();
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    private View.OnClickListener singleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "확인 버튼 Click!", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
        }
    };

    public ArrayList getArrayList(JsonArray jsonElement) {
        ArrayList<ListViewItem> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonElement.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonElement.get(i);
            String code = jsonObject.getAsJsonPrimitive("code").getAsString();
            String place = jsonObject.getAsJsonPrimitive("place").getAsString();
            arrayList.add(new ListViewItem(code, place));
        }
        return arrayList;
    }


    private class ListViewAdapter extends BaseAdapter {
        private ArrayList<ListViewItem> listViewItems = listViewItemList;

        // private ListViewItem item;

        Context context;

        public ListViewAdapter(Context applicationContext, ArrayList<ListViewItem> listViewItems) {
            this.context = applicationContext;
            this.listViewItems = listViewItems;
        }

        public ListViewAdapter() {
        }


        @Override
        public int getCount() {
            return listViewItems.size();
        }

        @Override
        public Object getItem(int position) {
            return listViewItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_settings, parent, false);
            }

            ListViewItem listViewItem = listViewItems.get(position);

            code = (TextView) convertView.findViewById(R.id.code);
            code.setText(listViewItem.getCode());

            place = (TextView) convertView.findViewById(R.id.place);
            place.setText(listViewItem.getPlace());


/*            radioBtn = (RadioButton) convertView.findViewById(R.id.radioBtn);
            final int pos = position;
            if (lastCheckPosition == pos) {
                radioBtn.setChecked(true);
            } else {
                radioBtn.setChecked(false);
            }*/

/*            radioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (lastCheckPosition == pos) {
                        return;
                    }
                    lastCheckPosition = pos;
                    if (mLashSelectedRadioButton != null) {
                        mLashSelectedRadioButton.setChecked(false);
                    }
                    mLashSelectedRadioButton = (RadioButton) buttonView;
                    notifyDataSetChanged();
                }
            });*/

            return convertView;
        }


        public void addItem(String code, String place) {
            ListViewItem item = new ListViewItem(code, place);
            item.setCode(code);
            item.setPlace(place);
            // listViewItemList.add(item);
        }
    }
}

/*public void modifyDialog(String oldItem, final int index) {
    final Dialog dialog = new Dialog(SettingActivity.this);
    dialog.setTitle("설치 위치 수정");
    dialog.setContentView(R.layout.modify_dialog);
    TextView txtMessage = (TextView) findViewById(R.id.txtMessage);
    txtMessage.setText("변경된 위치를 입력해주세요.");
    txtMessage.setTextColor(Color.parseColor("#000000"));
    final EditText editText = (EditText)dialog.findViewById(R.id.txtInput);
    editText.setText(oldItem);
    Button btn = (Button) dialog.findViewById(R.id.btnDone);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            arrayList.set(index,getText().toString());
            arrayAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    });
    dialog.show();
}*/