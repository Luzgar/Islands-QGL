package fr.unice.polytech.qgl.qcc.strategy.actions;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;

/**
 * Created by Kevin on 03/12/2015.
 */
public class Glimpse extends Action {

    private Direction dir;
    private int range;

    public Glimpse(Direction dir, int range){
        this.dir = dir;
        this.range = range;
    }
    public JSONObject act() {
        action = new JSONObject();
        action.put("action","glimpse");
        JSONObject parameters = new JSONObject();
        parameters.put("direction", dir.toString());
        parameters.put("range", range);
        action.put("parameters", parameters);
        return action;
    }
}
