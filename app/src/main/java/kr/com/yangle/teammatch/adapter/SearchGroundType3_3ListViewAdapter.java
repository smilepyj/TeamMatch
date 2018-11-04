package kr.com.yangle.teammatch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class SearchGroundType3_3ListViewAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    LayoutInflater mLayoutInflater = null;
    private JSONArray mDataJSONArray = null;
    private int mDataJSONArrayCnt = 0;

    private Context mContext;
    private ApplicationTM mApplicationTM;

    private boolean[] isCheckedConfrim;

    public SearchGroundType3_3ListViewAdapter(Context context, JSONArray jsonArray) {
        mDataJSONArray = jsonArray;
        mDataJSONArrayCnt = mDataJSONArray.length();

        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();

        isCheckedConfrim = new boolean[mDataJSONArrayCnt];
    }

    @Override
    public int getCount() {
        return mDataJSONArrayCnt;
    }

    @Override
    public Object getItem(int position) {
        try {
            return mDataJSONArray.get(position);
        }catch(Exception e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setAllChecked(boolean ischeked) {
        int tempSize = isCheckedConfrim.length;
        for(int a=0 ; a<tempSize ; a++){
            isCheckedConfrim[a] = ischeked;
        }
    }

    public void setChecked(int position) {
        isCheckedConfrim[position] = !isCheckedConfrim[position];
    }

    public boolean getChecked(int position) {
        return isCheckedConfrim[position];
    }

    public ArrayList<Integer> getChecked(){
        int tempSize = isCheckedConfrim.length;
        ArrayList<Integer> mArrayList = new ArrayList<Integer>();
        for(int b=0 ; b<tempSize ; b++){
            if(isCheckedConfrim[b]){
                mArrayList.add(b);
            }
        }
        return mArrayList;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int mPosition = position;

        if(convertView == null) {
            if(mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            convertView = mLayoutInflater.inflate(R.layout.listview_search_ground_type_3_3, parent, false);
        }

        CheckBox cb_listview_search_ground = convertView.findViewById(R.id.cb_listview_search_ground);
        TextView tv_listview_search_ground_type_3_3_1 = convertView.findViewById(R.id.tv_listview_search_ground_type_3_3_1);

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            cb_listview_search_ground.setClickable(false);
            cb_listview_search_ground.setFocusable(false);
            cb_listview_search_ground.setChecked(isCheckedConfrim[position]);
            tv_listview_search_ground_type_3_3_1.setText(mJSONObject.get("ground_name").toString());
        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }

        return convertView;
    }
}
