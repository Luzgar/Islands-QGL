package fr.unice.polytech.qgl.qcc.strategy.actions;

import org.json.JSONObject;

/**
 * Created by renaud on 11/11/2015.
 */
public class Fly extends Action {


    public Fly() {}

    public JSONObject act()
    {
        action = new JSONObject();
        action.put("action","fly");
        return action;
    }
}
