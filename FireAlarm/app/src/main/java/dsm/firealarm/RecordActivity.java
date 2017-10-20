package dsm.firealarm;

import android.content.Context;
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

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecordActivity extends AppCompatActivity {

    Retrofit mretrofit;
    ApiService mApiService;
    ListView listview = null;

    private TextView nameStr;
    private TextView spotStr;
    private TextView timeStr;
    private TextView codeStr;
    ListViewAdapter adapter;


    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList. (원본 데이터 리스트)
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    private class ListViewAdapter extends BaseAdapter implements Filterable {
        // 필터링된 결과 데이터를 저장하기 위한 ArrayList. 최초에는 전체 리스트 보유.
        Context context;

        public ListViewAdapter(Context context, ArrayList<ListViewItem> filteredItemList) {
            this.context = context;
            this.filteredItemList = filteredItemList;
        }

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
            codeStr = (TextView) convertView.findViewById(R.id.codeStr);

            // 아이템 내 각 위젯에 데이터 반영
            nameStr.setText(listViewItem.getNameStr());
            spotStr.setText(listViewItem.getSpotStr());
            timeStr.setText(listViewItem.getTimeStr());
            codeStr.setText(listViewItem.getCodeStr());

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
        public void addItem(String nameStr, String spotStr, String timeStr, String codeStr) {
            ListViewItem item = new ListViewItem(nameStr, spotStr, timeStr, codeStr);
            item.setNameStr(nameStr);
            item.setSpotStr(spotStr);
            item.setTimeStr(timeStr);
            item.setCodeStr(codeStr);
        }

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

        listview = (ListView) findViewById(R.id.listview1);


        mretrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = mretrofit.create(ApiService.class);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("token-----", token);

        Call<JsonObject> call = mApiService.search(token);
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
                    listview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        Log.d(this.getClass().toString(), "retrofit 오류");

        // Adapter 생성
        adapter = new ListViewAdapter();

        for (int i = 0; i < listViewItemList.size(); i++) {
            adapter.addItem(listViewItemList.get(i).getNameStr(), listViewItemList.get(i).getSpotStr(), listViewItemList.get(i).timeStr, listViewItemList.get(i).codeStr);
        }
        /** 파싱한 결과 */
        // 리스트뷰 참조 및 Adapter달기
        listview.setAdapter(adapter);

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

    public ArrayList getArrayList(JsonArray jsonElement) {
        ArrayList<ListViewItem> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonElement.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonElement.get(i);
            String name = jsonObject.getAsJsonPrimitive("name").getAsString();
            String place = jsonObject.getAsJsonPrimitive("place").getAsString();
            String time = jsonObject.getAsJsonPrimitive("time").getAsString();
            String code = jsonObject.getAsJsonPrimitive("code").getAsString();

            arrayList.add(new ListViewItem(name, place, time, code));
        }
        return arrayList;
    }
}