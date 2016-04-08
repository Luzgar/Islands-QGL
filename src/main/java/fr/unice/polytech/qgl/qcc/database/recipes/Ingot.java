package fr.unice.polytech.qgl.qcc.database.recipes;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;

import java.util.Set;

/**
 * Created by user on 07/12/15.
 */
public class Ingot extends Recipe{


    public Ingot(){
        ingredients.put(Biomes.Ressource.ORE, 5);
        ingredients.put(Biomes.Ressource.WOOD, 5);
        name = "INGOT";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Ingot ingot = (Ingot) o;
        return this.name.equals(ingot.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
