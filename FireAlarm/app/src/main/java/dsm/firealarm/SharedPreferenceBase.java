package dsm.firealarm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import java.util.HashSet;

/**
 * Created by 10107PSH on 2017-09-21.
 * SharedPreferences 일종의 DB.
 */

public class SharedPreferenceBase extends AppCompatActivity{
    private Context context;
    public static final String SHARED_PREFERENCE_NAME_COOKIE = "firealarm.cookie";
    private static SharedPreferenceBase mSharedPreferenceBase = null;
    private SharedPreferences sharedPreferences;

    public static SharedPreferenceBase getInstanceOf(Context c) {
        if(mSharedPreferenceBase == null) {
            mSharedPreferenceBase = new SharedPreferenceBase(c);
        }
        return mSharedPreferenceBase;
    }

    /**
     * @param context
     */

    public SharedPreferenceBase(Context context) {
        this.context = context;
        final String SHARED_PREFERENCE_NAME_COOKIE = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME_COOKIE, Activity.MODE_PRIVATE);
    }

    /**
     * @param key
     * @param hashSet
     */

    //SharedPreferences에 데이터 저장하기
    public void putSharedPreference(String key, HashSet<String> hashSet) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, hashSet); //key라는 key값으로 hashSet 데이터를 저장
        editor.commit(); //완료
    }

    /**
     * @param key
     * @param cookie
     * @return
     */

    public HashSet<String> getSharedPreferences (String key, HashSet<String> cookie) {
        try {
            return (HashSet<String>) sharedPreferences.getStringSet(key, cookie);
        } catch (Exception e) {
            return cookie;
        }
    }
}