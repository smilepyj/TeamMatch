package kr.com.yangle.teammatch.util;

import android.content.Context;
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
            switch (v.getId()) {
                case R.id.ib_match_success_call_manager :
                    break;
                case R.id.ib_match_success_call_ground :
                    break;
                case R.id.bt_match_success_agree :
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
        }
    }
}
