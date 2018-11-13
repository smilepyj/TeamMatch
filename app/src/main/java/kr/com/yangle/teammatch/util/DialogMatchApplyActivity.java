package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class DialogMatchApplyActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    TextView tv_match_apply_title, tv_match_apply_contents, tv_match_apply_team_name, tv_match_apply_team_lvl, tv_match_apply_team_member, tv_match_apply_match_time, tv_match_apply_match_ground;
    Button bt_match_apply_no, bt_match_apply_yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_match_apply);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        tv_match_apply_title = findViewById(R.id.tv_alert_dialog_title);
        tv_match_apply_contents = findViewById(R.id.tv_alert_dialog_contents);
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
        tv_match_apply_team_name.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_team_name)));
        tv_match_apply_team_lvl.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_team_lvl)));
        tv_match_apply_team_member.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_team_member)));
        tv_match_apply_match_time.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_match_time)));
        tv_match_apply_match_ground.setText(getIntent().getStringExtra(getString(R.string.match_apply_extra_match_ground)));
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_match_apply_no :
                    break;
                case R.id.bt_match_apply_yes :
                    break;
                default :
                    break;
            }
        }
    };
}
