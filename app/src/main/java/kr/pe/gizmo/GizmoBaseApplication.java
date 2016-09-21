package kr.pe.gizmo;

import android.app.Application;

import kr.pe.gizmo.common.ShPreference;

/**
 * Created by yhzo on 16. 9. 21.
 *
 * Base Application
 * Dex Method 최대 갯수가 65535개 이라서
 * 외부 라이브러리를 추가 하다 보면 초과 하는 경우가 생긴다.
 * 이럴땐 Application이 아니라 MultiDexApplication를 상속 해야 된다.
 * MultiDexApplication를 사용하기 위해선 app 레벨의 build.gradle에
  android {
     defaultConfig {
        multiDexEnabled true
     }
  }

 multiDexEnabled를 true로 해줘야 하고
 com.android.support:multidex:1.0.0 라이브러리를 추가 해야 한다.

 */
public class GizmoBaseApplication extends Application{

    public final static String PREF_USERPW = "PREF_USERPW";
    public final static String PREF_USERID = "PREF_USERID";

    public static final boolean DEBUG = false;

    // 접속할 서버 페이지. 운영서버
    public final String SERVER_ROOT_REAL = "http://1.234.20.171:8081";

    // 접속할 서버 페이지. 테스트 서버
    public final String SERVER_ROOT_TEST = "http://1.234.20.164:8080";

    private static GizmoBaseApplication instance;
    private ShPreference pref;


    @Override
    public void onCreate(){
        super.onCreate();

        instance = this;
    }

    /**
     * 서버 주소를 리턴한다.
     * @return server URL
     */
    public String getServerUrl(){
        if(DEBUG){
            return SERVER_ROOT_TEST;
        }
        else{
            return SERVER_ROOT_REAL;
        }
    }

    /**
     * Application의 인스턴스를 리턴한다.
     * @return Application instance
     */
    public static GizmoBaseApplication getInstance() {
        return instance;
    }

    /**
     * SharedPreferences에 값을 put 한다.
     * @param key key
     * @param value value
     */
    public void setPreference(String key, boolean value){
        pref.put(key, value);
    }

    /**
     * SharedPreferences에 값을 put 한다.
     * @param key key
     * @param value value
     */
    public void setPreference(String key, String value){
        pref.put(key, value);
    }

    /**
     * SharedPreferences에 값을 put 한다.
     * @param key key
     * @param value value
     */
    public void setPreference(String key, int value){
        pref.put(key, value);
    }

    /**
     * SharedPreferences에 값을 put 한다.
     * @param key key
     * @param value value
     */
    public void setPreference(String key, float value){
        pref.put(key, value);
    }

    /**
     * SharedPreferences에 값을 get 한다.
     * @param key key
     * @param defValue 값이 null 일경우 대체할 기본값.
     */
    public String getPreference(String key, String defValue){
        return pref.getValue(key, defValue);
    }

    /**
     * SharedPreferences에 값을 get 한다.
     * @param key key
     * @param defValue 값이 null 일경우 대체할 기본값.
     */
    public boolean getPreference(String key, boolean defValue){
        return pref.getValue(key, defValue);
    }

    /**
     * SharedPreferences에 값을 get 한다.
     * @param key key
     * @param defValue 값이 null 일경우 대체할 기본값.
     */
    public int getPreference(String key, int defValue){
        return pref.getValue(key, defValue);
    }

    /**
     * SharedPreferences에 값을 get 한다.
     * @param key key
     * @param defValue 값이 null 일경우 대체할 기본값.
     */
    public float getPreference(String key, float defValue){
        return pref.getValue(key, defValue);
    }

}
