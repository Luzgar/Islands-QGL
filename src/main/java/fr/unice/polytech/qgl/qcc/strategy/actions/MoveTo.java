package fr.unice.polytech.qgl.qcc.strategy.actions;


import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;

/**
 * Created by Kevin on 27/11/2015.
 */
public class MoveTo extends Action {

    private Direction direction;

    public MoveTo(Direction direction){
        this.direction = direction;
    }

    public JSONObject act() {
        action = new JSONObject();
        action.put("action","move_to");
        JSONObject parameters = new JSONObject();
        parameters.put("direction", direction.toString());
        action.put("parameters", parameters);
        return action;
    }
}
