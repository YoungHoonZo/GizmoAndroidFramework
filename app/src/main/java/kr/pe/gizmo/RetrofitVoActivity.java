package kr.pe.gizmo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.pe.gizmo.adapter.ListVoAdapter;
import kr.pe.gizmo.service.ActorService;
import kr.pe.gizmo.vo.ActorItem;
import kr.pe.gizmo.vo.ActorVo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitVoActivity extends GizmoBaseActivity {

    String TAG = "RetrofitVoActivity";

    Call call;
    ActorService service;
    ArrayList<ActorItem> items;

    ListVoAdapter adapter;

    @BindView(R.id.lvActorList)
    ListView lvActorList;

    Callback<ActorVo> callback = new Callback<ActorVo>() {

        /**
         * 한개의 Activity에서 각각 다른 서버api를 호출을 해야 하는 경우가 있기 때문에
         * 콜백 안에서 각 요청에 대한 구분을 줘야 한다.
         * 그래서 서버단에서 호출하는 api의 명칭을 command로 받아서 구분 시킨다.
         * @param call
         * @param response
         */
        @Override
        public void onResponse(Call<ActorVo> call, Response<ActorVo> response) {
            closeWaitDialog();
            ActorVo result = (ActorVo)response.body();


            if(result.getCommand().equals("actorList")){

                items.addAll( result.getActorList());
                adapter.notifyDataSetChanged();
            }
        }

//        @Override
//        public void onResponse(Call call, Response<ActorVo> response) {
//            closeWaitDialog();
//            ActorVo result = (ActorVo)response.body();

//            if(result.get("command").equals("actorList")){
//
//                items.addAll( (ArrayList<ActorVo>)result.get("actorList"));
//                adapter.notifyDataSetChanged();
//            }
//        }

        @Override
        public void onFailure(Call call, Throwable t) {
            closeWaitDialog();
            Log.e(TAG, "onFailure");
            Log.e(TAG, t.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_map);
        ButterKnife.bind(this);

        items = new ArrayList<>();
        adapter = new ListVoAdapter(this, items);
        lvActorList.setAdapter(adapter);
        service = retrofit.create(ActorService.class);
        Call<ActorVo> call = service.getActorVoList();
        doActionVoResult(call, callback);
    }
}
