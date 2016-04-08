package fr.unice.polytech.qgl.qcc.database.recipes;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 07/12/15.
 */
public class Recipe {
    protected Map<Biomes.Ressource, Integer> ingredients = new HashMap<Biomes.Ressource, Integer>(); //Liste des ingredients
    protected int qqtty = 1;//Quantity crafted
    protected String name;
    protected double failRatio = 0.15;

    public int getQuantity(){
        return qqtty;
    }

    public int getDosage(Biomes.Ressource r){
        if(ingredients.containsKey(r))
            return ingredients.get(r);
        else
            return -1;
    }

    public Set<Biomes.Ressource> getIngredients(){
        return ingredients.keySet();
    }

    public String getName(){
        return name;
    }

    public double getFailRatio()
    {
        return failRatio;
    }
}
