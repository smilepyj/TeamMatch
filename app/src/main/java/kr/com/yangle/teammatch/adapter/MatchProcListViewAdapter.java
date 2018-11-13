package kr.com.yangle.teammatch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.GroundDetailActivity;
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

        LinearLayout ll_list_view_match_proc_opponent_line = convertView.findViewById(R.id.ll_list_view_match_proc_opponent_line);
        LinearLayout ll_list_view_match_proc_opponent_info = convertView.findViewById(R.id.ll_list_view_match_proc_opponent_info);

        TextView tv_listview_match_proc_area = convertView.findViewById(R.id.tv_listview_match_proc_area);
        TextView tv_listview_match_proc_ground = convertView.findViewById(R.id.tv_listview_match_proc_ground);
        ImageButton ib_listview_match_proc_ground = convertView.findViewById(R.id.ib_listview_match_proc_ground);
        TextView tv_listview_match_proc_day = convertView.findViewById(R.id.tv_listview_match_proc_day);
        TextView tv_listview_match_proc_time = convertView.findViewById(R.id.tv_listview_match_proc_time);
        TextView tv_listview_match_proc_team_name = convertView.findViewById(R.id.tv_listview_match_proc_team_name);
        TextView tv_listview_match_proc_team_level = convertView.findViewById(R.id.tv_listview_match_proc_team_level);
        TextView tv_listview_match_proc_team_member = convertView.findViewById(R.id.tv_listview_match_proc_team_member);
        TextView tv_listview_match_proc_opponent_name = convertView.findViewById(R.id.tv_listview_match_proc_opponent_name);
        TextView tv_listview_match_proc_opponent_level = convertView.findViewById(R.id.tv_listview_match_proc_opponent_level);
        TextView tv_listview_match_proc_opponent_point = convertView.findViewById(R.id.tv_listview_match_proc_opponent_point);
        TextView tv_match_proc_name = convertView.findViewById(R.id.tv_match_proc_name);
        LinearLayout ll_listview_match_proc = convertView.findViewById(R.id.ll_listview_match_proc);

        String match_proc_cd = "";
        String match_apply_id = "";
        String guest_host_type = "";

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            final String match_hope_ground_id = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_id)).toString();

            tv_listview_match_proc_area.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_sido_name)).toString() + mContext.getString(R.string.search_result_list_hyphen) + mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_gugun_name)).toString());
            tv_listview_match_proc_ground.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_name)).toString());
            Date mDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_date)).toString());
            String mHopeDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_view), Locale.getDefault()).format(mDate);
            tv_listview_match_proc_day.setText(mHopeDate);
            Date mStartTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_start_time)).toString());
            String mHopeStartTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_view), Locale.getDefault()).format(mStartTime);
            Date mEndTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_end_time)).toString());
            String mHopeEndTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_view), Locale.getDefault()).format(mEndTime);
            tv_listview_match_proc_time.setText(mHopeStartTime + " ~ " + mHopeEndTime);
            tv_listview_match_proc_team_name.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_host_name)).toString());
            tv_listview_match_proc_team_level.setText(mApplicationTM.getC002().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_team_lvl)).toString()));
            tv_listview_match_proc_team_member.setText(mApplicationTM.getC003().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_team_member)).toString()));
            tv_listview_match_proc_opponent_name.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_name)).toString());
            tv_listview_match_proc_opponent_level.setText(mApplicationTM.getC002().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_lvl)).toString()));
            String opponent_point = mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_point))==null?"":mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_point)).toString();
            tv_listview_match_proc_opponent_point.setText(opponent_point==""?"":(opponent_point + "점"));

            Log.e(TAG, mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_proc_cd_name)).toString());

            String match_proc_cd_name;

            match_apply_id = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_apply_id)).toString();
            match_proc_cd = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_proc_cd)).toString();
            guest_host_type = mJSONObject.get(mContext.getString(R.string.matchProclist_result_guest_host_type)).toString();

            ll_list_view_match_proc_opponent_line.setVisibility(View.VISIBLE);
            ll_list_view_match_proc_opponent_info.setVisibility(View.VISIBLE);

            if("C004001".equals(match_proc_cd)) {
                if(!"".equals(match_apply_id) && "G".equals(guest_host_type)) {
                    match_proc_cd_name = "매치 신청 중";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_2));
                }else {
                    if("".equals(match_apply_id)) {
                        ll_list_view_match_proc_opponent_line.setVisibility(View.GONE);
                        ll_list_view_match_proc_opponent_info.setVisibility(View.GONE);
                    }

                    match_proc_cd_name = "매치 등록 중";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_1));
                }
            }else if("C004003".equals(match_proc_cd)) {
                match_proc_cd_name = "매치 승인 중";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_3));
            }else if("C004004".equals(match_proc_cd) || "C004005".equals(match_proc_cd)) {
                match_proc_cd_name = "매치 진행 중";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_4));
            }else {
                match_proc_cd_name = "매치 완료";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_5));
            }

            tv_match_proc_name.setText(match_proc_cd_name);

            tv_listview_match_proc_ground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, GroundDetailActivity.class);
                    mIntent.putExtra(mContext.getString(R.string.ground_detail_ground_id), match_hope_ground_id);
                    mContext.startActivity(mIntent);
                }
            });

            ib_listview_match_proc_ground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, GroundDetailActivity.class);
                    mIntent.putExtra(mContext.getString(R.string.ground_detail_ground_id), match_hope_ground_id);
                    mContext.startActivity(mIntent);
                }
            });

            ll_listview_match_proc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mApplicationTM.makeToast(mContext, mContext.getString(R.string.cording_message));
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            e.printStackTrace();
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }

        return convertView;
    }
}
