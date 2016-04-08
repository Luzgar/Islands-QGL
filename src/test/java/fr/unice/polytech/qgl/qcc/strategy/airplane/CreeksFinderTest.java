package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.GameEngine;
import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 16/11/15.
 */
public class CreeksFinderTest extends TestCase {
    GameEngine gameEngine;

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

        CreeksFinder Cf = new CreeksFinder(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());

        JSONObject actionTaken;
        JSONObject expectedAction;
        JSONObject result = new JSONObject();

        actionTaken = Cf.takeDecision(result);

        assertEquals("echo", actionTaken.getString("action"));

        result = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 4, \"found\": \"GROUND\" }, \"status\": \"OK\" }");

        actionTaken = Cf.takeDecision(result);
        assertEquals("fly", actionTaken.getString("action"));

        for(int i = 0; i < 2; ++i) { // On scan 2 cases avant le bord
            result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
            actionTaken = Cf.takeDecision(result);
            assertEquals("fly", actionTaken.getString("action"));
        }

        actionTaken = Cf.takeDecision(result);
        assertEquals("scan", actionTaken.getString("action"));

        result = new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}");
        actionTaken = Cf.takeDecision(result);
        assertEquals("fly", actionTaken.getString("action"));

        result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        actionTaken = Cf.takeDecision(result);
        assertEquals("scan", actionTaken.getString("action"));

        result = new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\", \"OCEAN\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}");
        actionTaken = Cf.takeDecision(result);
        assertEquals("fly", actionTaken.getString("action"));

        result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        actionTaken = Cf.takeDecision(result);
        assertEquals("scan", actionTaken.getString("action"));


        result = new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"OCEAN\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}");
        actionTaken = Cf.takeDecision(result);
        assertEquals("echo", actionTaken.getString("action"));

        result = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 4, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        actionTaken = Cf.takeDecision(result);

        assertEquals("echo", actionTaken.getString("action"));
        result = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 4, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        actionTaken = Cf.takeDecision(result);

        assertEquals("heading", actionTaken.getString("action"));
        result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        actionTaken = Cf.takeDecision(result);

        assertEquals("heading", actionTaken.getString("action"));
        result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");

        actionTaken = Cf.takeDecision(result);
        assertEquals("echo", actionTaken.getString("action"));
        result = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 4, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");

        actionTaken = Cf.takeDecision(result);
        assertEquals("fly", actionTaken.getString("action"));
        assertTrue(Cf.changedStep());




        //assertTrue(Cf.haveCrossIsland());

    }
}