package dsm.firealarm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Sohyeon Park on 2017-09-05.
 * 사용자 기기별 token을 생성하는 클래스.
 * 나중에 push 알림을 특정 타겟에게 보낼 때 사용되는 고유 키 값.
 * 토큰값을 용도에 맞게 사용
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService{
    private static final String TAG = "MyFirebaseIIDService";

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();

    }
}
