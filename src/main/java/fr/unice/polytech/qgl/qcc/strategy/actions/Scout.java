package fr.unice.polytech.qgl.qcc.strategy.actions;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;

/**
 * Created by Kevin on 28/11/2015.
 */
public class Scout extends Action {

    private Direction direction;

    public Scout(Direction direction){
        this.direction = direction;
    }

    public JSONObject act() {
        JSONObject action = new JSONObject();
        action.put("action", "scout");
        JSONObject parameters = new JSONObject();
        parameters.put("direction", direction.toString());
        action.put("parameters", parameters);
        return action;
    }

    /*public static void result(String response){
        //{ "cost": 5, "extras": { "altitude": 1, "resources": ["FUR", "WOOD"] }, "status": "OK" }
    }*/
}
