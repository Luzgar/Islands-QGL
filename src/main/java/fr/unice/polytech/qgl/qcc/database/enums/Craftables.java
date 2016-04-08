package fr.unice.polytech.qgl.qcc.database.enums;

import fr.unice.polytech.qgl.qcc.database.recipes.*;

/**
 * Created by user on 13/12/15.
 */
public enum Craftables {
    GLASS(new Glass()),
    INGOT(new Ingot()),
    LEATHER(new Leather()),
    PLANK(new Plank()),
    RUM(new Rum());

    private transient Recipe recipe;

    Craftables(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public static Craftables getCraftableByString(String recipe){
        switch(recipe){
            case "GLASS":
                return GLASS;
            case "INGOT":
                return INGOT;
            case "LEATHER":
                return LEATHER;
            case "PLANK":
                return PLANK;
            case "RUM":
                return RUM;
            default:
                return null;
        }
    }
}
