package kr.com.yangle.teammatch.network;

/**
 * 각 Activity의 ResponseListener로 Result Data 전달
 * Created by maloman72 on 2018-10-31.
 */

public class ResponseEvent {
    String resultData;

    public ResponseEvent (String resultData) { this.resultData = resultData; }

    public String getResultData() { return this.resultData; }
}
