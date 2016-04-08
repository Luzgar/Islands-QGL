package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import fr.unice.polytech.qgl.qcc.database.*;
import fr.unice.polytech.qgl.qcc.database.enums.*;
import fr.unice.polytech.qgl.qcc.strategy.actions.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kevin on 12/12/2015.
 */
public class ExploreArea implements Strategy {

    private Carte carte;
    private Contract contract;
    private Biomes.Ressource ressourceToExplore;
    private Biomes biome;
    private boolean firstCall = true;
    boolean changedStep = false;
    private boolean haveExploited = false;

    public ExploreArea(Carte carte, Contract contract, Biomes biome, Biomes.Ressource ressourceToExplore){
        this.carte = carte;
        this.contract = contract;
        this.biome = biome;
        this.ressourceToExplore = ressourceToExplore;
    }

    /**
     * Method who take the action to explore a area (explore + exploit if searching resources present)
     * @param lastActionResult
     * @return
     */
    @Override
    public JSONObject takeDecision(JSONObject lastActionResult) {
        Action action;
        if(firstCall){
            action = new Explore();
            firstCall = false;
            return action.act();
        }

        JSONArray resources = lastActionResult.getJSONObject("extras").getJSONArray("resources");
        JSONObject resourcesInfo;
        for(int i = 0; i < resources.length(); ++i){
            resourcesInfo = resources.getJSONObject(i);
            if(Biomes.Ressource.valueOf(resourcesInfo.getString("resource")).equals(ressourceToExplore)){
                //if(!resourcesInfo.getString("amount").equals("LOW") && !resourcesInfo.getString("cond").equals("HARSH")) {
                    action = new Exploit(resourcesInfo.getString("resource"));
                    changedStep = true;
                    return action.act();
                //}
            }
        }
        action = new Scout(Direction.N);
        changedStep = true;
        return action.act();

    }

    @Override
    public boolean changedStep() {
        return changedStep;
    }

}
