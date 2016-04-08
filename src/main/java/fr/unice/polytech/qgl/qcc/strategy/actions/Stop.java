package fr.unice.polytech.qgl.qcc.strategy.actions;


import org.json.JSONObject;

/**
 * Created by user on 11/11/15.
 */
public class Stop extends Action{

    //CONSTRUCTOR
    public Stop(){}

    public JSONObject act() {
        action = new JSONObject();
        action.put("action", "stop");
        return action;
    }
}
