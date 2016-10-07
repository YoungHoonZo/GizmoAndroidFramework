package kr.pe.gizmo.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhzo on 16. 10. 6.
 */
public class ActorVo {

    private ArrayList<ActorItem> actorList;
    private String command;

    public ArrayList<ActorItem> getActorList() {
        return actorList;
    }

    public void setActorList(ArrayList<ActorItem> actorList) {
        this.actorList = actorList;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
