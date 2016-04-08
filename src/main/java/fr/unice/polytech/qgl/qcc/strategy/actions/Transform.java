package fr.unice.polytech.qgl.qcc.strategy.actions;

import org.json.JSONObject;

/**
 * Created by user on 08/02/2016.
 */
public class Transform extends Action {

    private String ressource1;
    private int ammount1;
    private String ressource2;
    private int ammount2;

    public Transform(String res1, String res2, int a1, int a2){
        ressource1 = res1;
        ammount1 = a1;
        ressource2 = res2;
        ammount2 = a2;
    }

    public Transform(String res, int a){
        ressource1 = res;
        ammount1 = a;
    }

    @Override
    public JSONObject act() {
        action = new JSONObject();
        action.put("action", "transform");
        JSONObject parameters = new JSONObject();
        parameters.put(ressource1, ammount1);
        if( ressource2 != null)//Si on a  deux ressources et pas une seule
            parameters.put(ressource2, ammount2);
        action.put("parameters", parameters);
        return action;
    }
}
