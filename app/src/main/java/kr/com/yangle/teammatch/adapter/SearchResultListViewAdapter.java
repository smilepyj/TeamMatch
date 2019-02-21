package kr.com.yangle.teammatch.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;
import kr.com.yangle.teammatch.dialog.DialogAlertActivity;

public class SearchResultListViewAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    private LayoutInflater mLayoutInflater = null;
    private JSONArray mDataJSONArray;
    private int mDataJSONArrayCnt;

    private Activity mActivity;
    private Context mContext;
    private ApplicationTM mApplicationTM;

    private Service mService;

    public SearchResultListViewAdapter(Activity activity) {
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

            convertView = mLayoutInflater.inflate(R.layout.listview_search_result_match, parent, false);
        }

        TextView tv_listview_search_result_area = convertView.findViewById(R.id.tv_listview_search_result_area);
        TextView tv_listview_search_result_ground = convertView.findViewById(R.id.tv_listview_search_result_ground);
        ImageButton ib_listview_search_result_ground = convertView.findViewById(R.id.ib_listview_search_result_ground);
        TextView tv_listview_search_result_day = convertView.findViewById(R.id.tv_listview_search_result_day);
        TextView tv_listview_search_result_time = convertView.findViewById(R.id.tv_listview_search_result_time);
        TextView tv_listview_search_result_team_name = convertView.findViewById(R.id.tv_listview_search_result_team_name);
        TextView tv_listview_search_result_team_level = convertView.findViewById(R.id.tv_listview_search_result_team_level);
        TextView tv_listview_search_result_team_member = convertView.findViewById(R.id.tv_listview_search_result_team_member);
        ImageView iv_listview_search_result_pre_payment = convertView.findViewById(R.id.iv_listview_search_result_pre_payment);
        TextView tv_listview_search_result_pre_payment = convertView.findViewById(R.id.tv_listview_search_result_pre_payment);

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            final String match_hope_ground_id = mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_id)).toString();

            tv_listview_search_result_area.setText(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_sido_name)).toString() + mContext.getString(R.string.search_result_list_hyphen) + mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_gugun_name)).toString());
            tv_listview_search_result_ground.setText(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_ground_name)).toString());
            Date mDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_date)).toString());
            String mHopeDate = new SimpleDateFormat(mContext.getString(R.string.search_result_date_format_view), Locale.getDefault()).format(mDate);
            tv_listview_search_result_day.setText(mHopeDate);
            Date mTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_start_time)).toString());
//            String mHopeTime = new SimpleDateFormat(mContext.getString(R.string.search_result_time_format_view), Locale.getDefault()).format(mTime);
            String mHopeTime = mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_start_time)).toString().substring(0, 2);
            tv_listview_search_result_time.setText(mHopeTime + "시");
            tv_listview_search_result_team_name.setText(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_host_name)).toString());
            tv_listview_search_result_team_level.setText(mApplicationTM.getC002().get(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_team_lvl)).toString()));
            tv_listview_search_result_team_member.setText(mApplicationTM.getC003().get(mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_hope_team_member)).toString()));
            String pre_payment_at = mJSONObject.get(mContext.getString(R.string.matchProclist_result_pre_payment_at))==null?"":mJSONObject.get(mContext.getString(R.string.matchProclist_result_pre_payment_at)).toString();
            if("Y".equals(pre_payment_at)) {
                iv_listview_search_result_pre_payment.setVisibility(View.VISIBLE);
                tv_listview_search_result_pre_payment.setVisibility(View.VISIBLE);
            }else {
                iv_listview_search_result_pre_payment.setVisibility(View.GONE);
                tv_listview_search_result_pre_payment.setVisibility(View.GONE);
            }

            final String match_id = mJSONObject.get(mContext.getString(R.string.searchmatchlist_result_match_id)).toString();

            LinearLayout ll_listview_search_result_request = convertView.findViewById(R.id.ll_listview_search_result_request);
            ll_listview_search_result_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

                    builder.setTitle("매치 신청 확인")
                            .setMessage("매치 신청을 하시겠습니까?")
                            .setPositiveButton("매치 신청", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mService.applyMatch(applyMatch_Listener, match_id);
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            tv_listview_search_result_ground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, GroundDetailActivity.class);
                    mIntent.putExtra(mContext.getString(R.string.ground_detail_ground_id), match_hope_ground_id);
                    mContext.startActivity(mIntent);
                }
            });

            ib_listview_search_result_ground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, GroundDetailActivity.class);
                    mIntent.putExtra(mContext.getString(R.string.ground_detail_ground_id), match_hope_ground_id);
                    mContext.startActivity(mIntent);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }

        return convertView;
    }

    ResponseListener applyMatch_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(mContext.getString(R.string.result_code)))) {

                    // 닫기, 등록정보 확인
                    Intent mIntent = new Intent(mContext, DialogAlertActivity.class);
                    mIntent.putExtra(mContext.getString(R.string.alert_dialog_title), "매치 신청 완료");
                    mIntent.putExtra(mContext.getString(R.string.alert_dialog_contents_header), "");
                    mIntent.putExtra(mContext.getString(R.string.alert_dialog_contents), "매치 신청이 완료되었습니다.\n상대편의 승낙여부가 나올때까지 기다려주세요.");
                    mIntent.putExtra(mContext.getString(R.string.alert_dialog_cancel_text), "닫기");
                    mIntent.putExtra(mContext.getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                    mIntent.putExtra(mContext.getString(R.string.alert_dialog_type), 2);

                    mContext.startActivity(mIntent);

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(mContext.getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "searchMatchList_Listener - " + e);
            } finally {
                mApplicationTM.stopCustomProgressDialog();
            }
        }
    };
}
