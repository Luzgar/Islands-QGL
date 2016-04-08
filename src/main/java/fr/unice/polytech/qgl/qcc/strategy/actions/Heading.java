package fr.unice.polytech.qgl.qcc.strategy.actions;


import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;

/**
 * Created by renaud on 11/11/2015.
 */
public class Heading extends Action {

    private Direction dir; //Direction à prendre lors du heading

    public Heading(Direction dir){
        this.dir = dir;
    }

    public JSONObject act(){
        action = new JSONObject();
        action.put("action", "heading"); //On ajoute à l'objet JSON l'action à faire (ici heading)
        JSONObject parameters = new JSONObject();
        parameters.put("direction", dir.toString()); //On ajoute les données concernant le heading (direction à prendre)
        action.put("parameters", parameters);
        return action;
    }
}
