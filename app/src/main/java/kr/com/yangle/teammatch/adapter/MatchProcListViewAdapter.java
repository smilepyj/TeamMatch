package kr.com.yangle.teammatch.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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

public class MatchProcListViewAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    LayoutInflater mLayoutInflater = null;
    private JSONArray mDataJSONArray = null;
    private int mDataJSONArrayCnt = 0;

    private Context mContext;
    private ApplicationTM mApplicationTM;

    public MatchProcListViewAdapter(Context context, JSONArray jsonArray) {
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

            convertView = mLayoutInflater.inflate(R.layout.listview_match_proc, parent, false);
        }

        TextView tv_listview_search_result_area = convertView.findViewById(R.id.tv_listview_search_result_area);
        TextView tv_listview_search_result_ground = convertView.findViewById(R.id.tv_listview_search_result_ground);
        TextView tv_listview_search_result_day = convertView.findViewById(R.id.tv_listview_search_result_day);
        TextView tv_listview_search_result_time = convertView.findViewById(R.id.tv_listview_search_result_time);
        TextView tv_listview_search_result_team_name = convertView.findViewById(R.id.tv_listview_search_result_team_name);
        TextView tv_listview_search_result_team_level = convertView.findViewById(R.id.tv_listview_search_result_team_level);
        TextView tv_listview_search_result_team_member = convertView.findViewById(R.id.tv_listview_search_result_team_member);
        TextView tv_match_proc_name = convertView.findViewById(R.id.tv_match_proc_name);

        String match_proc_cd = "";
        String match_apply_id = "";
        String guest_host_type = "";

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            tv_listview_search_result_area.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_sido_name)).toString() + mContext.getString(R.string.search_result_list_hyphen) + mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_gugun_name)).toString());
            tv_listview_search_result_ground.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_name)).toString());
            Date mDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_date)).toString());
            String mHopeDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_view), Locale.getDefault()).format(mDate);
            tv_listview_search_result_day.setText(mHopeDate);
            Date mTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_time)).toString());
            String mHopeTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_view), Locale.getDefault()).format(mTime);
            tv_listview_search_result_time.setText(mHopeTime);
            tv_listview_search_result_team_name.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_host_name)).toString());
            tv_listview_search_result_team_level.setText(mApplicationTM.getC002().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_team_lvl)).toString()));
            tv_listview_search_result_team_member.setText(mApplicationTM.getC003().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_team_member)).toString()));

            Log.e(TAG, mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_proc_cd_name)).toString());

            String match_proc_cd_name = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_proc_cd_name)).toString();

            match_apply_id = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_apply_id)).toString();
            match_proc_cd = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_proc_cd)).toString();
            guest_host_type = mJSONObject.get(mContext.getString(R.string.matchProclist_result_guest_host_type)).toString();

            if("C004001".equals(match_proc_cd)) {
                if(!"".equals(match_apply_id) && "G".equals(guest_host_type)) {
                    match_proc_cd_name = "매치 신청";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_2));
                }else {
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_1));
                }
            }else if("C004003".equals(match_proc_cd)) {
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_1));
            }else {
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_3));
            }

            tv_match_proc_name.setText(match_proc_cd_name + " 중");

        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            e.printStackTrace();
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }

        LinearLayout ll_listview_match_proc = convertView.findViewById(R.id.ll_listview_match_proc);

        ll_listview_match_proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.cording_message));
            }
        });

        return convertView;
    }
}
