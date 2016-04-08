package fr.unice.polytech.qgl.qcc.strategy.actions;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;

/**
 * Created by renaud on 11/11/2015.
 */
public class Echo extends Action {

    Direction dir; //Direction de l'echo

    public Echo(Direction dir)
    {
        this.dir = dir;
    }

    public JSONObject act()
    {
        action = new JSONObject();
        action.put("action","echo");
        JSONObject param = new JSONObject();
        param.put("direction",dir.toString());
        action.put("parameters",param);
        return action;
    }
}
