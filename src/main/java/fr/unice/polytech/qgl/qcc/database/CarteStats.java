package fr.unice.polytech.qgl.qcc.database;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.recipes.Recipe;

import java.util.*;

/**
 * Created by Kevin on 29/12/2015.
 */
public class CarteStats {

    private Carte map;
    private Contract contract;
    private List<Biomes.Ressource> listRessources = new ArrayList<>();
    private Map<Biomes.Ressource, Integer> nbOfBiomes = new HashMap<>();
    private Map<Biomes.Ressource, Integer> nbRessourceAverage = new HashMap<>();


    public CarteStats (Carte map, Contract contract){
        this.map = map;
        this.contract = contract;

        for(Biomes.Ressource r : contract.getResources()) {
            nbRessourceAverage.put(r, 0);
        }
    }

    /**
     * Add a scanned biome to the list to make statistics
     * @param biome
     */
    public void addBiomes(Biomes biome){
        for(Biomes.Ressource r : contract.getResources()){
            if(Biomes.checkRessource(biome, r)){
                addRessourceToMap(r);
            }
        }
    }

    /**
     * Add resource to a map of a scanned zone
     * @param ressource
     */
    private void addRessourceToMap(Biomes.Ressource ressource){
        int amount = nbRessourceAverage.get(ressource);
        amount+=Biomes.Ressource.minimalAverageRessource(ressource);
        nbRessourceAverage.put(ressource, amount);
    }

    /**
     * Know if the contract can be made or not at a specific time
     * @return
     */
    public boolean canContractBeMade(){
        int expectedAmount;
        double actualAmount;
        for(Biomes.Ressource r : contract.getResources()) {
            actualAmount = nbRessourceAverage.get(r)*0.6;
            expectedAmount = contract.getAmount(r);
            if(actualAmount <= expectedAmount)
                return false;
        }
        return true;
    }

    /**
     * Sort the contract after landing
     */
    public void sortContract() {

        for(Biomes.Ressource r : contract.getResources()) {
            listRessources.add(r);
            nbOfBiomes.put(r, 0);
        }

        foundRessourcesOccurence();

        LinkedHashMap<Biomes.Ressource, Integer> newContract = sortFoundedRessources();

        contract.setContract(newContract);
    }

    /**
     * Get the occurence of all resources
     */
    private void foundRessourcesOccurence(){
        Case currentCase;
        for(int i = 0; i < map.getListCases().size(); ++i){
            currentCase = map.getListCases().get(i);
            for(int j = 0; j  < currentCase.getBiomes().length; ++j){
                for(int k = 0; k < listRessources.size(); ++k){
                    if(Biomes.checkRessource(Biomes.valueOf(currentCase.getBiomes()[j]), listRessources.get(k))){
                        int nbOfThisBiome = nbOfBiomes.get(listRessources.get(k));
                        nbOfBiomes.put(listRessources.get(k), nbOfThisBiome+1);
                    }
                }
            }
        }

        for(int i = 0; i < listRessources.size(); ++i){
            int amount = nbOfBiomes.get(listRessources.get(i));
            amount *= Biomes.Ressource.minimalAverageRessource(listRessources.get(i));
            nbOfBiomes.put(listRessources.get(i), amount);
        }
    }

    /**
     * Sort the founded resources
     * @return
     */
    private LinkedHashMap<Biomes.Ressource, Integer> sortFoundedRessources(){
        LinkedHashMap<Biomes.Ressource, Integer> newContract = new LinkedHashMap<>();
        double tmp, cost;
        Biomes.Ressource ressourceMax = null;
        for(int i = 0; i < listRessources.size(); ++i){
            tmp = 0;
            cost = 100000;
            for(Biomes.Ressource r : nbOfBiomes.keySet()) {
                /*if(tmp < (double) (nbOfBiomes.get(r)*Biomes.Ressource.minimalAverageRessource(r))/(double) contract.getAmount(r)) {
                    tmp = nbOfBiomes.get(r)/contract.getAmount(r);
                    cost = contract.getAmount(r)*4;
                    ressourceMax = r;
                }*/
                if(cost > ((double)contract.getAmount(r)/ (double)Biomes.Ressource.minimalAverageRessource(r))*4){
                    cost = ((double)contract.getAmount(r)/ (double)Biomes.Ressource.minimalAverageRessource(r))*4;
                    ressourceMax = r;
                }
            }
            if(ressourceMax != null) {
                nbOfBiomes.remove(ressourceMax);
                newContract.put(ressourceMax, contract.getAmount(ressourceMax));
            }
        }
        return newContract;
    }
}
