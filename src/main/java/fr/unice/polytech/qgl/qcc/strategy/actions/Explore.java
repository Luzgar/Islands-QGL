package fr.unice.polytech.qgl.qcc.strategy.actions;

import org.json.JSONObject;

/**
 * Created by Kevin on 03/12/2015.
 */
public class Explore extends Action {

    public JSONObject act() {
        JSONObject action = new JSONObject("{ \"action\": \"explore\" }");
        return action;
    }
}
