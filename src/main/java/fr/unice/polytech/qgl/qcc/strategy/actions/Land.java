package fr.unice.polytech.qgl.qcc.strategy.actions;


import org.json.JSONObject;

/**
 * Created by renaud on 11/11/2015.
 */
public class Land extends Action {

    private String creekID; //id alphanum de la crique, recupérée au cours d'un scan
    private int nbrOfWorkers; //nbr de personnes qui vont aller à terre

    public Land(String id, int nbr){
        creekID = id;
        nbrOfWorkers = nbr;
    }


    public JSONObject act()
    {
        action = new JSONObject();
        action.put("action", "land");
        JSONObject parameters = new JSONObject();
        parameters.put("creek", creekID);
        parameters.put("people", nbrOfWorkers);
        action.put("parameters", parameters);
        return action;
    }
}
