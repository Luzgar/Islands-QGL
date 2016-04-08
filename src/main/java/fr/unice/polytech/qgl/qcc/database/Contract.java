package fr.unice.polytech.qgl.qcc.database;

import fr.unice.polytech.qgl.qcc.database.recipes.Recipe;
import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.enums.Craftables;
import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.exceptions.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by user on 30/11/15.
 */
public class Contract {
    private int pa; // Points d'action
    private int initialPA;
    private int nbrOfMen;//Effectifs disponibles à l'exploration
    private int landedMen;
    /**
     *  Contract: contient les ressources primaires restantes à exploiter. Il evolue au cours de l'exploration
     */
    private Map<Biomes.Ressource, Integer> contract = new LinkedHashMap<>(); //contrat à remplir
    /**
     *  Objectifs: contient les ressources primaires et les quantités demandées à l'initialisation + les ressources primaires necessaires pour crafter
     */
    private Map<Biomes.Ressource, Integer> objectifs = new LinkedHashMap<>();//Objectifs initiaux
    /**
     *  Craftable: contient les ressources secondaires à ramasser et la quantité DEJA fabriquée
     */
    private Map<Recipe,Integer> craftable = new LinkedHashMap<>();//Contrat des ressources secondaires
    private ArrayList<Recipe> craftableMade = new ArrayList<>();
    private Direction firstDirection;
    private JSONArray contractList;

    public Contract(JSONObject context) throws Exception {
        try
        {
            pa = context.getInt("budget");
            initialPA = pa;
        }
        catch (Exception e)
        {
            throw new NoBudgetException();
        }
        try
        {
            nbrOfMen = context.getInt("men");
        }
        catch (Exception e)
        {
            throw new NoMenException();
        }
        try
        {
            firstDirection = Direction.valueOf(context.getString("heading"));
        }
        catch (Exception e)
        {
            throw new NoHeadingException();
        }
        try {
            contractList = context.getJSONArray("contracts");
        }
        catch (Exception e){
            throw new NoContractException();
        }
        //On initialise nos objets contrats pour la gestion en interne
        InitiateContractList();

    }

    /**
     * Initialize the contract
     * @throws UnknownResourceException
     */
    private void InitiateContractList() throws UnknownResourceException
    {
        int presentNbResource;
        for(int i =0; i< contractList.length(); i++){
            presentNbResource = 0;
            String res= contractList.getJSONObject(i).getString("resource");
            int amount = contractList.getJSONObject(i).getInt("amount");
            if (amount > 0) {
                if (Biomes.Ressource.contains(res)) {
                    Biomes.Ressource r = Biomes.Ressource.valueOf(contractList.getJSONObject(i).getString("resource"));
                    if(contract.containsKey(r))
                        presentNbResource = contract.get(r);
                    contract.put(r, amount+presentNbResource);
                    objectifs.put(r, amount+presentNbResource);
                } else { // Pas une ressource primaire -> voir si c'est un craftable
                    try {
                        Recipe craft = Craftables.valueOf(res).getRecipe();
                        craftable.put(craft, amount);
                        Set<Biomes.Ressource> ingr = craft.getIngredients();

                        completePrimaryDosages(ingr,amount, craft);
                    } catch (IllegalArgumentException e) // Une des ressources du contrat n'existe pas
                    {
                        throw new UnknownResourceException();
                    }
                }
            }
        }
    }


    /**
     * If contract is a craft, complete the contract with the primary resources who are composing the craft contract
     * @param ingr
     * @param amount
     * @param craft
     */
     private void completePrimaryDosages(Set<Biomes.Ressource> ingr, int amount, Recipe craft){
         int dosage;
         for (Biomes.Ressource r : ingr) {
             dosage = (craft.getDosage(r) * amount)/craft.getQuantity();
             dosage += dosage*craft.getFailRatio();
             if (contract.containsKey(r)) {
                 contract.put(r, dosage + contract.get(r));
                 objectifs.put(r, dosage + objectifs.get(r));
             } else {
                 contract.put(r, dosage);
                 objectifs.put(r, dosage);
             }
         }
     }

    /**
     * Get resources of the contract
     * @return
     */
    public Set<Biomes.Ressource> getResources(){
        return contract.keySet();
    }

    /**
     * Get amount of the exploit for a resource
     * @param ressource
     * @return
     */
    public int getAmount(Biomes.Ressource ressource){
        return contract.get(ressource);
    }

    /**
     * Get remaining PA
     * @return
     */
    public int getPa(){
        return pa;
    }

    /**
     * Get number of men
     * @return
     */
    public int getNbrOfMen(){
        return nbrOfMen;
    }

    /**
     * Get number of landed men
     * @return
     */
    public int getLandedMen(){
        return landedMen;
    }

    /**
     * Set the number of landed men
     * @param lm
     */
    public void setLandedMen(int lm){
        landedMen = lm;
    }

    /**
     * Reduce the number of PA
     * @param cost
     */
    public void payPA(int cost) {
        pa -= cost;
    }

    /**
     * Get first direction of the plane
     * @return
     */
    public Direction getFirstDirection(){
        return firstDirection;
    }

    /**
     * Returns -1 if the resource isn't part of the objectives, quantity harvested otherwise
     * @param ressource
     * @return the number of a given resource that we got
     */
    public int getHarvested(Biomes.Ressource ressource){
        if(objectifs.containsKey(ressource))
            return objectifs.get(ressource) - contract.get(ressource);
        else
            return -1;
    }
    /**
     * Returns -1 if the resource isn't part of the objectives, quantity left to harvest otherwise
     * /!\ DIFFERENT USE FROM THE ONE WITH RESOURCES AS PARAM  /!\
     * @param recipe
     * @return the number of a given craft yet to get
     */
    public int getHarvested(Recipe recipe){
        if(craftable.containsKey(recipe))
            return craftable.get(recipe);
        else
            return -1;
    }

