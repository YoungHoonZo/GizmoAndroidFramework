package kr.pe.gizmo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import kr.pe.gizmo.adapter.ListMapAdapter;
import kr.pe.gizmo.service.ActorService;
import retrofit2.Call;

public class MainActivity extends GizmoBaseActivity implements AdapterView.OnItemClickListener {

    Call call;
    ActorService service;
    ListMapAdapter adapter;
    ArrayList<Map> items;

    @BindView(R.id.lvList) ListView lvList;

    private GizmoBaseApplication app = GizmoBaseApplication.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        items = new ArrayList<Map>();
        service = retrofit.create(ActorService.class);
        makeListData();

        adapter = new ListMapAdapter(this, items);
        lvList.setAdapter(adapter);
//        lvList.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();
    }


    private void makeListData(){

        Map item = new HashMap();

        item = new HashMap();
        item.put("idx", 1);
        item.put("name", "Retrofit result using Map");//-- 0
        items.add(item);

        item = new HashMap();
        item.put("idx", 2);
        item.put("name", "Retrofit result using Vo");//-- 1
        items.add(item);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @OnItemClick(R.id.lvList)
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id){

        Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();

        Intent intent = null;
        if(position == 0){
            intent = new Intent(this, RetrofitMapActivity.class);
        }
        else if(position == 1){
            intent = new Intent(this, RetrofitVoActivity.class);
        }

        startActivity(intent);
    }
}
