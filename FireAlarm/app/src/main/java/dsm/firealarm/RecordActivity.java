package dsm.firealarm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class RecordActivity extends AppCompatActivity {

    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    private class ViewHolder {
        public ImageView mTestIcon;

        public TextView mUserName;

        public TextView mSpot;

        public TextView mTime;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_item, null);

                holder.mTestIcon = (ImageView) convertView.findViewById(R.id.mTestImage);
                holder.mUserName = (TextView) convertView.findViewById(R.id.mUserName);
                holder.mSpot = (TextView) convertView.findViewById(R.id.mSpot);
                holder.mTime = (TextView) convertView.findViewById(R.id.mTime);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if (mData.mTestIcon != null) {
                holder.mTestIcon.setVisibility(View.VISIBLE);
                holder.mTestIcon.setImageDrawable(mData.mTestIcon);
            }else{
                holder.mTestIcon.setVisibility(View.GONE);
            }

            holder.mUserName.setText(mData.mName);
            holder.mSpot.setText(mData.mSpot);
            holder.mTime = (TextView) convertView.findViewById(R.id.mTime);

            return convertView;
        }

        public void addItem(Drawable icon, String mName, String mSpot, String mTime){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mTestIcon = icon;
            addInfo.mName = mName;
            addInfo.mSpot = mSpot;
            addInfo.mTime = mTime;

            mListData.add(addInfo);
        }

        public void sort(){
            Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mListView = (ListView) findViewById(R.id.mList);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        mAdapter.addItem(getResources().getDrawable(R.drawable.logo),
                "박소현",
                "화재 발생 장소 1",
                "2014-02-18");
        mAdapter.addItem(getResources().getDrawable(R.drawable.logo),
                "이동현",
                "화재 발생 장소 2",
                "2014-02-01");
        mAdapter.addItem(getResources().getDrawable(R.drawable.logo),
                "김경민",
                "화재 발생 장소 3",
                "2014-02-04");
        mAdapter.addItem(getResources().getDrawable(R.drawable.logo),
                "나호겸",
                "화재 발생 장소 4",
                "2014-02-15");

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                ListData mData = mAdapter.mListData.get(position);
                Toast.makeText(RecordActivity.this, mData.mName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
