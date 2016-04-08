package fr.unice.polytech.qgl.qcc;

import fr.unice.polytech.qgl.qcc.database.*;
import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.enums.Craftables;
import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kevin on 30/11/2015.
 */
public class AnalyzeResult {

    private Carte map;
    private Contract contract;
    private CarteStats carteStats;

    public AnalyzeResult(Carte carte, Contract contract, CarteStats carteStats) {
        this.map = carte;
        this.contract = contract;
        this.carteStats = carteStats;
    }

    /**
     * Analyzer of a Fly action
     * @param result
     */
    public void analyzeFlyResult(JSONObject result) {
        contract.payPA(result.getInt("cost"));

        int x = map.getCurrentX();
        int y = map.getCurrentY();
        switch (map.getPlaneDirection()) //Changer les coordonnees instantanées
        {
            case N:
                map.setCurrentCoord(x, y + 3);
                break;
            case E:
                map.setCurrentCoord(x + 3, y);
                break;
            case S:
                map.setCurrentCoord(x, y - 3);
                break;
            case W:
                map.setCurrentCoord(x - 3, y);
                break;
        }
    }

    /**
     * Analyzer of a Echo action
     * @param result
     */
    public void analyzeEchoResult(JSONObject result){
        if ("OK".equals(result.getString("status"))) {
            contract.payPA(result.getInt("cost"));
        }
    }

    /**
     *  Analyzer of a Land action
     * @param result
     */
    public void analyzeLandResult(JSONObject result){
        if("OK".equals(result.getString("status"))){
            contract.payPA(result.getInt("cost"));
        }
    }

    /**
     *  Analyzer of a Scan action
     * @param result
     */
    public void analyzeScanResult(JSONObject result){
        if("OK".equals(result.getString("status"))) {
            contract.payPA(result.getInt("cost"));
            JSONObject extras = result.getJSONObject("extras");
            JSONArray biomes = extras.getJSONArray("biomes");
            map.addBigCase(map.getCurrentX(), map.getCurrentY(),biomes);
            JSONArray creeks = extras.getJSONArray("creeks");
            for(int i = 0; i < creeks.length(); ++i){
                map.addCreeks(new Creek(creeks.getString(i), map.getCurrentX(), map.getCurrentY()+2)); // On ajoute les creek trouvées lors d'un scan à la DataBase
            }

            if(biomes.length() == 1){
                for(int i = 0; i < 9; ++i){
                    carteStats.addBiomes(Biomes.valueOf(biomes.getString(0)));
                }
            }
        }
    }

    /**
     *  Analyzer of a Heading action
     * @param result
     * @param lastHeadingDirection
     */
    public void analyzeHeadingResult(JSONObject result, Direction lastHeadingDirection){
        //Récupération de la direction demandée pour le heading
        if("OK".equals(result.getString("status"))) {
            int x = map.getCurrentX();
            int y = map.getCurrentY();

            switch (map.getPlaneDirection()){ //Changer les coordonnees instantanées

                case N:
                    if (lastHeadingDirection.equals(Direction.E))
                        map.setCurrentCoord(x + 3, y + 3);
                    else
                        map.setCurrentCoord(x - 3, y + 3);
                    break;
                case E:

                    if (lastHeadingDirection.equals(Direction.N))
                        map.setCurrentCoord(x + 3, y + 3);
                    else
                        map.setCurrentCoord(x + 3, y - 3);
                    break;
                case S:

                    if (lastHeadingDirection.equals(Direction.E))
                        map.setCurrentCoord(x + 3, y - 3);
                    else
                        map.setCurrentCoord(x - 3, y - 3);
                    break;
                case W:
                    if (lastHeadingDirection.equals(Direction.N))
                        map.setCurrentCoord(x - 3, y + 3);
                    else
                        map.setCurrentCoord(x - 3, y - 3);
                    break;
                default:
                    break;
            }
            map.setPlaneDirection(lastHeadingDirection);
            contract.payPA(result.getInt("cost"));
        }
    }

    /**
     * Analyzer of a Move_to action
     * @param result
     * @param lastMoveToDirection
     */
    public void analyzeMoveToResult(JSONObject result, Direction lastMoveToDirection){
        if("OK".equals(result.getString("status"))) {
            contract.payPA(result.getInt("cost"));
            int x = map.getCurrentX();
            int y = map.getCurrentY();
            if (lastMoveToDirection.equals(Direction.N)) {
                map.setCurrentCoord(x, y + 1);
            } else if (lastMoveToDirection.equals(Direction.S)) {
                map.setCurrentCoord(x, y - 1);
            } else if (lastMoveToDirection.equals(Direction.E)) {
                map.setCurrentCoord(x + 1, y);
            } else {
                map.setCurrentCoord(x - 1, y);
            }
        }
    }

    /**
     * Analyzer of a Explore action
     * @param result
     */
    public void analyzeExploreResult(JSONObject result){
        if("OK".equals(result.getString("status"))){
            contract.payPA(result.getInt("cost"));
        }
    }

    /**
     * Analyzer of a Exploit action
     * @param result
     * @param ressourceExploited
     */
    public void analyzeExploitResult(JSONObject result, Biomes.Ressource ressourceExploited){
        if("OK".equals(result.getString("status"))) {
            contract.payPA(result.getInt("cost"));
            contract.stockResource(ressourceExploited, result.getJSONObject("extras").getInt("amount"));
        }
    }

    /**
     * Analyzer of a Scout action
     * @param result
     */
    public void analyzeScoutResult(JSONObject result){
        if("OK".equals(result.getString("status"))) {
            contract.payPA(result.getInt("cost"));
        }
    }

    /**
     * Analyzer of a Stop action
     * @param result
     */
    public void analyzeStopResult(JSONObject result){
        if("OK".equals(result.getString("status"))) {
            contract.payPA(result.getInt("cost"));
        }
    }

    /**
     * Analyzer of a Transform action
     * @param result
     */
    public void analyzeTransformResult(JSONObject result){
        if("OK".equals(result.getString("status"))) {
            contract.payPA(result.getInt("cost"));
            Craftables crafted = Craftables.valueOf(result.getJSONObject("extras").getString("kind"));
            contract.stockCraftable(crafted.getRecipe(), result.getJSONObject("extras").getInt("production"));
        }
    }
}