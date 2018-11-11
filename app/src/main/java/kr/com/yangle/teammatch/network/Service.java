package kr.com.yangle.teammatch.network;

import android.content.Context;
import android.util.Log;

import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONObject;

import java.util.ArrayList;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

/**
 * Service 실행부
 * Created by maloman72 on 2018-10-31
 */

public class Service {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private ApplicationTM mApplicationTM;

    private DefinitionNetwork mDefinitionNetwork;

    /**
     * 선언부
     * Created by maloman72 on 2018-10-31
     * */
    public Service(Context context) {
        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();

        mDefinitionNetwork = new DefinitionNetwork(mContext);
    }

    private void Offer(String serviceURL, JSONObject dataObject, ResponseListener responseListener) {
        try {
            String mData = mDefinitionNetwork.requsetParser(dataObject);

            if(mData != null) {
                mApplicationTM.startProgress(mContext, "");

                mDefinitionNetwork.Networking(serviceURL, mData, responseListener);
            } else {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
            }
        } catch (Exception e) {
            Log.e(TAG, "Offer - " + e);
        }
    }

    private void NaverOffer(String serviceURL, String serviceData, ResponseListener responseListener) {
        try {
            mApplicationTM.startProgress(mContext, "");

            mDefinitionNetwork.NaverNetworking(serviceURL, serviceData, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "NaverOffer - " + e);
        }
    }

