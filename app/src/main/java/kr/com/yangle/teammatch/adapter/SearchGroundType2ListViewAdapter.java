package kr.com.yangle.teammatch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class SearchGroundType2ListViewAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    LayoutInflater mLayoutInflater = null;
    private JSONArray mDataJSONArray = null;
    private int mDataJSONArrayCnt = 0;

    private Context mContext;
    private ApplicationTM mApplicationTM;

    private String ground_id;

    public SearchGroundType2ListViewAdapter(Context context, JSONArray jsonArray) {
        mDataJSONArray = jsonArray;
        mDataJSONArrayCnt = mDataJSONArray.length();

        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();
    }

    @Override
    public int getCount() {
        return mDataJSONArrayCnt;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getGround_id() { return ground_id; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int mPosition = position;

        if(convertView == null) {
            if(mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            convertView = mLayoutInflater.inflate(R.layout.listview_search_ground_type_2, parent, false);
        }

        TextView tv_listview_search_ground_type_2_1 = convertView.findViewById(R.id.tv_listview_search_ground_type_2_1);
        TextView tv_listview_search_ground_type_2_2 = convertView.findViewById(R.id.tv_listview_search_ground_type_2_2);

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            ground_id = mJSONObject.get("ground_id").toString();
            tv_listview_search_ground_type_2_1.setText(mJSONObject.get("ground_name").toString());

            double distance = Math.round((double)mJSONObject.get("distance")*10)/10.0;
            tv_listview_search_ground_type_2_2.setText(distance + "km");
        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }

        return convertView;
    }
}
