package fr.unice.polytech.qgl.qcc;

import fr.unice.polytech.qgl.qcc.database.Carte;
import fr.unice.polytech.qgl.qcc.database.CarteStats;
import fr.unice.polytech.qgl.qcc.database.Contract;
import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.database.enums.StepEnum;
import fr.unice.polytech.qgl.qcc.exceptions.NoHeadingException;
import fr.unice.polytech.qgl.qcc.strategy.Strategy;

import fr.unice.polytech.qgl.qcc.strategy.airplane.CreeksFinder;
import fr.unice.polytech.qgl.qcc.strategy.airplane.MoveExtrimityIsland;
import fr.unice.polytech.qgl.qcc.strategy.airplane.MoveToDirectionOfIsland;
import fr.unice.polytech.qgl.qcc.strategy.ground.ExploreIsland;
import fr.unice.polytech.qgl.qcc.strategy.ground.LandOnIsland;
import org.json.JSONObject;

import org.apache.log4j.Logger;

/**
 * Created by Kevin on 30/11/2015.
 */

public class GameEngine {

    private Carte carte; /*Carte de l'île que l'on construit*/
    private Contract contract; /*Contrat du client*/
    Strategy actualStrategy; /*Stratégie actuelle*/
    AnalyzeResult resultAnalyzer; /*Analyseur des resultats venant de l'arène*/
    private JSONObject lastActionResult; /*Resultat de la dernière action effectuée*/
    JSONObject lastTakenAction; /*Dernière action effectuée*/
    StepEnum currentStep = StepEnum.MOVETODIRECTIONOFISLAND; /*Etape courante*/
    private CarteStats carteStats;

    public GameEngine(JSONObject init) throws Exception{
        carte = new Carte(init);
        Direction planeDirection;
        try {
            planeDirection = Direction.valueOf(init.getString("heading"));
        }
        catch (Exception e)
        {
            throw new NoHeadingException();
        }
        carte.extrapolate(planeDirection);
        contract = new Contract(init);
        carteStats = new CarteStats(carte, contract);
        actualStrategy = new MoveToDirectionOfIsland(carte, contract.getFirstDirection());
        resultAnalyzer = new AnalyzeResult(carte, contract, carteStats);
        Biomes.fill();
    }

    /**
     * Method who regroup all the different strategy and decide wich strategy to call
     * @return
     */
    public JSONObject takeDecision(){
        JSONObject decision;
        if(actualStrategy.changedStep()){
            currentStep = currentStep.next();
            switch (currentStep){
                case FINDCREEKS:
                    actualStrategy = new CreeksFinder(carte, contract, carteStats);
                    break;
                case LANDONISLAND:
                    actualStrategy = new LandOnIsland(carte, contract);
                    break;
                case EXPLOREISLAND:
                    actualStrategy = new ExploreIsland(carte, contract);
                    break;
                case MOVEEXTRIMITYISLAND:
                    actualStrategy = new MoveExtrimityIsland(carte);
                    break;
           }
        }

        decision = actualStrategy.takeDecision(lastActionResult);
        lastTakenAction = decision;
        return decision;
    }

    /**
     * Method who take the result of a action and analyze it
     * @param result
     */
    public void acknowledgeResults(JSONObject result){
        lastActionResult = result;
        String action = lastTakenAction.getString("action");
        Direction dir;
        switch (action){

            case "echo":
                resultAnalyzer.analyzeEchoResult(lastActionResult);
                break;
            case "fly":
                resultAnalyzer.analyzeFlyResult(lastActionResult);
                break;
            case "heading":
                dir = Direction.valueOf(lastTakenAction.getJSONObject("parameters").getString("direction"));
                resultAnalyzer.analyzeHeadingResult(lastActionResult, dir);
                break;
            case "scan":
                resultAnalyzer.analyzeScanResult(lastActionResult);
                break;
            case "land":
                resultAnalyzer.analyzeLandResult(lastActionResult);
                break;
            case "move_to":
                dir = Direction.valueOf(lastTakenAction.getJSONObject("parameters").getString("direction"));
                resultAnalyzer.analyzeMoveToResult(lastActionResult, dir);
                break;
            case "explore":
                resultAnalyzer.analyzeExploreResult(lastActionResult);
                break;
            case "exploit":
                Biomes.Ressource ressourceExploited = Biomes.Ressource.valueOf(lastTakenAction.getJSONObject("parameters").getString("resource"));
                resultAnalyzer.analyzeExploitResult(lastActionResult, ressourceExploited);
                break;
            case "scout":
                resultAnalyzer.analyzeScoutResult(lastActionResult);
                break;
            case "stop":
                resultAnalyzer.analyzeStopResult(lastActionResult);
                break;
            case "transform":
                resultAnalyzer.analyzeTransformResult(lastActionResult);
                break;
            default:
                break;
        }
    }

    public Carte getCarte(){
        return this.carte;
    }

    public Contract getContract(){
        return this.contract;
    }

    public CarteStats getCarteStats(){
        return this.carteStats;
    }

}
