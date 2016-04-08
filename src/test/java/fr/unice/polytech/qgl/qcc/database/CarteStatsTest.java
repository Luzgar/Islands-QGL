package fr.unice.polytech.qgl.qcc.database;

import eu.ace_design.island.stdlib.Resources;
import fr.unice.polytech.qgl.qcc.GameEngine;
import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Created by renaud on 01/02/2016.
 */
public class CarteStatsTest {

    private GameEngine gameEngine;
    @Test
    public void testSortContract() throws Exception {
        ArrayList<Biomes.Ressource> listRessources = new ArrayList<>();

        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 6000, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 5, \"resource\": \"FLOWER\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        String[] ressources1 = {"TEMPERATE_DECIDUOUS_FOREST"};
        JSONArray ressources1Json = new JSONArray(ressources1);
        gameEngine.getCarte().addBigCase(0, 0, ressources1Json);

        String[] ressources2 = {"TEMPERATE_DECIDUOUS_FOREST"};
        JSONArray ressources2Json = new JSONArray(ressources2);
        gameEngine.getCarte().addBigCase(10, 10, ressources2Json);

        String[] ressources3 = {"MANGROVE"};
        JSONArray ressources3Json = new JSONArray(ressources3);
        gameEngine.getCarte().addBigCase(20, 20, ressources3Json);

        CarteStats carteStats = new CarteStats(gameEngine.getCarte(), gameEngine.getContract());
        carteStats.sortContract();

        for(Biomes.Ressource r : gameEngine.getContract().getResources())
            listRessources.add(r);

        assertEquals(Biomes.Ressource.FLOWER,listRessources.get(0));
        assertEquals(Biomes.Ressource.WOOD,listRessources.get(1));

        /******************************************************************************
         *                              SCENARIO 2                                    *
         ******************************************************************************/

        listRessources = new ArrayList<>();
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 60, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 500, \"resource\": \"FLOWER\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        String[] ressources4 = {"TEMPERATE_DECIDUOUS_FOREST"};
        ressources1Json = new JSONArray(ressources4);
        gameEngine.getCarte().addBigCase(0, 0, ressources1Json);

        String[] ressources5 = {"TEMPERATE_DECIDUOUS_FOREST"};
        ressources2Json = new JSONArray(ressources5);
        gameEngine.getCarte().addBigCase(10, 10, ressources2Json);

        String[] ressources6 = {"MANGROVE"};
        ressources3Json = new JSONArray(ressources6);
        gameEngine.getCarte().addBigCase(20, 20, ressources3Json);

        carteStats = new CarteStats(gameEngine.getCarte(), gameEngine.getContract());
        carteStats.sortContract();

        for(Biomes.Ressource r : gameEngine.getContract().getResources())
            listRessources.add(r);

        assertEquals(Biomes.Ressource.FLOWER,listRessources.get(1));
        assertEquals(Biomes.Ressource.WOOD,listRessources.get(0));

    }


    @Test
    public void testCanContractBeMade() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 80, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 2, \"resource\": \"FLOWER\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        /*gameEngine.getCarteStats().addBiomes(Biomes.TEMPERATE_DECIDUOUS_FOREST);
        gameEngine.getCarteStats().addBiomes(Biomes.TEMPERATE_DECIDUOUS_FOREST);
        gameEngine.getCarteStats().addBiomes(Biomes.MANGROVE);

        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.WOOD), new Integer(60));
        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.FLOWER), new Integer(1));
        assertFalse(gameEngine.getCarteStats().canContractBeMade());

        gameEngine.getCarteStats().addBiomes(Biomes.BEACH);

        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.WOOD), new Integer(60));
        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.FLOWER), new Integer(1));
        assertFalse(gameEngine.getCarteStats().canContractBeMade());

        gameEngine.getCarteStats().addBiomes(Biomes.TROPICAL_RAIN_FOREST);
        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.WOOD), new Integer(80));
        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.FLOWER), new Integer(1));
        assertFalse(gameEngine.getCarteStats().canContractBeMade());

        gameEngine.getCarteStats().addBiomes(Biomes.MANGROVE);
        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.WOOD), new Integer(100));
        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.FLOWER), new Integer(2));
        assertFalse(gameEngine.getCarteStats().canContractBeMade());

        gameEngine.getCarteStats().addBiomes(Biomes.TROPICAL_RAIN_FOREST);
        gameEngine.getCarteStats().addBiomes(Biomes.TROPICAL_RAIN_FOREST);
        gameEngine.getCarteStats().addBiomes(Biomes.TROPICAL_RAIN_FOREST);
        gameEngine.getCarteStats().addBiomes(Biomes.TROPICAL_RAIN_FOREST);
        gameEngine.getCarteStats().addBiomes(Biomes.TROPICAL_RAIN_FOREST);
        gameEngine.getCarteStats().addBiomes(Biomes.TROPICAL_RAIN_FOREST);

        gameEngine.getCarteStats().addBiomes(Biomes.MANGROVE);
        gameEngine.getCarteStats().addBiomes(Biomes.MANGROVE);
        gameEngine.getCarteStats().addBiomes(Biomes.MANGROVE);
        gameEngine.getCarteStats().addBiomes(Biomes.MANGROVE);

        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.WOOD), new Integer(300));
        assertEquals(gameEngine.getCarteStats().getRessourceAverage().get(Biomes.Ressource.FLOWER), new Integer(6));
        assertTrue(gameEngine.getCarteStats().canContractBeMade());*/

    }
}