package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.GameEngine;
import fr.unice.polytech.qgl.qcc.database.enums.Craftables;
import fr.unice.polytech.qgl.qcc.database.recipes.Plank;
import fr.unice.polytech.qgl.qcc.strategy.actions.Action;
import fr.unice.polytech.qgl.qcc.strategy.actions.Transform;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 13/02/2016.
 */
public class TransformRessourcesTest {

    private GameEngine gameEngine;

    @Test
    public void testTakeDecision() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"PLANK\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        TransformRessources TR = new TransformRessources(gameEngine.getContract(), Craftables.PLANK);

        JSONObject actualResult = TR.takeDecision(new JSONObject());
        JSONObject expectedResult = new JSONObject("{ \"action\": \"transform\", \"parameters\": { \"WOOD\": 57}}");

        assertEquals(expectedResult.getString("action"), actualResult.getString("action"));
        assertEquals(expectedResult.getJSONObject("parameters").getInt("WOOD"), actualResult.getJSONObject("parameters").getInt("WOOD"));

        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        TR = new TransformRessources(gameEngine.getContract(), Craftables.GLASS);

        actualResult = TR.takeDecision(new JSONObject());
        expectedResult = new JSONObject("{ \"action\": \"transform\", \"parameters\": { \"WOOD\": 1100, \"QUARTZ\": 2200 }}");

        assertEquals(expectedResult.getString("action"), actualResult.getString("action"));
        assertEquals(expectedResult.getJSONObject("parameters").getInt("WOOD"), actualResult.getJSONObject("parameters").getInt("WOOD"));
        assertEquals(expectedResult.getJSONObject("parameters").getInt("QUARTZ"), actualResult.getJSONObject("parameters").getInt("QUARTZ"));
    }
}