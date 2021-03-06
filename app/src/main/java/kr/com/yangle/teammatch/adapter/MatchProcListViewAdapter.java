package kr.com.yangle.teammatch.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.GroundDetailActivity;
import kr.com.yangle.teammatch.R;
import kr.com.yangle.teammatch.dialog.DialogMatchApplyActivity;
import kr.com.yangle.teammatch.dialog.DialogMatchSuccessActivity;
import kr.com.yangle.teammatch.dialog.DialogOkAlertActivity;
import kr.com.yangle.teammatch.dialog.DialogRatingActivity;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class MatchProcListViewAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    LayoutInflater mLayoutInflater = null;
    private JSONArray mDataJSONArray = null;
    private int mDataJSONArrayCnt = 0;

    private Activity mActivity;
    private Context mContext;
    private ApplicationTM mApplicationTM;

    private Service mService;

    public MatchProcListViewAdapter(Activity activity) {
        mActivity = activity;
        mContext = activity;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();

        mService = new Service(mActivity);
    }

    public void setMDataJSONArray(JSONArray jsonArray) {
        mDataJSONArray = jsonArray;
        mDataJSONArrayCnt = mDataJSONArray.length();
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
        ImageView iv_listview_match_proc_pre_payment = convertView.findViewById(R.id.iv_listview_match_proc_pre_payment);
        TextView tv_listview_match_proc_pre_payment = convertView.findViewById(R.id.tv_listview_match_proc_pre_payment);
        TextView tv_match_proc_name = convertView.findViewById(R.id.tv_match_proc_name);
        LinearLayout ll_listview_match_proc = convertView.findViewById(R.id.ll_listview_match_proc);

        String match_proc_cd = "";
        String guest_host_type = "";

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            final String match_id = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_id)).toString();
            final String match_apply_id = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_apply_id)).toString();
            final String match_hope_ground_id = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_id)).toString();

            tv_listview_match_proc_area.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_sido_name)).toString() + mContext.getString(R.string.search_result_list_hyphen) + mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_gugun_name)).toString());
            tv_listview_match_proc_ground.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_ground_name)).toString());
            Date mDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_date)).toString());
            String mHopeDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_view), Locale.getDefault()).format(mDate);
            tv_listview_match_proc_day.setText(mHopeDate);
            Date mStartTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_start_time)).toString());
