package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class DialogMatchApplyActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    TextView tv_match_apply_title, tv_match_apply_contents, tv_match_apply_team_name, tv_match_apply_team_lvl, tv_match_apply_team_member, tv_match_apply_match_time, tv_match_apply_match_ground;
    Button bt_match_apply_no, bt_match_apply_yes;

    String match_id, match_apply_id, opponent_team_id, accept_reject_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_match_apply);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        tv_match_apply_title = findViewById(R.id.tv_match_apply_title);
        tv_match_apply_contents = findViewById(R.id.tv_match_apply_contents);
        tv_match_apply_team_name = findViewById(R.id.tv_match_apply_team_name);
        tv_match_apply_team_lvl = findViewById(R.id.tv_match_apply_team_lvl);
        tv_match_apply_team_member = findViewById(R.id.tv_match_apply_team_member);
        tv_match_apply_match_time = findViewById(R.id.tv_match_apply_match_time);
        tv_match_apply_match_ground = findViewById(R.id.tv_match_apply_match_ground);

        bt_match_apply_no = findViewById(R.id.bt_match_apply_no);
        bt_match_apply_yes = findViewById(R.id.bt_match_apply_yes);

        bt_match_apply_no.setOnClickListener(mOnClickListener);
        bt_match_apply_yes.setOnClickListener(mOnClickListener);

        tv_match_apply_title.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_title)));
        tv_match_apply_contents.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_contents)));
        match_id = getIntent().getStringExtra(getString(R.string.match_apply_extra_match_id));
        match_apply_id = getIntent().getStringExtra(getString(R.string.match_apply_extra_match_apply_id));
        opponent_team_id = getIntent().getStringExtra(getString(R.string.match_apply_extra_team_id));
        tv_match_apply_team_name.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_team_name)));
        tv_match_apply_team_lvl.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_team_lvl)));
        tv_match_apply_team_member.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_team_point)) + "점");
        tv_match_apply_match_time.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_match_time)));
        tv_match_apply_match_ground.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_match_ground)));
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_match_apply_no :
                    accept_reject_type = "R";
                    mService.acceptMatch(acceptMatch_Listener, match_id, match_apply_id, accept_reject_type);
                    break;
                case R.id.bt_match_apply_yes :
                    accept_reject_type = "A";
                    mService.acceptMatch(acceptMatch_Listener, match_id, match_apply_id, accept_reject_type);
                    break;
                default :
                    break;
            }
        }
    };

    ResponseListener acceptMatch_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(mContext.getString(R.string.result_code)))) {

                    if("A".equals(accept_reject_type)) {

                    }else if("R".equals(accept_reject_type)) {
                        mApplicationTM.makeToast(mContext, "매치 거절을 완료했습니다.");
                    }

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(mContext.getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "searchMatchList_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
                finish();
            }
        }
    };
}