    /**
     * Craft a resources
     * @param recipe
     * @return
     */
    public boolean craft(Recipe recipe){
        Set<Biomes.Ressource> set = recipe.getIngredients();
        boolean b = true;
        for(Biomes.Ressource r : set){
            if(recipe.getDosage(r)> getHarvested(r))
                b = false;
        }
        if(b) {
            useResource(recipe);
            stockCraftable(recipe, recipe.getQuantity());
        }
        return b;
    }

    /**
     * Stock the exploited resources
     * @param ressource
     * @param qqtty
     */
    public void stockResource(Biomes.Ressource ressource, int qqtty){
        int amount = contract.get(ressource);
        contract.put(ressource, amount - qqtty);
    }

    /**
     * Use the exploited resources (for craft)
     * @param recipe
     */
    private void useResource(Recipe recipe){
        Set<Biomes.Ressource> ingredients = recipe.getIngredients();
        for(Biomes.Ressource r : ingredients){
            int amount = contract.get(r);
            contract.put(r, amount + recipe.getDosage(r));//TODO: Plus??
        }
    }

    /**
     * Stock the crafted resources
     * @param recipe
     * @param qtty
     */
    public void stockCraftable(Recipe recipe, int qtty){
        int amount = craftable.get(recipe);
        craftable.put(recipe, amount-qtty);
        craftable.remove(recipe);
        craftableMade.add(recipe);
    }

    /**
     * Get the recipe by a String of the contract
     * @param res
     * @return
     */
    public Recipe getCorrectRecipe(String res){
        return Craftables.valueOf(res).getRecipe();
    }

    /**
     * Get the full contract
     * @return
     */
    public Map<Biomes.Ressource,Integer> getContract() //for test purposes
    {
        return contract;
    }

    /**
     * Get the full craft map
     * @return
     */
    public Map<Recipe,Integer> getCraftable() //same ^
    {
        return craftable;
    }

    /**
     * Decide if the next contract can be done
     * @param map
     * @param resourceToExploit
     * @return
     */
    public boolean doNextContract(Carte map, Biomes.Ressource resourceToExploit)
    {
        int nbToCollect = contract.get(resourceToExploit);
        int averageCost = 9;
        int averageExploit = Biomes.Ressource.minimalAverageRessource(resourceToExploit);
        int moveCost = 4;
        int posX = map.getCurrentX();
        int posY = map.getCurrentY();
        Case current = new Case(posX,posY);
        List<Case> newList = selectCasesByResource(map.getListCases(), resourceToExploit);
        Case closestCase = closestCaseOf(newList,current);
        int distance = current.distanceTo(closestCase);
        int paNeeded = (nbToCollect/averageExploit)*averageCost + (nbToCollect/averageExploit)*moveCost + distance*moveCost;
        if (pa < paNeeded)
            return false;

        // 2nd test on the known biomes
        int numberOfBiomes = numberOfBiomes(map, resourceToExploit);
        if (nbToCollect > numberOfBiomes*averageExploit) //not enough known biomes
            return false;

        // Contract can likely be completed
        return true;
    }

    /**
     * Fill a map with all present resources on the map
     * @param list
     * @param r
     * @return
     */
    public List<Case> selectCasesByResource(List<Case> list, Biomes.Ressource r)
    {
        List<Case> outList = new ArrayList<>();
        for (Case c : list)
        {
            if (Biomes.checkRessource(Biomes.valueOf(c.getBiomes()[0]),r)) // si la ressource est présente sur la case, on l'ajoute a la outList
            {
                outList.add(c);
            }
        }
        return outList;
    }

    /**
     * Get the closest case
     * @param list
     * @param cur
     * @return
     */
    public Case closestCaseOf(List<Case> list, Case cur)
    {
        Case closest = new Case();
        int distance = -1;
        for (Case c : list)
        {
            if ((distance == -1 || closest.distanceTo(cur) > c.distanceTo(cur)) && !cur.equals(c))
            {
                closest = c;
                distance = c.distanceTo(cur);
            }
        }
        return closest;
    }

    /**
     * Get the number of biome for one resources on the map
     * @param map
     * @param r
     * @return
     */
    public int numberOfBiomes(Carte map, Biomes.Ressource r)
    {
        int nbOfThisBiome = 0;
        Case currentCase;
        for(int i = 0; i < map.getListCases().size(); ++i){
            currentCase = map.getListCases().get(i);
            for(int j = 0; j  < currentCase.getBiomes().length; ++j){
                if(Biomes.checkRessource(Biomes.valueOf(currentCase.getBiomes()[j]), r)){
                    nbOfThisBiome++;
                }
            }
        }
        return nbOfThisBiome;
    }

    /**
     * Get all the recipe of all craft contract
     * @return
     */
    public Set<Recipe> getCraftablesRecipe(){
        return craftable.keySet();
    }

    public void setContract(LinkedHashMap<Biomes.Ressource, Integer> newContract){
        this.contract = newContract;
        this.objectifs = newContract;
    }

    /**
     * Get initial number of PA
     * @return
     */
    public int getInitialPA(){
        return initialPA;
    }

    public Map<Biomes.Ressource, Integer> getObjectifs(){
        return objectifs;
    }

    /**
     * Method who return if the craft contract have already be done
     * @param r
     * @return
     */
    public boolean haveCraftAlreadyBeMade(Recipe r){
        if(craftableMade.contains(r))
            return true;
        return false;
    }
}
