package kr.pe.gizmo;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.pe.gizmo.adapter.ListMapAdapter;
import kr.pe.gizmo.service.ActorService;
import kr.pe.gizmo.vo.ActorVo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitMapActivity extends GizmoBaseActivity {

    Call call;
    ActorService service;
    ArrayList<Map> items;

    ListMapAdapter adapter;

    @BindView(R.id.lvActorList)
    ListView lvActorList;

    Callback callback = new Callback() {

        /**
         * 한개의 Activity에서 각각 다른 서버api를 호출을 해야 하는 경우가 있기 때문에
         * 콜백 안에서 각 요청에 대한 구분을 줘야 한다.
         * 그래서 서버단에서 호출하는 api의 명칭을 command로 받아서 구분 시킨다.
         * @param call
         * @param response
         */
        @Override
        public void onResponse(Call call, Response response) {
            closeWaitDialog();
            Map result = (HashMap)response.body();

            if(result.get("command").equals("actorList")){


                items.addAll( (ArrayList<Map>)result.get("actorList"));
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_map);
        ButterKnife.bind(this);

        items = new ArrayList<>();
        adapter = new ListMapAdapter(this, items);
        lvActorList.setAdapter(adapter);
        service = retrofit.create(ActorService.class);
        call = service.getActorMapList();
        doActionMapResult(call, callback);
    }
}