    /**
     * 로그인 서비스
     * Created by maloman72 on 2018-10-31
     * */
    public void userLogin(ResponseListener responseListener, String email_id) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.userlogin_service);

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.userlogin_param_email_id), email_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "userLogin - " + e);
        }
    }

    /**
     * 네이버 아이디로 로그인 - 회원 프로필조회
     * Created by maloman72 on 2018-11-04
     * */
    public void naverSearchProfile(ResponseListener responseListener, OAuthLogin oAuthLogin) {
        String mURL = mContext.getString(R.string.login_naver_url);

        NaverOffer(mURL, oAuthLogin.getAccessToken(mContext), responseListener);
    }

    /**
     * 회원정보 등록 서비스
     * Created by maloman72 on 2018-10-31
     * */
    public void insertUserInfo(ResponseListener responseListener, String email_id, String user_name, String user_telnum, String team_name, ArrayList<String> hope_grounds, String team_level_code, String team_age_code) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.insertuserinfo_service);

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.insertuserinfo_param_email_id), email_id);
            mJSONObject.put(mContext.getString(R.string.insertuserinfo_param_user_name), user_name);
            mJSONObject.put(mContext.getString(R.string.insertuserinfo_param_user_telnum), user_telnum);
            mJSONObject.put(mContext.getString(R.string.insertuserinfo_param_team_name), team_name);
            mJSONObject.put(mContext.getString(R.string.insertuserinfo_param_hope_grounds), mApplicationTM.ArrayListToStringParser(hope_grounds));
            mJSONObject.put(mContext.getString(R.string.insertuserinfo_param_team_level_code), team_level_code);
            mJSONObject.put(mContext.getString(R.string.insertuserinfo_param_team_age_code), team_age_code);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "insertUserInfo - " + e);
        }
    }


    /**
     * 회원정보 수정 서비스
     * Created by maloman72 on 2018-10-31
     * */
    public void updateUserInfo(ResponseListener responseListener, String email_id, String user_name, String user_telnum, String team_id, String team_name, ArrayList<String> hope_grounds, String team_level_code, String team_age_code) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.updateuserinfo_service);

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.updateuserinfo_param_email_id), email_id);
            mJSONObject.put(mContext.getString(R.string.updateuserinfo_param_user_name), user_name);
            mJSONObject.put(mContext.getString(R.string.updateuserinfo_param_user_telnum), user_telnum);
            mJSONObject.put(mContext.getString(R.string.updateuserinfo_param_team_id), team_id);
            mJSONObject.put(mContext.getString(R.string.updateuserinfo_param_team_name), team_name);
            mJSONObject.put(mContext.getString(R.string.updateuserinfo_param_hope_grounds), mApplicationTM.ArrayListToStringParser(hope_grounds));
            mJSONObject.put(mContext.getString(R.string.updateuserinfo_param_team_level_code), team_level_code);
            mJSONObject.put(mContext.getString(R.string.updateuserinfo_param_team_age_code), team_age_code);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "insertUserInfo - " + e);
        }
    }


    /**
     * 회원정보 조회 서비스
     * Created by maloman72 on 2018-10-31
     * */
    public void searchUserInfo(ResponseListener responseListener, String email_id) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.searchuserinfo_service);

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.searchuserinfo_email_id), email_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "insertUserInfo - " + e);
        }
    }

    /**
     * 매칭 검색 서비스
     * Created by maloman72 on 218-11-01
     * */
    public void searchMatchList(ResponseListener responseListener, String search_date, String search_time, String search_area_group, String search_area, String search_ground, String search_team_member, String search_team_lvl) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.searchmatchlist_service);
            String email_id = mApplicationTM.getUserEmail();

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_email_id), email_id);
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_search_date), search_date);
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_search_time), search_time);
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_search_area_group), search_area_group);
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_search_area), search_area);
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_search_ground), search_ground);
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_search_team_member), search_team_member);
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_search_team_lvl), search_team_lvl);
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_search_count), mContext.getString(R.string.searchmatchlist_param_search_count_data));
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_search_page), mContext.getString(R.string.searchmatchlist_param_search_page_data));

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchMatchList - " + e);
        }

    }

    /**
     * 구장 검색 서비스
     * Created by maloman72 on 218-11-04
     * */
    public void searchGroundList(ResponseListener responseListener, String search_type_code, String search_loc_lat, String search_loc_lon, String search_area_code, String search_area_group_code) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "ground/searchGroundList";
            String email_id = mApplicationTM.getUserEmail(), search_count = "50", search_page = "1";

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("email_id", email_id);
            mJSONObject.put("search_type_code", search_type_code);
            mJSONObject.put("search_loc_lat", search_loc_lat);
            mJSONObject.put("search_loc_lon", search_loc_lon);
            mJSONObject.put("search_area_code", search_area_code);
            mJSONObject.put("search_area_group_code", search_area_group_code);
            mJSONObject.put("search_count", search_count);
            mJSONObject.put("search_page", search_page);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchGroundList - " + e);
        }

    }

    /**
     * 구장 정보 상세 조회 서비스
     * Created by maloman72 on 218-11-04
     * */
    public void searchGroundDetail(ResponseListener responseListener, String ground_id) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "ground/searchGroundDetail";
            String email_id = mApplicationTM.getUserEmail();

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("email_id", email_id);
            mJSONObject.put("ground_id", ground_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchGroundDetail - " + e);
        }

    }

    /**
     * 지역 그룹 코드 조회 서비스
     * Created by maloman72 on 218-11-04
     * */
    public void searchAreaGroupList(ResponseListener responseListener) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "etc/searchAreaGroupList";
            String email_id = mApplicationTM.getUserEmail();

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("email_id", email_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchAreaGroupList - " + e);
        }

    }

    /**
     * 지역 코드 조회 서비스
     * Created by maloman72 on 218-11-04
     * */
    public void searchAreaList(ResponseListener responseListener, String area_group_code) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "etc/searchAreaList";
            String email_id = mApplicationTM.getUserEmail();

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("email_id", email_id);
            mJSONObject.put("area_group_code", area_group_code);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchAreaList - " + e);
        }
    }

    /**
     * 매치 진행 상황 조회
     * Created by maloman72 on 218-11-08
     * */
    public void searchMatchProcList(ResponseListener responseListener) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "matchHist/searchMatchProcList";
            String email_id = mApplicationTM.getUserEmail(), search_count = "50", search_page = "1";

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("email_id", email_id);
            mJSONObject.put("search_count", search_count);
            mJSONObject.put("search_page", search_page);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchMatchProcList - " + e);
        }
    }

    /**
     * 랭킹 조회
     * Created by maloman72 on 2018-11-10
     * */
    public void searchRankList(ResponseListener responseListener, String rankingType) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.ranking_service);
            String email_id = mApplicationTM.getUserEmail(), search_count = "50", search_page = "1";

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.ranking_param_email_id), email_id);
            mJSONObject.put(mContext.getString(R.string.ranking_param_search_type_code), rankingType);
            mJSONObject.put(mContext.getString(R.string.ranking_param_search_count), search_count);
            mJSONObject.put(mContext.getString(R.string.ranking_param_search_page), search_page);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchRankList - " + e);
        }
    }

    /**
     * 매치 신청 서비스
     * Created by maloman72 on 218-11-01
     * */
    public void applyMatch(ResponseListener responseListener, String match_id) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.applyMatch_service);
            String email_id = mApplicationTM.getUserEmail();

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.applyMatch_param_email_id), email_id);
            mJSONObject.put(mContext.getString(R.string.applyMatch_param_match_id), match_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchMatchList - " + e);
        }

    }

    /**
     * 매치 등록 서비스
     * Created by maloman72 on 218-11-01
     * */
    public void registMatch(ResponseListener responseListener, String match_hope_ground_id, String match_hope_date, String match_hope_time, String match_hope_team_member, String match_hope_team_lvl) {
        try {
            String mURL = mContext.getString(R.string.service_url) + mContext.getString(R.string.registMatch_service);
            String email_id = mApplicationTM.getUserEmail();

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put(mContext.getString(R.string.searchmatchlist_param_email_id), email_id);
            mJSONObject.put(mContext.getString(R.string.registMatch_param_match_hope_ground_id), match_hope_ground_id);
            mJSONObject.put(mContext.getString(R.string.registMatch_param_match_hope_date), match_hope_date);
            mJSONObject.put(mContext.getString(R.string.registMatch_param_match_hope_time), match_hope_time);
            mJSONObject.put(mContext.getString(R.string.registMatch_param_match_hope_team_member), match_hope_team_member);
            mJSONObject.put(mContext.getString(R.string.registMatch_param_match_hope_team_lvl), match_hope_team_lvl);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "searchMatchList - " + e);
        }

    }
}
