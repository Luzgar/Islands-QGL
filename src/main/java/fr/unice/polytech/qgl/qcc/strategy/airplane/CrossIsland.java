package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.database.*;
import fr.unice.polytech.qgl.qcc.strategy.actions.*;
import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kevin on 08/12/2015.
 */
public class CrossIsland implements Strategy {

    private Carte carte;
    boolean changedStep = false;
    private boolean firstCall = true;
    private boolean firstScan;
    private boolean wasTurnAroundStrategyBefore = true;
    private boolean willCrossIsland = true;
    private JSONObject lastAction;
    private int distanceToIsland;

    public CrossIsland(Carte carte){
        this.carte = carte;
    }

    /**
     * Method who decide the action to take to cross the island
     * @param lastActionResult
     * @return
     */
    @Override
    public JSONObject takeDecision(JSONObject lastActionResult) {
        Action action;

        if(firstCall){
            action = new Echo(carte.getPlaneDirection());
            firstCall = false;
            lastAction = action.act();
            return lastAction;
        }

        String lastActionName;
        lastActionName = lastAction.getString("action");

        switch(lastActionName){
            case "echo":
                String found = lastActionResult.getJSONObject("extras").getString("found");
                int range = lastActionResult.getJSONObject("extras").getInt("range");
                if("GROUND".equals(found)){
                    if(range <=1){
                        action = new Scan();
                        firstScan = true;
                    }
                    else {
                        distanceToIsland = range;
                        action = new Fly();
                        distanceToIsland--;
                    }
                }
                else{
                    changedStep = true;
                    if(wasTurnAroundStrategyBefore)
                        return new JSONObject("{ \"action\": \"nextStep\" }");
                    else
                        action = new Echo(carte.getPlaneDirection());
                }
                break;
            case "scan":
                JSONArray biomesScan = lastActionResult.getJSONObject("extras").getJSONArray("biomes");
                boolean onlyOcean = false;
                if(biomesScan.length() == 1 && "OCEAN".equals(biomesScan.getString(0)))
                    onlyOcean = true;
                if(onlyOcean && !firstScan) {
                    action = new Echo(carte.getPlaneDirection());
                }
                else {
                    action = new Fly();
                    firstScan = false;
                }
                willCrossIsland = false;
                break;
            case "fly":
                if(distanceToIsland > 1){
                    action = new Fly();
                    distanceToIsland--;
                }
                else{
                    if(willCrossIsland) {
                        firstScan = true;
                    }
                    action = new Scan();
                }
                break;
            default:
                action = new Echo(carte.getPlaneDirection());
                break;
        }
        wasTurnAroundStrategyBefore = false;
        lastAction = action.act();
        return lastAction;
    }

    @Override
    public boolean changedStep() {
        return changedStep;
    }
}
