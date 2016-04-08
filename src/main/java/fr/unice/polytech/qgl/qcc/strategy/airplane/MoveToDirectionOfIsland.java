package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.database.*;
import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.strategy.actions.*;
import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import org.json.JSONObject;

/**
 * Created by Kevin on 07/12/2015.
 */
public class MoveToDirectionOfIsland implements Strategy {

    boolean changedStep = false;
    private boolean firstCall = true;
    private JSONObject lastAction;
    private Direction firstPlaneDirection;
    private Carte carte;

    int distanceToBorder;

    public MoveToDirectionOfIsland(Carte carte, Direction firstPlaneDirection){
        this.carte = carte;
        this.firstPlaneDirection = firstPlaneDirection;
    }

    /**
     * Method who decide the action to take to move to the direction of the island
     * @param lastActionResult
     * @return
     */
    @Override
    public JSONObject takeDecision(JSONObject lastActionResult) {
        Action action;

        if(firstCall){
            this.firstCall = false;
            action = new Echo(carte.getPlaneDirection().clockwise());
            lastAction = action.act();
            return lastAction;
        }

        String lastActionName = lastAction.getString("action");

        switch(lastActionName){
            case "echo":
                Direction dir = Direction.valueOf(lastAction.getJSONObject("parameters").getString("direction"));
                int range = lastActionResult.getJSONObject("extras").getInt("range");

                if(range > 2) {
                    distanceToBorder = range;
                    action = new Heading(dir);
                    distanceToBorder--;
                }
                else {
                    action = new Fly();
                    changedStep = true;
                }
                break;
            case "fly":
                if(distanceToBorder > 3) {
                    action = new Fly();
                    distanceToBorder--;
                }
                else {
                    action = new Heading(firstPlaneDirection);
                    changedStep = true;
                }
                break;
            case "heading":
                action = new Fly();
                distanceToBorder--;
                break;
            default:
                action = new Fly();
                break;
        }
        lastAction = action.act();
        return lastAction;
    }

    @Override
    public boolean changedStep() {
        return changedStep;
    }
}
