package fr.unice.polytech.qgl.qcc.database.enums;

import fr.unice.polytech.qgl.qcc.database.recipes.Glass;
import fr.unice.polytech.qgl.qcc.database.recipes.Ingot;
import fr.unice.polytech.qgl.qcc.database.recipes.Leather;
import fr.unice.polytech.qgl.qcc.database.recipes.Plank;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Unreal Swing
 */
public class CraftablesTest {

    @Test
    public void getCraftableByString() throws Exception {
        assertEquals(Craftables.GLASS, Craftables.getCraftableByString("GLASS"));
        assertEquals(Craftables.PLANK, Craftables.getCraftableByString("PLANK"));
        assertEquals(Craftables.INGOT, Craftables.getCraftableByString("INGOT"));
        assertEquals(Craftables.LEATHER, Craftables.getCraftableByString("LEATHER"));
        assertEquals(Craftables.RUM, Craftables.getCraftableByString("RUM"));
    }
}