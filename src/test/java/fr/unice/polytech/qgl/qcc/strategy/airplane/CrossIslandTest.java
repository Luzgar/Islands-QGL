package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.GameEngine;
import fr.unice.polytech.qgl.qcc.strategy.actions.Echo;
import fr.unice.polytech.qgl.qcc.strategy.actions.Fly;
import fr.unice.polytech.qgl.qcc.strategy.actions.Scan;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 08/12/2015.
 */
public class CrossIslandTest {

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

        CrossIsland Ci = new CrossIsland(gameEngine.getCarte());
        Direction dir = gameEngine.getCarte().getPlaneDirection();

        JSONObject lastAction = Ci.takeDecision(new JSONObject());
        JSONObject expectedAction = new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \""+dir.toString()+"\" } }");

        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));

        int range = 10;
        JSONObject actionResult = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\":"+range+", \"found\": \"GROUND\" }, \"status\": \"OK\" }");

        lastAction = Ci.takeDecision(actionResult);
        expectedAction = new JSONObject("{ \"action\": \"fly\" }");
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        range--;

        actionResult = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        while(range > 1){
            lastAction = Ci.takeDecision(actionResult);
            expectedAction = new JSONObject("{ \"action\": \"fly\" }");
            assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
            range--;
        }

        lastAction = Ci.takeDecision(actionResult);
        expectedAction = new Scan().act();
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));

        actionResult = new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"OCEAN\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}");

        lastAction = Ci.takeDecision(actionResult);
        expectedAction = new Fly().act();
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertFalse(Ci.changedStep());

        actionResult = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");

        lastAction = Ci.takeDecision(actionResult);
        expectedAction = new Scan().act();
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));

        actionResult = new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"GLACIER\", \"ALPINE\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}");

        lastAction = Ci.takeDecision(actionResult);
        expectedAction = new Fly().act();
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));

        actionResult = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");

        lastAction = Ci.takeDecision(actionResult);
        expectedAction = new Scan().act();
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));

        actionResult = new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"OCEAN\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}");

        lastAction = Ci.takeDecision(actionResult);
        expectedAction = new Echo(dir).act();
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));

        actionResult = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\":10, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");

        lastAction = Ci.takeDecision(actionResult);
        expectedAction = new Echo(gameEngine.getCarte().getPlaneDirection()).act();

        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));
        assertTrue(Ci.changedStep());


    }
}