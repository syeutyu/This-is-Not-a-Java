package dsm.firealarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecordActivity extends AppCompatActivity {

    Retrofit mRetrofit;
    ApiService2 mApiService2;
    ListView listview = null;

    private TextView nameStr;
    private TextView spotStr;
    private TextView timeStr;

    private class ListViewAdapter extends BaseAdapter implements Filterable {
        // Adapter에 추가된 데이터를 저장하기 위한 ArrayList. (원본 데이터 리스트)
        private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();
        // 필터링된 결과 데이터를 저장하기 위한 ArrayList. 최초에는 전체 리스트 보유.
        private ArrayList<ListViewItem> filteredItemList = listViewItemList;


        Filter listFilter;

        // ListViewAdapter의 생성자
        public ListViewAdapter() {
        }

        // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            return filteredItemList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_item, parent, false);
            }

            // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
            ListViewItem listViewItem = filteredItemList.get(position);

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            nameStr = (TextView) convertView.findViewById(R.id.nameStr);
            spotStr = (TextView) convertView.findViewById(R.id.spotStr);
            timeStr = (TextView) convertView.findViewById(R.id.timeStr);

            // 아이템 내 각 위젯에 데이터 반영
            nameStr.setText(listViewItem.getNameStr());
            spotStr.setText(listViewItem.getSpotStr());
            timeStr.setText(listViewItem.getTimeStr());

            return convertView;
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            return filteredItemList.get(position);
        }

        // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.

        public void addItem(String nameStr, String spotStr, String timeStr) {
            ListViewItem item = new ListViewItem();
            item.setNameStr(nameStr);
            item.setSpotStr(spotStr);
            item.setTimeStr(timeStr);

            listViewItemList.add(item);
        }

        // TODO : filtering item.

        @Override
        public Filter getFilter() {
            if (listFilter == null) {
                listFilter = new ListFilter();
            }
            return listFilter;
        }

        private class ListFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.values = listViewItemList;
                    results.count = listViewItemList.size();
                } else {
                    ArrayList<ListViewItem> itemList = new ArrayList<>();

                    for (ListViewItem item : listViewItemList) {
                        if (item.getNameStr().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                                item.getSpotStr().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                                item.getTimeStr().toUpperCase().contains(constraint.toString().toUpperCase())) {
                            itemList.add(item);
                        }
                    }

                    results.values = itemList;
                    results.count = itemList.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                // update listview by filtered data list.
                filteredItemList = (ArrayList<ListViewItem>) results.values;

                // notify
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mRetrofit = new Retrofit.Builder().baseUrl(ApiService2.API_URL).build();
        mApiService2 = mRetrofit.create(ApiService2.class);


        String token= FirebaseInstanceId.getInstance().getToken();
        Log.d("xxx",token);

        Call<Void> call = mApiService2.search(token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                Log.d(this.getClass().getName(),"기록보기 토큰 실행");
                int codee = response.code();
                Log.d("상태코드는요", Integer.toString(codee));
                if(response.code()==203) {
                    /** 토큰값 제대로 줬을 때**/
                    Log.d("xxx", Integer.toString(codee));
//                    Call<JsonObject> calll = mApiService2.getSearch();
//                    call.enqueue(new Callback() {
//                        @Override
//                        public void onResponse(Call call, Response response) {
//                            Log.d(response.body().toString(), "responseCheck");
////                            Iterator<String> iter = json.keys();
////                            while (iter.hasNext()) {
////                                String key = iter.next();
////                                try {
////                                    Object value = json.get(key);
////                                } catch (JSONException e) {
////                                    // Something went wrong!
////                                }
////                            }
//                            JsonObject jsonObject = (JsonObject) response.body();
//                            Log.d(jsonObject.toString(), "checkJson");
//                        }
//
//                        @Override
//                        public void onFailure(Call call, Throwable t) {
//                            Log.d(t.toString(), "logCheck");
//                        }
//                    });
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버 오류가 발생하였습니다.", Toast.LENGTH_LONG);
            }
        });

        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        adapter.addItem(
                "박소현",
                "화재 발생 장소 1",
                "2014-02-18");
        adapter.addItem(
                "이동현",
                "화재 발생 장소 2",
                "2014-02-01");
        adapter.addItem(
                "김경민",
                "화재 발생 장소 3",
                "2014-02-04");
        adapter.addItem(
                "나호겸",
                "화재 발생 장소 4",
                "2014-02-15");

        EditText editTextFilter = (EditText) findViewById(R.id.editTextFilter);
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString();
                ((ListViewAdapter) listview.getAdapter()).getFilter().filter(filterText);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }
}
