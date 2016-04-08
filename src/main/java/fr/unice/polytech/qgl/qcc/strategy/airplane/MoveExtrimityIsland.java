package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.database.*;
import fr.unice.polytech.qgl.qcc.strategy.actions.*;
import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import org.json.JSONObject;

/**
 * Created by Kevin on 12/11/2015.
 */

/*Cette classe (ou strategie) permet de placer l'avion au niveau d'une extremité de l'île*/
public class MoveExtrimityIsland implements Strategy {

    private Carte carte;
    private boolean firstCall = true; /*Savoir si c'est la première fois qu'on appelle cette classe*/
    private JSONObject lastAction; /*Dernière action effectuée*/
    boolean changedStep = false;

    public MoveExtrimityIsland(Carte carte) {
        this.carte = carte;
        this.lastAction = new JSONObject("{ \"action\": \"debut\"}");
    }

    /**
     * Method who decide the action to take to move at the extremity of the island
     * @param lastActionResult
     * @return
     */
    public JSONObject takeDecision(JSONObject lastActionResult) {

        Action action;

        if (firstCall) {
            this.firstCall = false;
            action = new Echo(carte.getPlaneDirection().anticlockwise());
            lastAction = action.act();
            return lastAction;
        }

        String lastActionName = lastAction.getString("action");

        switch (lastActionName) {
            case "echo":
                String found = lastActionResult.getJSONObject("extras").getString("found");
                if (found.equals("GROUND")) {
                    action = new Heading(carte.getPlaneDirection().anticlockwise());
                    changedStep = true;
                }
                else {
                    action = new Fly();
                }
                break;
            case "fly":
            default:
                action = new Echo(carte.getPlaneDirection().anticlockwise());
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