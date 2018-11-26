package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelfCheckActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    Toolbar toolbar;

    LinearLayout ll_self_check_question, ll_self_check_01, ll_self_check_02, ll_self_check_03, ll_self_check_04, ll_self_check_05, ll_self_check_06, ll_self_check_before, ll_self_check_after;
    RelativeLayout rl_self_check_result;
    Button bt_self_check_01_1, bt_self_check_01_2, bt_self_check_01_3, bt_self_check_01_4, bt_self_check_01_5, bt_self_check_02_1, bt_self_check_02_2, bt_self_check_02_3, bt_self_check_02_4, bt_self_check_02_5,
            bt_self_check_03_1, bt_self_check_03_2, bt_self_check_03_3, bt_self_check_03_4, bt_self_check_04_1, bt_self_check_04_2, bt_self_check_04_3, bt_self_check_04_4, bt_self_check_05_1, bt_self_check_05_2,
            bt_self_check_05_3, bt_self_check_05_4, bt_self_check_06_1, bt_self_check_06_2, bt_self_check_06_3, bt_self_check_06_4, bt_self_check_06_5, bt_self_check_recheck, bt_self_check_ok;
    TextView tv_self_check_before, tv_self_check_after, tv_self_check_result;

    Integer[] mScore;
    int mPage = 1;
    String mTeamLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_check);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);

        mScore = new Integer[6];

        ll_self_check_question = findViewById(R.id.ll_self_check_question);
        rl_self_check_result = findViewById(R.id.rl_self_check_result);

        ll_self_check_01 = findViewById(R.id.ll_self_check_01);
        ll_self_check_02 = findViewById(R.id.ll_self_check_02);
        ll_self_check_03 = findViewById(R.id.ll_self_check_03);
        ll_self_check_04 = findViewById(R.id.ll_self_check_04);
        ll_self_check_05 = findViewById(R.id.ll_self_check_05);
        ll_self_check_06 = findViewById(R.id.ll_self_check_06);
        ll_self_check_before = findViewById(R.id.ll_self_check_before);
        ll_self_check_after = findViewById(R.id.ll_self_check_after);
        bt_self_check_01_1 = findViewById(R.id.bt_self_check_01_1);
        bt_self_check_01_2 = findViewById(R.id.bt_self_check_01_2);
        bt_self_check_01_3 = findViewById(R.id.bt_self_check_01_3);
        bt_self_check_01_4 = findViewById(R.id.bt_self_check_01_4);
        bt_self_check_01_5 = findViewById(R.id.bt_self_check_01_5);
        bt_self_check_02_1 = findViewById(R.id.bt_self_check_02_1);
        bt_self_check_02_2 = findViewById(R.id.bt_self_check_02_2);
        bt_self_check_02_3 = findViewById(R.id.bt_self_check_02_3);
        bt_self_check_02_4 = findViewById(R.id.bt_self_check_02_4);
        bt_self_check_02_5 = findViewById(R.id.bt_self_check_02_5);
        bt_self_check_03_1 = findViewById(R.id.bt_self_check_03_1);
        bt_self_check_03_2 = findViewById(R.id.bt_self_check_03_2);
        bt_self_check_03_3 = findViewById(R.id.bt_self_check_03_3);
        bt_self_check_03_4 = findViewById(R.id.bt_self_check_03_4);
        bt_self_check_04_1 = findViewById(R.id.bt_self_check_04_1);
        bt_self_check_04_2 = findViewById(R.id.bt_self_check_04_2);
        bt_self_check_04_3 = findViewById(R.id.bt_self_check_04_3);
        bt_self_check_04_4 = findViewById(R.id.bt_self_check_04_4);
        bt_self_check_05_1 = findViewById(R.id.bt_self_check_05_1);
        bt_self_check_05_2 = findViewById(R.id.bt_self_check_05_2);
        bt_self_check_05_3 = findViewById(R.id.bt_self_check_05_3);
        bt_self_check_05_4 = findViewById(R.id.bt_self_check_05_4);
        bt_self_check_06_1 = findViewById(R.id.bt_self_check_06_1);
        bt_self_check_06_2 = findViewById(R.id.bt_self_check_06_2);
        bt_self_check_06_3 = findViewById(R.id.bt_self_check_06_3);
        bt_self_check_06_4 = findViewById(R.id.bt_self_check_06_4);
        bt_self_check_06_5 = findViewById(R.id.bt_self_check_06_5);
        tv_self_check_before = findViewById(R.id.tv_self_check_before);
        tv_self_check_after = findViewById(R.id.tv_self_check_after);

        tv_self_check_result = findViewById(R.id.tv_self_check_result);
        bt_self_check_recheck = findViewById(R.id.bt_self_check_recheck);
        bt_self_check_ok = findViewById(R.id.bt_self_check_ok);

        bt_self_check_01_1.setOnClickListener(mOnClickListener);
        bt_self_check_01_2.setOnClickListener(mOnClickListener);
        bt_self_check_01_3.setOnClickListener(mOnClickListener);
        bt_self_check_01_4.setOnClickListener(mOnClickListener);
        bt_self_check_01_5.setOnClickListener(mOnClickListener);
        bt_self_check_02_1.setOnClickListener(mOnClickListener);
        bt_self_check_02_2.setOnClickListener(mOnClickListener);
        bt_self_check_02_3.setOnClickListener(mOnClickListener);
        bt_self_check_02_4.setOnClickListener(mOnClickListener);
        bt_self_check_02_5.setOnClickListener(mOnClickListener);
        bt_self_check_03_1.setOnClickListener(mOnClickListener);
        bt_self_check_03_2.setOnClickListener(mOnClickListener);
        bt_self_check_03_3.setOnClickListener(mOnClickListener);
        bt_self_check_03_4.setOnClickListener(mOnClickListener);
        bt_self_check_04_1.setOnClickListener(mOnClickListener);
        bt_self_check_04_2.setOnClickListener(mOnClickListener);
        bt_self_check_04_3.setOnClickListener(mOnClickListener);
        bt_self_check_04_4.setOnClickListener(mOnClickListener);
        bt_self_check_05_1.setOnClickListener(mOnClickListener);
        bt_self_check_05_2.setOnClickListener(mOnClickListener);
        bt_self_check_05_3.setOnClickListener(mOnClickListener);
        bt_self_check_05_4.setOnClickListener(mOnClickListener);
        bt_self_check_06_1.setOnClickListener(mOnClickListener);
        bt_self_check_06_2.setOnClickListener(mOnClickListener);
        bt_self_check_06_3.setOnClickListener(mOnClickListener);
        bt_self_check_06_4.setOnClickListener(mOnClickListener);
        bt_self_check_06_5.setOnClickListener(mOnClickListener);
        ll_self_check_before.setOnClickListener(mOnClickListener);
        ll_self_check_after.setOnClickListener(mOnClickListener);
        tv_self_check_before.setOnClickListener(mOnClickListener);
        tv_self_check_after.setOnClickListener(mOnClickListener);

        bt_self_check_recheck.setOnClickListener(mOnClickListener);
        bt_self_check_ok.setOnClickListener(mOnClickListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_self_check_01_1 :
                    SelfCheck01(1);
                    break;
                case R.id.bt_self_check_01_2 :
                    SelfCheck01(2);
                    break;
                case R.id.bt_self_check_01_3 :
                    SelfCheck01(3);
                    break;
                case R.id.bt_self_check_01_4 :
                    SelfCheck01(4);
                    break;
                case R.id.bt_self_check_01_5 :
                    SelfCheck01(5);
                    break;
                case R.id.bt_self_check_02_1 :
                    SelfCheck02(1);
                    break;
                case R.id.bt_self_check_02_2 :
                    SelfCheck02(2);
                    break;
                case R.id.bt_self_check_02_3 :
                    SelfCheck02(3);
                    break;
                case R.id.bt_self_check_02_4 :
                    SelfCheck02(4);
                    break;
                case R.id.bt_self_check_02_5 :
                    SelfCheck02(5);
                    break;
                case R.id.bt_self_check_03_1 :
                    SelfCheck03(1);
                    break;
                case R.id.bt_self_check_03_2 :
                    SelfCheck03(2);
                    break;
                case R.id.bt_self_check_03_3 :
                    SelfCheck03(3);
                    break;
                case R.id.bt_self_check_03_4 :
                    SelfCheck03(4);
                    break;
                case R.id.bt_self_check_04_1 :
                    SelfCheck04(1);
                    break;
                case R.id.bt_self_check_04_2 :
                    SelfCheck04(2);
                    break;
                case R.id.bt_self_check_04_3 :
                    SelfCheck04(3);
                    break;
                case R.id.bt_self_check_04_4 :
                    SelfCheck04(4);
                    break;
                case R.id.bt_self_check_05_1 :
                    SelfCheck05(1);
                    break;
                case R.id.bt_self_check_05_2 :
                    SelfCheck05(2);
                    break;
                case R.id.bt_self_check_05_3 :
                    SelfCheck05(3);
                    break;
                case R.id.bt_self_check_05_4 :
                    SelfCheck05(4);
                    break;
                case R.id.bt_self_check_06_1 :
                    SelfCheck06(1);
                    break;
                case R.id.bt_self_check_06_2 :
                    SelfCheck06(2);
                    break;
                case R.id.bt_self_check_06_3 :
                    SelfCheck06(3);
                    break;
                case R.id.bt_self_check_06_4 :
                    SelfCheck06(4);
                    break;
                case R.id.bt_self_check_06_5 :
                    SelfCheck06(5);
                    break;
                case R.id.ll_self_check_after :
                case R.id.tv_self_check_after :
                    SetPage(false);
                    break;
                case R.id.ll_self_check_before :
                case R.id.tv_self_check_before :
                    SetPage(true);
                    break;
                case R.id.bt_self_check_recheck :
                    Reset();
                    break;
                case R.id.bt_self_check_ok :
                    Intent mIntent = new Intent();
                    mIntent.putExtra(getString(R.string.self_check_level), mTeamLevel);
                    setResult(RESULT_OK, mIntent);
                    finish();
                    break;
                default :
                    break;
            }
        }
    };

    /**
     * 문항별 버튼 초기화 및 선택
     * Created by maloman72 on 2018-11-17
     * */
    private void SelfCheck01(int check) {
        bt_self_check_01_1.setSelected(false);
        bt_self_check_01_2.setSelected(false);
        bt_self_check_01_3.setSelected(false);
        bt_self_check_01_4.setSelected(false);
        bt_self_check_01_5.setSelected(false);

        switch (check) {
            case 1 :
                bt_self_check_01_1.setSelected(true);
                mScore[0] = 10;
                break;
            case 2 :
                bt_self_check_01_2.setSelected(true);
                mScore[0] = 20;
                break;
            case 3 :
                bt_self_check_01_3.setSelected(true);
                mScore[0] = 13;
                break;
            case 4 :
                bt_self_check_01_4.setSelected(true);
                mScore[0] = 12;
                break;
            case 5 :
                bt_self_check_01_5.setSelected(true);
                mScore[0] = 11;
                break;
            default :
                break;
        }
    }

    private void SelfCheck02(int check) {
        bt_self_check_02_1.setSelected(false);
        bt_self_check_02_2.setSelected(false);
        bt_self_check_02_3.setSelected(false);
        bt_self_check_02_4.setSelected(false);
        bt_self_check_02_5.setSelected(false);

        switch (check) {
            case 1 :
                bt_self_check_02_1.setSelected(true);
                mScore[1] = 7;
                break;
            case 2 :
                bt_self_check_02_2.setSelected(true);
                mScore[1] = 10;
                break;
            case 3 :
                bt_self_check_02_3.setSelected(true);
                mScore[1] = 12;
                break;
            case 4 :
                bt_self_check_02_4.setSelected(true);
                mScore[1] = 14;
                break;
            case 5 :
                bt_self_check_02_5.setSelected(true);
                mScore[1] = 15;
                break;
            default :
                break;
        }
    }

    private void SelfCheck03(int check) {
        bt_self_check_03_1.setSelected(false);
        bt_self_check_03_2.setSelected(false);
        bt_self_check_03_3.setSelected(false);
        bt_self_check_03_4.setSelected(false);

        switch (check) {
            case 1 :
                bt_self_check_03_1.setSelected(true);
                mScore[2] = 0;
                break;
            case 2 :
                bt_self_check_03_2.setSelected(true);
                mScore[2] = 15;
                break;
            case 3 :
                bt_self_check_03_3.setSelected(true);
                mScore[2] = 20;
                break;
            case 4 :
                bt_self_check_03_4.setSelected(true);
                mScore[2] = 25;
                break;
            default :
                break;
        }
    }

    private void SelfCheck04(int check) {
        bt_self_check_04_1.setSelected(false);
        bt_self_check_04_2.setSelected(false);
        bt_self_check_04_3.setSelected(false);
        bt_self_check_04_4.setSelected(false);

        switch (check) {
            case 1 :
                bt_self_check_04_1.setSelected(true);
                mScore[3] = 4;
                break;
            case 2 :
                bt_self_check_04_2.setSelected(true);
                mScore[3] = 6;
                break;
            case 3 :
                bt_self_check_04_3.setSelected(true);
                mScore[3] = 8;
                break;
            case 4 :
                bt_self_check_04_4.setSelected(true);
                mScore[3] = 10;
                break;
            default :
                break;
        }
    }

    private void SelfCheck05(int check) {
        bt_self_check_05_1.setSelected(false);
        bt_self_check_05_2.setSelected(false);
        bt_self_check_05_3.setSelected(false);
        bt_self_check_05_4.setSelected(false);

        switch (check) {
            case 1 :
                bt_self_check_05_1.setSelected(true);
                mScore[4] = 10;
                break;
            case 2 :
                bt_self_check_05_2.setSelected(true);
                mScore[4] = 8;
                break;
            case 3 :
                bt_self_check_05_3.setSelected(true);
                mScore[4] = 5;
                break;
            case 4 :
                bt_self_check_05_4.setSelected(true);
                mScore[4] = 4;
                break;
            default :
                break;
        }
    }

    private void SelfCheck06(int check) {
        bt_self_check_06_1.setSelected(false);
        bt_self_check_06_2.setSelected(false);
        bt_self_check_06_3.setSelected(false);
        bt_self_check_06_4.setSelected(false);
        bt_self_check_06_5.setSelected(false);

        switch (check) {
            case 1 :
                bt_self_check_06_1.setSelected(true);
                mScore[5] = 8;
                break;
            case 2 :
                bt_self_check_06_2.setSelected(true);
                mScore[5] = 10;
                break;
            case 3 :
                bt_self_check_06_3.setSelected(true);
                mScore[5] = 14;
                break;
            case 4 :
                bt_self_check_06_4.setSelected(true);
                mScore[5] = 18;
                break;
            case 5 :
                bt_self_check_06_5.setSelected(true);
                mScore[5] = 20;
                break;
            default :
                break;
        }
    }

    /**
     * 자가진단 항목 출력
     * Created by maloman72
     * */
    private void SetPage(boolean mCase) {
        if((mPage == 1 && mScore[0] == null) || (mPage == 2 && mScore[1] == null) || (mPage == 3 && mScore[2] == null) || (mPage == 4 && mScore[3] == null) || (mPage == 5 && mScore[4] == null) || (mPage == 6 && mScore[5] == null)) {
            mApplicationTM.makeToast(mContext, getString(R.string.self_check_blank));
            return;
        }

        if(mCase) {
            if(mPage >= 2) {
                mPage = mPage - 1;
            }
        } else {
            if(mPage <= 6) {
                mPage = mPage + 1;
            }
        }


        switch (mPage) {
            case 1 :
                ll_self_check_02.setVisibility(View.GONE);
                ll_self_check_01.setVisibility(View.VISIBLE);
                break;
            case 2 :
                ll_self_check_01.setVisibility(View.GONE);
                ll_self_check_03.setVisibility(View.GONE);
                ll_self_check_02.setVisibility(View.VISIBLE);
                break;
            case 3 :
                ll_self_check_02.setVisibility(View.GONE);
                ll_self_check_04.setVisibility(View.GONE);
                ll_self_check_03.setVisibility(View.VISIBLE);
                break;
            case 4 :
                ll_self_check_03.setVisibility(View.GONE);
                ll_self_check_05.setVisibility(View.GONE);
                ll_self_check_04.setVisibility(View.VISIBLE);
                break;
            case 5 :
                ll_self_check_04.setVisibility(View.GONE);
                ll_self_check_06.setVisibility(View.GONE);
                ll_self_check_05.setVisibility(View.VISIBLE);

                tv_self_check_after.setText(getString(R.string.self_check_after));
                break;
            case 6 :
                ll_self_check_05.setVisibility(View.GONE);
                ll_self_check_06.setVisibility(View.VISIBLE);

                tv_self_check_after.setText(getString(R.string.self_check_diagnosis));
                break;
            case 7 :
                CheckScore();
                break;
        }
    }

    /**
     * 레벨 진단
     * Created by maloman72 on 2018-11-17
     * */
    private void CheckScore() {
        String[] C002_code = getResources().getStringArray(R.array.C002_code);
        int resultScore = 0;

        for(int i = 0; i < mScore.length; i++) {
            resultScore = resultScore + mScore[i];
        }

        if(resultScore >= 90) {
            mTeamLevel = C002_code[0];
        } else if(resultScore >= 80) {
            mTeamLevel = C002_code[1];
        } else if(resultScore >= 70) {
            mTeamLevel = C002_code[2];
        } else if(resultScore >= 60) {
            mTeamLevel = C002_code[3];
        } else {
            mTeamLevel = C002_code[4];
        }

        ll_self_check_question.setVisibility(View.GONE);
        rl_self_check_result.setVisibility(View.VISIBLE);
        tv_self_check_result.setText(mApplicationTM.getC002().get(mTeamLevel));
    }

    /**
     * reset - 다시점검
     * Created by maloman72
     * */
    private void Reset() {
        this.recreate();
    }
}
