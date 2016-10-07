package kr.pe.gizmo.service;

import java.util.Map;

import kr.pe.gizmo.vo.ActorVo;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yhzo on 16. 9. 23.
 */
public interface ActorService {

    @POST("list/actorList.do")
    Call<Map> getActorMapList ();

    @POST("list/actorList.do")
    Call<ActorVo> getActorVoList ();

}