//            String mHopeStartTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_view), Locale.getDefault()).format(mStartTime);
            String mHopeStartTime = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_start_time)).toString().substring(0, 2);
            Date mEndTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_end_time)).toString());
            String mHopeEndTime = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_end_time)).toString().substring(0, 2);
            tv_listview_match_proc_time.setText(mHopeStartTime + "시" + " ~ " + mHopeEndTime + "시");
            tv_listview_match_proc_team_name.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_host_name)).toString());
            tv_listview_match_proc_team_level.setText(mApplicationTM.getC002().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_team_lvl)).toString()));
            tv_listview_match_proc_team_member.setText(mApplicationTM.getC003().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_hope_team_member)).toString()));
            tv_listview_match_proc_opponent_name.setText(mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_name)).toString());
            tv_listview_match_proc_opponent_level.setText(mApplicationTM.getC002().get(mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_lvl)).toString()));
            String opponent_point = mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_point))==null?"":mJSONObject.get(mContext.getString(R.string.matchProclist_result_opponent_point)).toString();
            tv_listview_match_proc_opponent_point.setText(opponent_point==""?"":(opponent_point + "점"));
            String pre_payment_at = mJSONObject.get(mContext.getString(R.string.matchProclist_result_pre_payment_at))==null?"":mJSONObject.get(mContext.getString(R.string.matchProclist_result_pre_payment_at)).toString();
            if("Y".equals(pre_payment_at)) {
                iv_listview_match_proc_pre_payment.setVisibility(View.VISIBLE);
                tv_listview_match_proc_pre_payment.setVisibility(View.VISIBLE);
            }else {
                iv_listview_match_proc_pre_payment.setVisibility(View.GONE);
                tv_listview_match_proc_pre_payment.setVisibility(View.GONE);
            }

            Log.e(TAG, mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_proc_cd_name)).toString());

            String match_proc_cd_name;
            final String match_proc_type;

            match_proc_cd = mJSONObject.get(mContext.getString(R.string.matchProclist_result_match_proc_cd)).toString();
            guest_host_type = mJSONObject.get(mContext.getString(R.string.matchProclist_result_guest_host_type)).toString();

            ll_list_view_match_proc_opponent_line.setVisibility(View.VISIBLE);
            ll_list_view_match_proc_opponent_info.setVisibility(View.VISIBLE);

            String eval_at = mJSONObject.get(mContext.getString(R.string.matchProclist_result_eval_at)).toString();

            if("C004001".equals(match_proc_cd)) {
                if(!"".equals(match_apply_id) && "G".equals(guest_host_type)) {
                    match_proc_cd_name = "매치 신청 중";
                    match_proc_type = "3";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_2));
                }else {
                    if("".equals(match_apply_id)) {
                        ll_list_view_match_proc_opponent_line.setVisibility(View.GONE);
                        ll_list_view_match_proc_opponent_info.setVisibility(View.GONE);
                        match_proc_type = "1";
                    }else {
                        match_proc_type = "2";
                    }

                    match_proc_cd_name = "매치 등록 중";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_1));
                }
            }else if("C004003".equals(match_proc_cd)) {
                match_proc_cd_name = "매치 승인 중";
                match_proc_type = "4";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_3));
            }else if("C004004".equals(match_proc_cd)) {
                match_proc_cd_name = "매치 진행 중";
                match_proc_type = "5";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_4));
            }else if("C004005".equals(match_proc_cd)) {
                if("Y".equals(eval_at)) {
                    match_proc_cd_name = "매치 완료";
                    match_proc_type = "7";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_5));
                }else {
                    match_proc_cd_name = "매치 진행 중";
                    match_proc_type = "6";
                    tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_4));
                }
            }else if("C004007".equals(match_proc_cd)) {
                match_proc_cd_name = "매치 반려";
                match_proc_type = "8";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_listview_match_proc_name_6));
            }else if("C004999".equals(match_proc_cd)) {
                match_proc_cd_name = "매치 만료";
                match_proc_type = "999";
                tv_match_proc_name.setTextColor(ContextCompat.getColor(mContext, R.color.color_313131));
            }else {
                match_proc_cd_name = "매치 완료";
                match_proc_type = "7";
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

                    if("1".equals(match_proc_type)) {
//                        mApplicationTM.makeToast(mContext, "등록된 매치에 대해 신청된 정보가 없습니다.");

                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

                        builder.setTitle("매치 등록 취소")
                                .setMessage("등록된 매치를 취소하시겠습니까?")
                                .setPositiveButton("매치 등록 취소", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 매치 등록 취소
                                        mService.registMatchCancel(searchMatchAlertInfo_Listener, match_id);
                                    }
                                })
                                .setNegativeButton("닫기", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else if("2".equals(match_proc_type)) {
                        mService.searchMatchAlertInfo(searchMatchAlertInfo_Listener, match_id, match_apply_id, match_proc_type);
                    }else if("3".equals(match_proc_type)) {
                        mApplicationTM.makeToast(mContext, "상대팀의 승낙여부가 나올때까지 기다려주세요.");
                    }else if("4".equals(match_proc_type)) {
                        mApplicationTM.makeToast(mContext, "구장이용 가능 여부를 확인 중입니다.");
                    }else if("5".equals(match_proc_type)) {
//                        mService.searchMatchAlertInfo(searchMatchAlertInfo_Listener, match_id, match_apply_id, match_proc_type);

                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

                        builder.setTitle("매치 취소")
                                .setMessage("성사된 매치를 취소하시겠습니까?")
                                .setPositiveButton("매치 취소", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 매치 취소
                                        mService.acceptMatchCancel(searchMatchAlertInfo_Listener, match_id);
                                    }
                                })
                                .setNegativeButton("닫기", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }else if("6".equals(match_proc_type)) {
                        mService.searchMatchAlertInfo(searchMatchAlertInfo_Listener, match_id, match_apply_id, match_proc_type);
                    }else if("7".equals(match_proc_type)) {
                        mApplicationTM.makeToast(mContext, "종료된 매치 입니다.");
                    }else if("8".equals(match_proc_type)) {
                        mApplicationTM.makeToast(mContext, "해당 구장이 이미 예약되어 있습니다.");
                    }else if("999".equals(match_proc_type)){
                        mApplicationTM.makeToast(mContext, "매치가 성사되지 않아 자동 만료 되었습니다.");
                    }
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            e.printStackTrace();
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }

        return convertView;
    }

    ResponseListener searchMatchAlertInfo_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(mContext.getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));
                    JSONObject data = (JSONObject)mJSONArray.get(0);

                    String match_alert_type = data.getString("match_alert_type");

                    if("2".equals(match_alert_type)) {
                        Intent mIntent = new Intent(mContext, DialogMatchApplyActivity.class);
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_title), "매치 신청");
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_contents), "상대편이 매치를 신청하였습니다.\n수락하시겠습니까?");
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_match_id), data.getString("match_id"));
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_match_apply_id), data.getString("match_apply_id"));
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_team_id), data.getString("opponent_id"));
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_team_name), data.getString("opponent_name"));
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_team_lvl), data.getString("opponent_lvl"));
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_team_point), data.getString("opponent_point"));
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_match_time), data.getString("hope_match_time"));
                        mIntent.putExtra(mContext.getString(R.string.match_apply_extra_match_ground), data.getString("hope_match_ground"));
                        mContext.startActivity(mIntent);
                    }else if("4".equals(match_alert_type)) {
                        String match_id = data.getString("match_id");
                        String host_team_id = data.getString("host_team_id");
                        String host_team_name = data.getString("host_team_name");
                        String host_team_lvl = data.getString("host_team_lvl");
                        String host_team_point = data.getString("host_team_point");
                        String host_team_user_name = data.getString("host_team_user_name");
                        String host_team_user_tel = data.getString("host_team_user_tel");
                        String guest_team_id = data.getString("guest_team_id");
                        String guest_team_name = data.getString("guest_team_name");
                        String guest_team_lvl = data.getString("guest_team_lvl");
                        String guest_team_point = data.getString("guest_team_point");
                        String guest_team_user_name = data.getString("guest_team_user_name");
                        String guest_team_user_tel = data.getString("guest_team_user_tel");
                        String hope_match_time = data.getString("hope_match_time");
                        String hope_match_ground = data.getString("hope_match_ground");
                        String hope_match_ground_tel = data.getString("hope_match_ground_tel");
                        String hope_match_ground_cost = data.getString("hope_match_ground_cost");

                        if(mApplicationTM.getTeamId().equals(host_team_id)) {
                            Intent mIntent = new Intent(mContext, DialogMatchSuccessActivity.class);
                            mIntent.putExtra(mContext.getString(R.string.match_success_extra_type), "HOST");
                            mIntent.putExtra("SUB_TITLE", mContext.getString(R.string.match_success_dialog_sub_title_regist));
                            mIntent.putExtra("SUB_TITLE_ETC", mContext.getString(R.string.match_success_dialog_sub_title_etc_proc));
                            mIntent.putExtra("MATCH_ID", match_id);
                            mIntent.putExtra("HOST_TEAM_NAME", host_team_name);
                            mIntent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                            mIntent.putExtra("HOST_TEAM_POINT", host_team_point);
                            mIntent.putExtra("HOST_TEAM_USER_NAME", host_team_user_name);
                            mIntent.putExtra("HOST_TEAM_USER_TEL", host_team_user_tel);
                            mIntent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                            mIntent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                            mIntent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                            mIntent.putExtra("GUEST_TEAM_USER_NAME", guest_team_user_name);
                            mIntent.putExtra("GUEST_TEAM_USER_TEL", guest_team_user_tel);
                            mIntent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
                            mIntent.putExtra("MATCH_TIME", hope_match_time);
                            mIntent.putExtra("GROUND_TEL", hope_match_ground_tel);
                            mIntent.putExtra("GROUND_COST", hope_match_ground_cost);
                            mIntent.putExtra("NOTICE", mContext.getString(R.string.match_success_dialog_contents_proc));
                            mContext.startActivity(mIntent);

                        }else if(mApplicationTM.getTeamId().equals(guest_team_id)){
                            Intent mIntent = new Intent(mContext, DialogMatchSuccessActivity.class);
                            mIntent.putExtra(mContext.getString(R.string.match_success_extra_type), "GUEST");
                            mIntent.putExtra("SUB_TITLE", mContext.getString(R.string.match_success_dialog_sub_title_proc));
                            mIntent.putExtra("MATCH_ID", match_id);
                            mIntent.putExtra("HOST_TEAM_NAME", host_team_name);
                            mIntent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                            mIntent.putExtra("HOST_TEAM_POINT", host_team_point);
                            mIntent.putExtra("HOST_TEAM_USER_NAME", host_team_user_name);
                            mIntent.putExtra("HOST_TEAM_USER_TEL", host_team_user_tel);
                            mIntent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                            mIntent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                            mIntent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                            mIntent.putExtra("GUEST_TEAM_USER_NAME", guest_team_user_name);
                            mIntent.putExtra("GUEST_TEAM_USER_TEL", guest_team_user_tel);
                            mIntent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
                            mIntent.putExtra("MATCH_TIME", hope_match_time);
                            mIntent.putExtra("GROUND_TEL", hope_match_ground_tel);
                            mIntent.putExtra("GROUND_COST", hope_match_ground_cost);
                            mIntent.putExtra("NOTICE", mContext.getString(R.string.match_success_dialog_contents_regist));
                            mContext.startActivity(mIntent);
                        }

                    }else if("6".equals(match_alert_type)) {
                        String match_id = data.getString("match_id");
                        String host_team_id = data.getString("host_team_id");
                        String host_team_name = data.getString("host_team_name");
                        String host_team_lvl = data.getString("host_team_lvl");
                        String host_team_point = data.getString("host_team_point");
                        String guest_team_id = data.getString("guest_team_id");
                        String guest_team_name = data.getString("guest_team_name");
                        String guest_team_lvl = data.getString("guest_team_lvl");
                        String guest_team_point = data.getString("guest_team_point");
                        String hope_match_time = data.getString("hope_match_time");
                        String hope_match_ground = data.getString("hope_match_ground");

                        if(mApplicationTM.getTeamId().equals(host_team_id)){
                            Intent mIntent = new Intent(mContext, DialogRatingActivity.class);
                            mIntent.putExtra(mContext.getString(R.string.match_success_extra_type), "HOST");
                            mIntent.putExtra("MATCH_ID", match_id);
                            mIntent.putExtra("TEAM_ID", guest_team_id);
                            mIntent.putExtra("TEAM_NAME", guest_team_name);
                            mIntent.putExtra("TEAM_LVL", guest_team_lvl);
                            mIntent.putExtra("TEAM_POINT", guest_team_point);
                            mIntent.putExtra("GROUND_NAME", hope_match_ground);
                            mIntent.putExtra("MATCH_TIME", hope_match_time);
                            mContext.startActivity(mIntent);
                        }else if(mApplicationTM.getTeamId().equals(guest_team_id)){
                            Intent mIntent = new Intent(mContext, DialogRatingActivity.class);
                            mIntent.putExtra(mContext.getString(R.string.match_success_extra_type), "GUEST");
                            mIntent.putExtra("MATCH_ID", match_id);
                            mIntent.putExtra("TEAM_ID", host_team_id);
                            mIntent.putExtra("TEAM_NAME", host_team_name);
                            mIntent.putExtra("TEAM_LVL", host_team_lvl);
                            mIntent.putExtra("TEAM_POINT", host_team_point);
                            mIntent.putExtra("GROUND_NAME", hope_match_ground);
                            mIntent.putExtra("MATCH_TIME", hope_match_time);
                            mContext.startActivity(mIntent);
                        }
                    }else if("9".equals(match_alert_type)) {
                        Intent mIntent = new Intent(mContext, DialogOkAlertActivity.class);
                        mIntent.putExtra(mContext.getString(R.string.alert_dialog_title), "매치 취소 완료");
                        mIntent.putExtra(mContext.getString(R.string.alert_dialog_contents_header), "");
                        mIntent.putExtra(mContext.getString(R.string.alert_dialog_contents), "매치 취소가 정상적으로 실행되었습니다.");
                        mIntent.putExtra(mContext.getString(R.string.alert_dialog_ok_text), "확인");
                        mContext.startActivity(mIntent);
                    }else if("11".equals(match_alert_type)) {
                        Intent mIntent = new Intent(mContext, DialogOkAlertActivity.class);
                        mIntent.putExtra(mContext.getString(R.string.alert_dialog_title), "매치등록 취소 완료");
                        mIntent.putExtra(mContext.getString(R.string.alert_dialog_contents_header), "");
                        mIntent.putExtra(mContext.getString(R.string.alert_dialog_contents), "매치 등록 취소가 정상적으로 실행되었습니다.");
                        mIntent.putExtra(mContext.getString(R.string.alert_dialog_ok_text), "확인");
                        mContext.startActivity(mIntent);
                    }

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(mContext.getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "searchMatchAlertInfo_Listener - " + e);
                e.printStackTrace();
            } finally {
                mApplicationTM.stopCustomProgressDialog();
            }
        }
    };
}
