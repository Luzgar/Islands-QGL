package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.database.Carte;
import fr.unice.polytech.qgl.qcc.database.Case;
import fr.unice.polytech.qgl.qcc.database.Contract;
import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.enums.Craftables;
import fr.unice.polytech.qgl.qcc.database.recipes.Recipe;
import fr.unice.polytech.qgl.qcc.strategy.actions.*;
import fr.unice.polytech.qgl.qcc.strategy.Strategy;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 21/11/2015.
 */
public class ExploreIsland implements Strategy {
    boolean changedStep = false;
    private Carte carte;
    private Contract contract;
    private Strategy actualStrategy;
    private List<Biomes.Ressource> listRessources = new ArrayList<>();
    private Map<Biomes.Ressource, Integer> objectifs = new HashMap<>();
    private boolean isCoordNull = true;
    int ressourceToExploit = 0;
    private boolean moving = true;
    private boolean firstCall = true;


    public ExploreIsland(Carte carte, Contract contract){
        this.carte = carte;
        this.contract = contract;
        for(Biomes.Ressource res : contract.getObjectifs().keySet()){
            objectifs.put(res, contract.getObjectifs().get(res));
        }
        for(Biomes.Ressource res : contract.getResources()){
            listRessources.add(res);
        }

        for(int i = 0; i < listRessources.size(); ++i) {
            int coord[] = getNextCoord(Biomes.checkBiomes(listRessources.get(ressourceToExploit)));
            if (coord != null){
                isCoordNull = false;
                actualStrategy = new MoveToCoord(carte, coord[0], coord[1]);
                break;
            }
            ++ressourceToExploit;
        }
    }

    /**
     * Main method for exploring the island. Decide when to move and explore/exploit
     * @param lastActionResult
     * @return
     */
    @Override
    public JSONObject takeDecision(JSONObject lastActionResult) {
        Craftables craftables = getMakeAbleCraftable();
        if(craftables != null && contract.getPa() > 500){
            return new TransformRessources(contract, craftables).takeDecision(lastActionResult);
        }

        if(contract.getContract().get(listRessources.get(ressourceToExploit)) < 0){
            ressourceToExploit++;
            while (ressourceToExploit < contract.getContract().size() && !contract.doNextContract(carte, listRessources.get(ressourceToExploit)))
            {
                ++ressourceToExploit;
            }
        }

        if((firstCall && isCoordNull) || contract.getPa() < 300 || ressourceToExploit >= listRessources.size()){
            return new Stop().act();
        }

        firstCall = false;

        if(actualStrategy.changedStep()){
            if(moving){
                actualStrategy = new ExploreArea(carte, contract, Biomes.OCEAN, listRessources.get(ressourceToExploit));
                if(carte.getCase(carte.getCurrentX(), carte.getCurrentY()) != null)
                    carte.getCase(carte.getCurrentX(), carte.getCurrentY()).setRessourcesExploited(listRessources.get(ressourceToExploit));
                moving = false;
            }
            else {
                for(int i = ressourceToExploit; i < listRessources.size(); ++i) {
                    int[] coord = getNextCoord(Biomes.checkBiomes(listRessources.get(ressourceToExploit)));
                    if (coord != null) {
                        actualStrategy = new MoveToCoord(carte, coord[0], coord[1]);
                        moving = true;
                        break;
                    }
                    ++ressourceToExploit;
                }
                if(!moving)
                    return new Stop().act();
            }
        }
        return actualStrategy.takeDecision(lastActionResult);
    }


    @Override
    public boolean changedStep() {
        return changedStep;
    }

    /**
     * Get next coord to move
     * @param biomesList
     * @return
     */
    private int[] getNextCoord(List<Biomes> biomesList){
        int finalCoord[] = new int[2];
        int tmpCoord[] = new int[2];
        boolean firstLoop = true;
        for(int i = 0; i < biomesList.size(); ++i){
            tmpCoord = getNearBiome(biomesList.get(i));
            if(tmpCoord != null){
                if(firstLoop){
                    finalCoord[0] = tmpCoord[0];
                    finalCoord[1] = tmpCoord[1];
                    firstLoop = false;
                }
                else {
                    if(isCloser(finalCoord[0], finalCoord[1], tmpCoord[0], tmpCoord[1])){
                        finalCoord[0] = tmpCoord[0];
                        finalCoord[1] = tmpCoord[1];
                    }
                }
            }
        }
        if(finalCoord[0] == 0 && finalCoord[1] == 0)
            return null;
        return finalCoord;
    }

    /**
     * Get nearest researched biome coordinate
     * @param biomes
     * @return
     */
    private int[] getNearBiome(Biomes biomes){
        int coord[] = new int[2];
        boolean firstCase = true;
        Case cases;
        int x,y;
        for(int i = 0; i < carte.getListCases().size(); ++i){
            cases = carte.getListCases().get(i);
            if(cases.getBiomes().length == 1 && !cases.hasRessourceBeenExploited(listRessources.get(ressourceToExploit)) && cases.getBiomes()[0].equals(biomes.toString())){
                x= cases.getX();
                y = cases.getY();
                if(firstCase){
                    firstCase = false;
                    coord[0] = x;
                    coord[1] = y;
                }
                else {
                    if(isCloser(coord[0], coord[1], x, y)){
                        coord[0] = x;
                        coord[1] = y;
                    }
                }
            }
        }
        if(coord[0] == 0 && coord[1] == 0)
            return null;
        return coord;
    }

    /**
     * Mathematic method to decid if a case is closer than another
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private boolean isCloser(int x1, int y1, int x2, int y2){
        int actualDistance = Math.abs(carte.getCurrentX() - x1) + Math.abs(carte.getCurrentY() - y1);
        int newDistance = Math.abs(carte.getCurrentX() - x2) + Math.abs(carte.getCurrentY() - y2);

        return newDistance < actualDistance;
    }

    /**
     * Get a craft contract that can be made
     * @return
     */
    public Craftables getMakeAbleCraftable(){
        try {
            int amount, dosage;
            boolean canBeMade = true;
            for (Recipe r : contract.getCraftablesRecipe()) {
                if (!contract.haveCraftAlreadyBeMade(r)) {
                    amount = contract.getCraftable().get(r);
                    canBeMade = true;
                    for (Biomes.Ressource res : r.getIngredients()) {
                        dosage = (amount * r.getDosage(res)) / r.getQuantity();
                        dosage += dosage * r.getFailRatio();
                        if(objectifs.containsKey(res) && contract.getContract().containsKey(res)) {
                            if (objectifs.get(res) - contract.getContract().get(res) < dosage)
                                canBeMade = false;
                        }
                    }
                    if (canBeMade) {
                        return Craftables.getCraftableByString(r.getName());
                    }
                }
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
}