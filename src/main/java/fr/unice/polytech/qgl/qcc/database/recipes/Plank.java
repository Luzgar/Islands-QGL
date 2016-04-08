package fr.unice.polytech.qgl.qcc.database.recipes;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;

import java.util.Set;

/**
 * Created by user on 07/12/15.
 */
public class Plank extends Recipe {


    public Plank(){
        ingredients.put(Biomes.Ressource.WOOD, 1);
        qqtty = 4;
        name = "PLANK";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Plank plank = (Plank) o;

        return this.name.equals(plank.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
