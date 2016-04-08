package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.database.Carte;
import fr.unice.polytech.qgl.qcc.database.CarteStats;
import fr.unice.polytech.qgl.qcc.database.Contract;
import fr.unice.polytech.qgl.qcc.strategy.actions.Action;
import fr.unice.polytech.qgl.qcc.strategy.actions.Land;
import fr.unice.polytech.qgl.qcc.strategy.actions.Stop;
import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import org.json.JSONObject;

/**
 * Created by Kevin on 21/11/2015.
 */
public class LandOnIsland implements Strategy {

    private Carte carte;
    private Contract contract;
    boolean changedStep = false;

    public LandOnIsland(Carte carte, Contract contract){
        this.carte = carte;
        this.contract = contract;
    }

    /**
     * Method who just land the explorator on the island, after sorting the contract and extrapolate the map
     * @param lastActionResult
     * @return
     */
    public JSONObject takeDecision(JSONObject lastActionResult) {
        Action action;
        if(carte.getListCreeks().size() != 0) {
            carte.extrapolate(contract.getFirstDirection());
            action = new Land(carte.getListCreeks().get(0).getId(), 1);
            carte.setCurrentCoord(carte.getListCreeks().get(0).getX(), carte.getListCreeks().get(0).getY());
            carte.setLandCoordinate(carte.getListCreeks().get(0).getX(), carte.getListCreeks().get(0).getY());
            changedStep = true;
            contract.setLandedMen(1);
            CarteStats makeMapStats = new CarteStats(carte, contract);
            makeMapStats.sortContract();
        }
        else
            action = new Stop();

        return action.act();
    }

    public boolean changedStep() {
        return changedStep;
    }
}
