package kr.com.yangle.teammatch.network;

/**
 * ResponseListener를 연결하기 위한 Interface
 * Created by maloman72 on 2018-10-31.
 */

public interface ResponseListener {
    void receive(ResponseEvent responseEvent);
}
