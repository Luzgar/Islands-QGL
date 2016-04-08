package fr.unice.polytech.qgl.qcc.database.recipes;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Kevin on 02/01/2016.
 */
public class RecipeTest {
    @Test
    public void testEquality() throws Exception {
        Recipe recipe1 = new Glass();
        Recipe recipe2 = new Glass();
        Recipe falseEqualRecipe = new Plank();
        assertEquals(recipe1, recipe2);
        assertEquals(recipe1, recipe1);
        assertNotEquals(recipe1, falseEqualRecipe);
        assertEquals(recipe1.hashCode(), recipe2.hashCode());


        recipe1 = new Ingot();
        recipe2 = new Ingot();
        assertEquals(recipe1, recipe2);
        assertEquals(recipe1, recipe1);
        assertNotEquals(recipe1, falseEqualRecipe);
        assertEquals(recipe1.hashCode(), recipe2.hashCode());

        recipe1 = new Leather();
        recipe2 = new Leather();
        assertEquals(recipe1, recipe2);
        assertEquals(recipe1, recipe1);
        assertNotEquals(recipe1, falseEqualRecipe);
        assertEquals(recipe1.hashCode(), recipe2.hashCode());

        recipe1 = new Plank();
        recipe2 = new Plank();
        falseEqualRecipe = new Leather();
        assertEquals(recipe1, recipe1);
        assertNotEquals(recipe1, falseEqualRecipe);
        assertEquals(recipe1, recipe2);
        assertEquals(recipe1.hashCode(), recipe2.hashCode());

        recipe1 = new Rum();
        recipe2 = new Rum();
        assertEquals(recipe1, recipe2);
        assertEquals(recipe1, recipe1);
        assertNotEquals(recipe1, falseEqualRecipe);
        assertEquals(recipe1.hashCode(), recipe2.hashCode());
    }

    @Test
    public void testDosage(){
        Recipe recipe = new Glass();
        assertEquals(-1, recipe.getDosage(Biomes.Ressource.FLOWER));
    }
}