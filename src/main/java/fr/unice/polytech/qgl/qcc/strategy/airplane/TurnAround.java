package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.database.*;
import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.strategy.actions.*;
import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import org.json.JSONObject;

/**
 * Created by Kevin on 08/12/2015.
 */
public class TurnAround implements Strategy {

    private Carte carte;
    boolean changedStep = false;
    private boolean firstCall = true;
    private JSONObject lastAction;
    private Direction firstPlaneDirection;
    private Direction directionBeforeTurnAround;

    public TurnAround(Carte carte, Direction firstPlaneDirection){
        this.carte = carte;
        this.firstPlaneDirection = firstPlaneDirection;
        directionBeforeTurnAround = carte.getPlaneDirection();
    }

    /**
     * Method who take the action to turn around
     * @param lastActionResult
     * @return
     */
    @Override
    public JSONObject takeDecision(JSONObject lastActionResult) {
        Action action;
        if(firstCall){
            firstCall = false;
            lastAction = new JSONObject("{ \"action\": \"echo\"}");
        }


        String lastActionName;
        lastActionName = lastAction.getString("action");

        switch(lastActionName){
            case "echo":
                action = new Heading(firstPlaneDirection);
                break;
            case "heading":
                action = new Heading(directionBeforeTurnAround.clockwise().clockwise());
                changedStep = true;
                break;
            default:
                action = new Echo(carte.getPlaneDirection());
        }
        lastAction = action.act();
        return lastAction;
    }

    @Override
    public boolean changedStep() {
        return changedStep;
    }
}
