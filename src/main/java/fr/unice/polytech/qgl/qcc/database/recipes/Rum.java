package fr.unice.polytech.qgl.qcc.database.recipes;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;

import java.util.Set;

/**
 * Created by user on 07/12/15.
 */
public class Rum extends Recipe {


    public Rum(){
        ingredients.put(Biomes.Ressource.SUGAR_CANE, 10);
        ingredients.put(Biomes.Ressource.FRUITS, 1);
        name = "RUM";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Rum rum = (Rum) o;

        return this.name.equals(rum.name);

    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
