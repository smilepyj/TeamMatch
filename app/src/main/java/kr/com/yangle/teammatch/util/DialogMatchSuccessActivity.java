package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class DialogMatchSuccessActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    LinearLayout ll_match_success_dialog_regist, ll_match_success_dialog_proc;
    TextView tv_match_success_sub_title, tv_match_success_sub_title_etc, tv_match_success_team_name, tv_match_success_team_lvl, tv_match_success_team_member, tv_match_success_manager, tv_match_success_phone_manager,
            tv_match_success_ground_name, tv_match_success_phone_ground, tv_match_success_time, tv_match_success_ground, tv_match_success_cost, tv_match_success_notice;
    ImageButton ib_match_success_call_manager, ib_match_success_call_ground;
    CheckBox cb_match_success_check;
    Button bt_match_success_agree;

    String phone_manager, phone_ground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_match_success);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        ll_match_success_dialog_regist = findViewById(R.id.ll_match_success_dialog_regist);
        ll_match_success_dialog_proc = findViewById(R.id.ll_match_success_dialog_proc);

        tv_match_success_sub_title = findViewById(R.id.tv_match_success_sub_title);
        tv_match_success_sub_title_etc = findViewById(R.id.tv_match_success_sub_title_etc);
        tv_match_success_team_name = findViewById(R.id.tv_match_success_team_name);
        tv_match_success_team_lvl = findViewById(R.id.tv_match_success_team_lvl);
        tv_match_success_team_member = findViewById(R.id.tv_match_success_team_member);
        tv_match_success_manager = findViewById(R.id.tv_match_success_manager);
        tv_match_success_phone_manager = findViewById(R.id.tv_match_success_phone_manager);
        tv_match_success_ground_name = findViewById(R.id.tv_match_success_ground_name);
        tv_match_success_phone_ground = findViewById(R.id.tv_match_success_phone_ground);
        tv_match_success_time = findViewById(R.id.tv_match_success_time);
        tv_match_success_ground = findViewById(R.id.tv_match_success_ground);
        tv_match_success_cost = findViewById(R.id.tv_match_success_cost);
        tv_match_success_notice = findViewById(R.id.tv_match_success_notice);

        ib_match_success_call_manager = findViewById(R.id.ib_match_success_call_manager);
        ib_match_success_call_ground = findViewById(R.id.ib_match_success_call_ground);
        cb_match_success_check = findViewById(R.id.cb_match_success_check);

        bt_match_success_agree = findViewById(R.id.bt_match_success_agree);

        ib_match_success_call_manager.setOnClickListener(mOnClickListener);
        ib_match_success_call_ground.setOnClickListener(mOnClickListener);
        bt_match_success_agree.setOnClickListener(mOnClickListener);

        setData();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent;

            switch (v.getId()) {
                case R.id.ib_match_success_call_manager :
                    if(phone_manager != null && !"".equals(phone_manager)) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(phone_manager)));
                        startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, getString(R.string.ground_detail_no_phone_num));
                    }
                    break;
                case R.id.ib_match_success_call_ground :
                    if(phone_ground != null && !"".equals(phone_ground)) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(phone_ground)));
                        startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, getString(R.string.ground_detail_no_phone_num));
                    }
                    break;
                case R.id.bt_match_success_agree :
                    if (!cb_match_success_check.isChecked()) {
                        mApplicationTM.makeToast(mContext, "구장이용료 관련 알림에 동의해 주세요.");
                        return;
                    }

                    finish();
                    break;
                default :
                    break;
            }
        }
    };

    /**
     * Dialog Data Set
     * Created by maloman72 on 2018-11-14
     * */
    private void setData() {
        String mType = getIntent().getStringExtra(getString(R.string.match_success_extra_type));

        if(getString(R.string.match_success_extra_proc).equals(mType)) {
            tv_match_success_sub_title.setText(getString(R.string.match_success_dialog_sub_title_proc));
            tv_match_success_sub_title_etc.setVisibility(View.VISIBLE);

            ll_match_success_dialog_regist.setVisibility(View.GONE);
            ll_match_success_dialog_proc.setVisibility(View.VISIBLE);

            tv_match_success_notice.setText(getString(R.string.match_success_dialog_contents_proc));
        }else if("HOST".equals(mType)) {

            String sub_title = getIntent().getStringExtra("SUB_TITLE");
            String sub_title_etc = getIntent().getStringExtra("SUB_TITLE_ETC");
            String ground_name = getIntent().getStringExtra("GROUND_NAME");
            String ground_tel = getIntent().getStringExtra("GROUND_TEL");
            String match_time = getIntent().getStringExtra("MATCH_TIME");
            String ground_cost = getIntent().getStringExtra("GROUND_COST");
            String notice = getIntent().getStringExtra("NOTICE");

            tv_match_success_sub_title.setText(sub_title);
            tv_match_success_sub_title_etc.setText(sub_title_etc);
            tv_match_success_sub_title_etc.setVisibility(View.VISIBLE);
            tv_match_success_ground_name.setText(ground_name);
            phone_ground = ground_tel;
            tv_match_success_phone_ground.setText(ground_tel);
            tv_match_success_time.setText(match_time);
            tv_match_success_ground.setText(ground_name);
            tv_match_success_cost.setText(ground_cost);
            tv_match_success_notice.setText(notice);

            ll_match_success_dialog_regist.setVisibility(View.GONE);
            ll_match_success_dialog_proc.setVisibility(View.VISIBLE);

            tv_match_success_notice.setText(getString(R.string.match_success_dialog_contents_proc));
        }else if("GUEST".equals(mType)) {

            String sub_title = getIntent().getStringExtra("SUB_TITLE");
            String team_name = getIntent().getStringExtra("TEAM_NAME");
            String team_lvl = getIntent().getStringExtra("TEAM_LVL");
            String team_point = getIntent().getStringExtra("TEAM_POINT");
            String team_user_name = getIntent().getStringExtra("TEAM_USER_NAME");
            String team_user_tel = getIntent().getStringExtra("TEAM_USER_TEL");
            String ground_name = getIntent().getStringExtra("GROUND_NAME");
            String match_time = getIntent().getStringExtra("MATCH_TIME");
            String ground_cost = getIntent().getStringExtra("GROUND_COST");
            String notice = getIntent().getStringExtra("NOTICE");

            tv_match_success_sub_title.setText(sub_title);
            tv_match_success_sub_title_etc.setVisibility(View.GONE);
            tv_match_success_team_name.setText(team_name);
            tv_match_success_team_lvl.setText(team_lvl);
            tv_match_success_team_member.setText(team_point);
            tv_match_success_manager.setText(team_user_name);
            phone_manager = team_user_tel;
            tv_match_success_phone_manager.setText(team_user_tel);
            tv_match_success_time.setText(match_time);
            tv_match_success_ground.setText(ground_name);
            tv_match_success_cost.setText(ground_cost);
            tv_match_success_notice.setText(notice);

            ll_match_success_dialog_regist.setVisibility(View.VISIBLE);
            ll_match_success_dialog_proc.setVisibility(View.GONE);

            tv_match_success_notice.setText(getString(R.string.match_success_dialog_contents_proc));
        }
    }
}
