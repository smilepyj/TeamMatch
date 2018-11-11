package kr.com.yangle.teammatch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class SearchResultListViewAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    private LayoutInflater mLayoutInflater = null;
    private JSONArray mDataJSONArray;
    private int mDataJSONArrayCnt;

    private Context mContext;
    private ApplicationTM mApplicationTM;

    public SearchResultListViewAdapter(Context context, JSONArray jsonArray) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int mPosition = position;

        if(convertView == null) {
            if(mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            convertView = mLayoutInflater.inflate(R.layout.listview_search_result_match, parent, false);
        }

        TextView tv_listview_search_result_area = convertView.findViewById(R.id.tv_listview_search_result_area);
        TextView tv_listview_search_result_ground = convertView.findViewById(R.id.tv_listview_search_result_ground);
        TextView tv_listview_search_result_day = convertView.findViewById(R.id.tv_listview_search_result_day);
        TextView tv_listview_search_result_time = convertView.findViewById(R.id.tv_listview_search_result_time);
        TextView tv_listview_search_result_team_name = convertView.findViewById(R.id.tv_listview_search_result_team_name);
        TextView tv_listview_search_result_team_level = convertView.findViewById(R.id.tv_listview_search_result_team_level);
        TextView tv_listview_search_result_team_member = convertView.findViewById(R.id.tv_listview_search_result_team_member);

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            tv_listview_search_result_area.setText(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_sido_name)).toString() + mContext.getString(R.string.search_result_list_hyphen) + mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_gugun_name)).toString());
            tv_listview_search_result_ground.setText(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_name)).toString());
            Date mDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_date)).toString());
            String mHopeDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_view), Locale.getDefault()).format(mDate);
            tv_listview_search_result_day.setText(mHopeDate);
            Date mTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_time)).toString());
            String mHopeTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_view), Locale.getDefault()).format(mTime);
            tv_listview_search_result_time.setText(mHopeTime);
            tv_listview_search_result_team_name.setText(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_host_name)).toString());
            tv_listview_search_result_team_level.setText(mApplicationTM.getC002().get(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_team_lvl)).toString()));
            tv_listview_search_result_team_member.setText(mApplicationTM.getC003().get(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_team_member)).toString()));
        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }

        LinearLayout ll_listview_search_result_request = convertView.findViewById(R.id.ll_listview_search_result_request);

        ll_listview_search_result_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.cording_message));
            }
        });

        return convertView;
    }
}
