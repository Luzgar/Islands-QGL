package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import fr.unice.polytech.qgl.qcc.database.*;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.strategy.actions.*;
import org.json.JSONObject;

/**
 * Created by Kevin on 24/11/2015.
 */
public class CreeksFinder implements Strategy {

    private Carte carte;
    private Direction firstPlaneDirection;
    boolean changedStep = false;
    private boolean wasCrossIsland = true;
    private Strategy actualStrategy;
    private Contract contract;
    private CarteStats carteStats;

    public CreeksFinder(Carte carte, Contract contract, CarteStats carteStats){
        this.carte = carte;
        this.contract = contract;
        this.firstPlaneDirection = contract.getFirstDirection();
        actualStrategy = new CrossIsland(carte);
        this.carteStats = carteStats;
    }

    /**
     * Method who decide the action to take to find a creek
     * @param lastActionResult
     * @return
     */
    @Override
    public JSONObject takeDecision(JSONObject lastActionResult) {
        if(actualStrategy.changedStep()){
            if(wasCrossIsland){
                actualStrategy = new TurnAround(carte, firstPlaneDirection);
                wasCrossIsland = false;
            }
            else {
                actualStrategy = new CrossIsland(carte);
                wasCrossIsland = true;
            }
        }
        JSONObject actionToDo = actualStrategy.takeDecision(lastActionResult);

        if(carteStats.canContractBeMade() && carte.getListCreeks().size() != 0){
            changedStep = true;
            return new Fly().act();
        }

        if("nextStep".equals(actionToDo.getString("action")) || contract.getPa() < contract.getInitialPA()*0.6){
            changedStep = true;
            return new Fly().act();
        }
        return actionToDo;
    }

    @Override
    public boolean changedStep() {
        return changedStep;
    }

}
