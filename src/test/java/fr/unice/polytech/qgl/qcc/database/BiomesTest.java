package fr.unice.polytech.qgl.qcc.database;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 23/11/15.
 */
public class BiomesTest extends TestCase {

    @Test
    public void testCheckRessource() throws Exception {
        Biomes.fill();
        assertTrue(Biomes.checkRessource(Biomes.OCEAN, Biomes.Ressource.FISH));
        assertFalse(Biomes.checkRessource(Biomes.OCEAN, Biomes.Ressource.WOOD));
    }

    @Test
    public void testCheckBiomes() throws Exception {
        Biomes.fill();
        List<Biomes> list =  Biomes.checkBiomes(Biomes.Ressource.FISH);
        assertTrue(list.contains(Biomes.OCEAN));
        assertFalse(list.contains(Biomes.SNOW));
    }
}