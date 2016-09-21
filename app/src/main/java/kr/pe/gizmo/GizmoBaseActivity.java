package kr.pe.gizmo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by yhzo on 16. 9. 21.
 *
 * Base Activity
 */
public class GizmoBaseActivity extends AppCompatActivity {

    private GizmoBaseApplication app = GizmoBaseApplication.getInstance();
    public Retrofit retrofit;

    private ProgressDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        waitDialog = new ProgressDialog(this);
        waitDialog.setMessage("잠시만 기다려주세요.");

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(app.getServerUrl() )
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public void onResume(){
        super.onResume();
    }

    /**
     * Toast 커스터 마이징
     * @param msg toast 메세지
     * @return Toast
     */
    public Toast showToastMsg(String msg){

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, toast.getXOffset(), toast.getYOffset());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

        return toast;
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 다이얼로그 알림창 표시
     * 기본 Alert다이얼로그
     * @param title 타이틀
     * @param message 보여줄 메세지
     */
    public void showMessageBox(String title, String message) {

        this.showMessageBox(title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, null);
    }

    /**
     * 다이얼로그 알림창 표시
     * 기본 Alert다이얼로그에 html을 이용해서 만든 Spanned 메세지를 보여준다.
     * @param title 타이틀
     * @param message 보여줄 메세지
     */
    public void showMessageBox(String title, Spanned message) {

        this.showMessageBox(title, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, null);
    }

    /**
     * 다이얼로그 알림창 표시
     * 확인 버튼만 있는 Listener에 html을 이용해서 만든 Spanned 메세지를 보여준다.
     * @param title 타이틀
     * @param message 보여줄 메세지
     * @param confirmListener 확인 버튼의 리스너
     */
    public void showMessageBox(String title, Spanned message,
                               DialogInterface.OnClickListener confirmListener) {
        this.showMessageBox(title, message, confirmListener, null);
    }

    /**
     * 다이얼로그 알림창 표시
     * 확인 버튼만 있는 Listener
     *
     * @param title 타이틀
     * @param message 보여줄 메세지
     * @param confirmListener 확인 버튼의 리스너
     */
    public void showMessageBox(String title, String message,
                               DialogInterface.OnClickListener confirmListener) {
        this.showMessageBox(title, message, confirmListener, null);
    }

    /**
     * 커스텀 AlertDialog
     * @param title 제목
     * @param message 내용
     * @param confirmListener 확인버튼 리스너
     * @param cancelListener 취소버튼 리스너
     */
    public void showMessageBox(String title, String message,
                               DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);

        TextView myMsg = new TextView(this);
        myMsg.setPadding(getDIP(25), getDIP(20),0,0);
        myMsg.setText(title);
        myMsg.setTextColor(Color.BLACK);
        myMsg.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        ab.setCustomTitle(myMsg);

//        ab.setTitle(title);
        ab.setMessage(message);
        ab.setCancelable(false);
        if (confirmListener == null) {
            confirmListener = defHandler;
        }
        ab.setPositiveButton("확인", confirmListener);
        if (cancelListener != null) {
            ab.setNegativeButton("취소", cancelListener);
        }
        AlertDialog dialog = ab.show();
        TextView msgView = (TextView) dialog.findViewById(android.R.id.message);
        msgView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        Button btn1 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btn1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        Button btn2 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btn2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
    }

    /**
     * 알림창 기본 핸들러
     */
    static DialogInterface.OnClickListener defHandler = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    /**
     * 디이얼로그에 보여주는 text를 강조하기 위해
     * Html Tag를 사용한 Spanned Text를 message로 받는다.
     * Spanned 작성법
     * Html.fromHtml("<strong>아아아아</strong>")
     * @param title 제목
     * @param message 내용
     * @param confirmListener 확인버튼 리스너
     * @param cancelListener 취소버튼 리스너
     */
    public void showMessageBox(String title, Spanned message,
                               DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);

        TextView myMsg = new TextView(this);
        myMsg.setPadding(getDIP(25), getDIP(20),0,0);
        myMsg.setText(title);
        myMsg.setTextColor(Color.BLACK);
        myMsg.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        ab.setCustomTitle(myMsg);

//        ab.setTitle(title);
        ab.setMessage(message);
        ab.setCancelable(false);
        if (confirmListener == null) {
            confirmListener = defHandler;
        }
        ab.setPositiveButton("확인", confirmListener);
        if (cancelListener != null) {
            ab.setNegativeButton("취소", cancelListener);
        }
        AlertDialog dialog = ab.show();

        //-- 시스템에서 글씨를 크게 하면 크게 나오는걸 방지 한다.
        //-- 내용과, 버튼에 적용.
        TextView msgView = (TextView) dialog.findViewById(android.R.id.message);
        msgView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        Button btn1 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btn1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        Button btn2 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btn2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

    }

    /**
     * 화면에서 동적으로 View를 만들어서 넣을때 size를 넣어야 되거나 높이나 너비를 넣어야 할때,
     * 파라미터로 받은 값을 dp에 대응 되는 px값으로 변환해서 넘겨준다.
     * @param dp 픽셀로 변환할 dp값
     * @return pixel 값
     */
    public int getDIP(int dp){

        int val = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return val;
    }

    public void closeWaitDialog(){
        waitDialog.dismiss();
    }

    /**
     * 네트워크 통신을 시작 하기전에 dialog를 보여주기 때문에 통신이 끝나는 callback에서 반드시 closeWaitDialog를 호출해줘야 한다.
     * @param call 서버 통신의 위한 Call의 객체 통신에 필요한 service를 가지고 있다.
     * @param callback 서버 통신후 결과에 대한 콜백 객체
     */
    public void doActionResult(Call call, Callback<JSONObject> callback){

        if(!this.isFinishing()) {
            waitDialog.show();
            call.enqueue(callback);
        }
    }
}
