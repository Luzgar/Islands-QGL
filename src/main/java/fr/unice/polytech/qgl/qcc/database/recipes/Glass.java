package fr.unice.polytech.qgl.qcc.database.recipes;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;

import java.util.Set;

/**
 * Created by user on 07/12/15.
 */
public class Glass extends Recipe {

    public Glass(){
        failRatio = 0.1;
        ingredients.put(Biomes.Ressource.QUARTZ, 10);
        ingredients.put(Biomes.Ressource.WOOD, 5);
        name = "GLASS";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;

        Glass glass = (Glass) o;

        return this.name.equals(glass.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }


}
