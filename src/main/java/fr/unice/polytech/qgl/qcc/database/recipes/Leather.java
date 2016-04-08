package fr.unice.polytech.qgl.qcc.database.recipes;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;

import java.util.Set;

/**
 * Created by user on 07/12/15.
 */
public class Leather extends Recipe{

    public Leather(){
        ingredients.put(Biomes.Ressource.FUR, 3);
        name = "LEATHER";
        failRatio = 0.2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Leather leather = (Leather) o;

        return this.name.equals(leather.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
