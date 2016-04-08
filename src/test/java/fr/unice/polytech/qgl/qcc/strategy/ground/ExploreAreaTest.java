package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.GameEngine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 28/12/2015.
 */
public class ExploreAreaTest {

    private GameEngine gameEngine;

    @Test
    public void testTakeDecision() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        String[] ressource = {"TEMPERATE_DECIDUOUS_FOREST"};
        gameEngine.getCarte().addBigCase(0, 0, new JSONArray(ressource));
        ExploreArea EA = new ExploreArea(gameEngine.getCarte(), gameEngine.getContract(), Biomes.TEMPERATE_DECIDUOUS_FOREST, Biomes.Ressource.WOOD);

        JSONObject actionResult = EA.takeDecision(new JSONObject());

        assertEquals("explore", actionResult.getString("action"));
        assertFalse(EA.changedStep());

        JSONObject result = new JSONObject("{\n" +
                "  \"cost\": 5,\n" +
                "  \"extras\": {\n" +
                "    \"resources\": [\n" +
                "      { \"amount\": \"HIGH\", \"resource\": \"WOOD\", \"cond\": \"FAIR\" }\n" +
                "    ],\n" +
                "    \"pois\": [ \"creek-id\" ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}");
        actionResult = EA.takeDecision(result);

        assertEquals("exploit", actionResult.getString("action"));

        assertTrue(EA.changedStep());

        EA = new ExploreArea(gameEngine.getCarte(), gameEngine.getContract(), Biomes.TEMPERATE_DECIDUOUS_FOREST, Biomes.Ressource.WOOD);

        actionResult = EA.takeDecision(new JSONObject());

        assertEquals("explore", actionResult.getString("action"));
        assertFalse(EA.changedStep());

        result = new JSONObject("{\n" +
                "  \"cost\": 5,\n" +
                "  \"extras\": {\n" +
                "    \"resources\": [\n" +
                "      { \"amount\": \"HIGH\", \"resource\": \"SUGAR_CANE\", \"cond\": \"FAIR\" }\n" +
                "    ],\n" +
                "    \"pois\": [ \"creek-id\" ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}");
        actionResult = EA.takeDecision(result);
        assertEquals("scout", actionResult.getString("action"));
        assertTrue(EA.changedStep());
    }
}