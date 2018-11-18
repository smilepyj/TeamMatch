package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class DialogPrivacyActivity extends AppCompatActivity {
//    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    LinearLayout ll_privacy_close;
    TextView tv_privacy_title, tv_privacy_contents;
    ImageButton ib_privacy_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_privacy);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        ll_privacy_close = findViewById(R.id.ll_privacy_close);
        tv_privacy_title = findViewById(R.id.tv_privacy_title);
        tv_privacy_contents = findViewById(R.id.tv_privacy_contents);
        ib_privacy_close = findViewById(R.id.ib_privacy_close);
        ib_privacy_close.setOnClickListener(mOnClickListener);

        LayoutSet();

        tv_privacy_title.setText("개인정보 취급방침");

        String contents = "개인정보 수집 및 이용에 대한 안내(필수)\n" +
                "\n" +
                "① 수집 및 이용 목적 : 회원관리, 맞춤형 서비스 제공, 신원확인, 유저 간 커뮤니케이션 서비스 제공, 체육시설 대관 및 관리를 위한 이용자 확인, 결제, 마케팅 및 광고 등\n" +
                "\n" +
                "② 수집 항목 : 카카오톡 ID, 비밀번호, 실명, 이메일, 연락처, 성별, 생년월일, 주소, 서비스 이용기록, 방문일시, 불량이용기록, 접속 로그, 쿠키, 접속 IP 정보, 키, 몸무게, 포지션 등\n" +
                "\n" +
                "③ 수집 및 이용 기간 : 회원 가입 시점부터 회원 탈퇴를 요청 시, 개인정보의 수집 및 이용에 대한 동의 철회 시, 수집 및 이용목적이 달성되거나 보유 및 이용기간 종료 시 까지\n" +
                "\n" +
                "개인정보 취급방침 및 개인정보 제3자 제공 동의서\n" +
                "\n" +
                "풋살매치는 (이하 ‘회사’) 고객님의 개인정보를 중요시하며 「정보통신망 이용촉진 및 정보보호에 관한 법률」을 준수하고 있습니다. 회사는 개인정보취급방침을 통하여 고객님께서 제공하시는 개인정보가 어떠한 용도와 방식으로 이용되고 있으며, 개인정보보호를 위해 어떠한 조치가 취해지고 있는지 알려드립니다.\n" +
                "\n" +
                "1. 본 약관에서 사용하는 용어들의 정의는 ‘이용약관 ? 풋살매치’ 제1조 및 전문을 참고하시길 바랍니다.\n" +
                "\n" +
                "2. 본 약관은 회원가입 시 개인정보 취급방침, 개인정보 제3자 제공 동의에 동의 버튼을 클릭하여 서비스 회원가입을 신청한 후 이를 회사가 승낙하면 즉각 효력이 발생합니다. 회사가 개인정보 취급 방침을 개정할 경우에는 기존약관과 개정약관 및 개정약관의 적용일자와 개정사유를 명시하여 현행 약관과 함께 그 적용일자 30일 전부터 적용일 이후 상당한 기간 동안 공지해야 합니다. 개정 내용이 회원에게 불리한 경우, 그 적용일자 30일 전부터 적용일 이후 상당한 기간 동안 각각 이를 서비스 홈페이지에 게시하거나 회원에게 대면 전달, 전화, 팩스, 전자우편, 전자쪽지, 모바일 메신저, 알림창, 서비스 화면에 게시 및 기타 유무선 통신 등 다양한 수단을 이용하여 약관 개정 사실을 발송하여 고지합니다. 회사가 전항에 따라 개정약관을 공지 또는 통지하면서 회원에게 15일 기간 내에 의사표시를 하지 않으면 의사표시가 표명된 것으로 본다는 뜻을 명확하게 공지 또는 통지 하였음에도 회원이거부의 의사표시를 하지 아니한 경우 회원이 개정약관에 동의한 것으로 봅니다. 본 개인정보 취급 방침에 동의하지 않는 경우 회원 가입 및 주요 서비스 이용이 제한될 수 있습니다.\n" +
                "\n" +
                "3. 회사는 회원가입, 상담, 서비스 이용, 결제, 신원확인 등등을 위해 아래와 같은 개인정보를 수집하고 있습니다.\n" +
                "- 수집항목 : ID, 비밀번호, 실명, 이메일, 연락처, 성별, 생년월일, 주소, 서비스 이용기록, 방문일시, 불량이용기록, 접속 로그, 쿠키, 접속 IP 정보, 키, 포지션, 사용 발, 사용 손, 닉네임\n" +
                "- 개인정보 수집방법 : 홈페이지 및 모바일 (회원가입, 공개 게시판, 경품 응모, 이벤트 응모, 배송요청 등), 방문, 전화/팩스를 통한 회원가입, 이벤트 응모, 배송 요청, 제휴사로부터의 제공, 생성정보 수집 툴을 통한 수집, 메일 등\n" +
                "- 다음과 같은 결제정보들이 수집될 수 있습니다.\n" +
                "신용카드 결제 시 : 카드사명, 카드번호 등\n" +
                "휴대전화 결제 시 : 이동전화번호, 통신사, 결제승인번호 등\n" +
                "계좌이체 시 : 은행명, 계좌번호 등\n" +
                "상품권 이용 시 : 상품권 번호 등\n" +
                "\n" +
                "4. 회사는 수집한 개인정보를 다음의 목적을 위해 활용합니다.\n" +
                "- 서비스 제공에 관한 계약 이행 및 요금정산, 유저 간 커뮤니케이션 서비스 제공, 체육시설 대관 및 관리를 위한 이용자 확인, 콘텐츠 제공, 맞춤형 서비스 제공, 구매 및 요금 결제, 물품배송 또는 청구지 등 발송, 금융거래 본인 인증 및 금융 서비스, 회원가입 시 본인인증\n" +
                "- 회원 관리 회원제 서비스 이용에 따른 본인확인, 개인 식별, 불량회원의 부정 이용 방지와 비인가 사용 방지, 서비스의 원활한 운영에 지장을 미치는 행위 및 부정이용 행위 제재, 가입 의사 확인, 가입횟수 제한, 연령확인, 분쟁조정을 위한 기록보존, 불만처리 등 민원처리, 고지사항 전달, 회원탈퇴 의사 확인\n" +
                "- 마케팅 및 광고에 활용, 신규 서비스(제품) 개발 및 특화, 이벤트 등 광고성 정보 전달, 인구통계학적 특성에 따른 서비스 제공 및 광고 게재, 접속 빈도 파악 또는 회원의 서비스 이용에 대한 통계\n" +
                "\n" +
                "5. 원칙적으로, 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다. 단, 관계법령의 규정에 의하여 보존할 필요가 있는 경우 회사는 아래와 같이 관계법령에서 정한 일정한 기간 동안 회원정보를 보관합니다.\n" +
                "- 보존 항목 : ID, 비밀번호 , 이메일 , 서비스 이용기록 , 접속 로그 , 쿠키 , 접속 IP 정보 , 결제기록, 실명, 연락처\n" +
                "- 보존 근거 : 「전자상거래등에서의 소비자보호에 관한 법률」, 「신용정보의 이용 및 보호에 관한 법률」\n" +
                "- 보존 기간 : 5년\n" +
                "- 표시/광고에 관한 기록 : 6개월 「전자상거래등에서의 소비자보호에 관한 법률」\n" +
                "- 계약 또는 청약철회 등에 관한 기록 : 5년 「전자상거래등에서의 소비자보호에 관한 법률」\n" +
                "- 대금결제 및 재화 등의 공급에 관한 기록 : 5년 「전자상거래등에서의 소비자보호에 관한 법」\n" +
                "- 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년 「전자상거래등에서의 소비자보호에 관한 법」\n" +
                "- 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년 「신용정보의 이용 및 보호에 관한 법」\n" +
                "- 전자금융 거래에 관한 기록 : 5년 「전자금융거래법」\n" +
                "- 부정이용기록(부정가입, 징계기록, 약관위반 등의 비정상적인 서비스 이용기록) : 99년 (부정 가입 및 이용방지)\n" +
                "\n" +
                "6. 파기절차 및 방법은 다음과 같습니다.\n" +
                "\n" +
                "- 파기절차 : 회원님이 회원가입 등을 위해 입력하신 정보는 목적이 달성된 후 별도의 DB로 옮겨져(종이의 경우 별도의 서류함) 내부 방침 및 기타 관련 법령에 의한 정보보호 사유에 따라(보유 및 이용기간 참조) 일정 기간 저장된 후 파기되어집니다. 별도 DB로 옮겨진 개인정보는 법령에 고시된 경우를 제외하고는 보유 목적 이외의 용도로 이용되지 않습니다.\n" +
                "\n" +
                "- 파기방법 : 전자적 파일형태로 저장된 개인정보는 기록을 재생할 수 없는 기술적 방법을 사용하여 삭제합니다. 종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.\n" +
                "\n" +
                "7. 회사는 회원에 대하여 보다 더 질 높은 서비스 제공 등을 위해 제 3자에게 귀하의 개인정보를 제공할 수 있습니다. 회원은 이를 거부 할 권리가 있습니다. 개인정보를 제공, 공유하는 경우에는 사전 동의절차를 거치게 되며, 정보를 제공받는 자, 정보를 제공받는 자의 개인정보 이용목적, 제공하는 개인정보의 항목, 개인정보를 제공받는 자의 개인정보 보유 및 이용기간, 정보제공 동의를 거부할 권리가 있다는 사실 및 동의 거부에 따른 불이익 등을 명시하여 회원에게 동의를 구해야합니다.\n" +
                "\n" +
                "- 정보를 제공받는 자 : 결제대행사, 본인확인 서비스 제공사, 이동 통신사, 제휴시설의 시설관리자\n" +
                "- 제공하는 개인정보 항목 : 본명, 연락처, 생년월일, ID, 이메일, 서비스 이용기록, 접속 로그, 결제기록 등\n" +
                "- 제공정보의 이용 목적 : 콘텐츠 제공, 구매 및 요금 결제, 물품배송 또는 청구지 등 발송, 금융거래 본인 인증 및 금융 서비스, 요금추심, 회원제 서비스 이용에 따른 본인확인서비스, 개인 식별, 불량회원의 부정 이용 방지와 비인가 사용 방지, 가입 의사 확인, 연령 및 성별 확인, 원활한 서비스 이용을 위한 개인 연락처 확인, 불만처리 등 민원처리, 고지사항 전달, 신규 서비스(제품) 개발 및 특화, 이벤트 등 광고성 정보 전달, 인구통계학적 특성에 따른 서비스 제공 및 광고 게재, 접속 빈도 파악 또는 회원의 서비스 이용에 대한 통계, 체육시설 대관 및 관리를 위한 이용자 확인\n" +
                "- 제공 정보의 보유 및 이용 기간 : 5년\n" +
                "- 제 3자 개인정보 제공 동의 거부 시 불이익 : 일부 서비스 이용 제한, 가입 제한\n" +
                "- 본인 확인 서비스 이용을 위한 본인확인정보가 본인확인 서비스 제공사 및 이동 통신사로 제공되며 본인확인을 위한 문자메시지가 발송됩니다.\n" +
                "\n" +
                "\n" +
                "8. 이용자는 언제든지 등록되어 있는 자신의 개인정보를 조회하거나 수정할 수 있으며 가입해지를 요청할 수도 있습니다.\n" +
                "- 이용자의 개인정보 조회·수정을 위해서는 ‘개인정보변경’(또는 ‘회원정보수정’ 등)을 가입해지(동의철회)를 위해서는 ‘회원탈퇴’를 클릭하여 본인 확인 절차를 거치신 후 직접 열람, 정정 또는 탈퇴가 가능합니다. 혹은 개인정보관리책임자에게 서면, 전화 또는 이메일로 연락하시면 지체 없이 조치하겠습니다. 회원이 개인정보의 오류에 대한 정정을 요청하신 경우 정정을 완료하기 전까지 당해 개인정보를 이용 또는 제공하지 않습니다. 또한 잘못된 개인정보를 제3자에게 이미 제공한 경우 정정 처리결과를 제3자에게 지체 없이 통지하여 정정이 이루어지도록 하겠습니다. 이용자의 요청에 의해 해지 또는 삭제된 개인정보는 회사가 수집하는 “개인정보의 보유 및 이용기간”에 명시된 바에 따라 처리하고 그 외의 용도로 열람 또는 이용할 수 없도록 처리하고 있습니다.\n" +
                "\n" +
                "9. 풋살매치는 회원들 사이의 소통을 통한 신뢰 형성을 위해 부분 실명제로 운영됩니다. 체육동호인들 간 최소한의 상호 존중의 표현을 위해 채택한 방식입니다.\n" +
                "\n" +
                "10. 회원은 풋살매치를 이용하여 자신의 개인정보(연락처, 본명, 팀 정보 등)를 타 회원에게 전달 할 수 있습니다. 전달된 개인정보는 목적 달성 이후 즉각 폐기되어야 합니다. 회사는 전달 된 개인정보에 대하여 어떠한 법적인 책임도지지 않습니다.\n" +
                "\n" +
                "11. 회사는 고객의 개인정보를 보호하고 개인정보와 관련한 불만을 처리하기 위하여 아래와 같이 관련 부서 및 개인정보관리책임자를 지정하고 있습니다.\n" +
                "- 전화번호 : 070-7806-4465\n" +
                "- 이메일 : teammatch77@gmail.com\n" +
                "- 개인정보관리책임자 : 김광민\n" +
                "\n" +
                "\n" +
                "12. 귀하께서는 회사의 서비스를 이용하시며 발생하는 모든 개인정보보호 관련 민원을 개인정보관리책임자 혹은 담당부서로 신고, 상담하실 수 있습니다. 회사는 이용자들의 신고사항에 대해 신속하게 충분한 답변을 드릴 것입니다. 기타 개인정보침해에 대한 신고나 상담이 필요하신 경우에는 아래 기관에 문의하시기 바랍니다.\n" +
                "- 개인정보침해신고센터 (www.1336.or.kr/국번없이 118)\n" +
                "- 정보보호마크인증위원회 (www.eprivacy.or.kr/02-580-0533~4)\n" +
                "- 대검찰청 인터넷범죄수사센터 (http://icic.sppo.go.kr/02-3480-3600)\n" +
                "- 경찰청 사이버테러대응센터 (www.ctrc.go.kr/02-392-0330)";

        tv_privacy_contents.setText(contents);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_privacy_close :
                    finish();
                    break;
                default :
                    break;
            }
        }
    };

    private void LayoutSet() {
        DisplayMetrics mDisplayMetrics_margin = getResources().getDisplayMetrics();
        int mHeightMarginsSize = Math.round(10 * mDisplayMetrics_margin.density);
        int mWidthMarginsSize = Math.round(25 * mDisplayMetrics_margin.density);

        Display mDisplay = getWindowManager().getDefaultDisplay();
        double mRealWidth, mRealHeight;

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        mDisplay.getRealMetrics(mDisplayMetrics);
        mRealWidth = mDisplayMetrics.widthPixels;
        mRealHeight = mDisplayMetrics.heightPixels;

        LinearLayout.LayoutParams ll_privacy_close_param = (LinearLayout.LayoutParams) ll_privacy_close.getLayoutParams();

        double mScreenRate = mRealHeight / mRealWidth;

        if (mScreenRate >= 2) {
            ll_privacy_close_param.setMargins(mHeightMarginsSize, mHeightMarginsSize, mHeightMarginsSize, mHeightMarginsSize);
        } else {
            ll_privacy_close_param.setMargins(mWidthMarginsSize, mHeightMarginsSize, mWidthMarginsSize, mHeightMarginsSize);
        }
    }
}
