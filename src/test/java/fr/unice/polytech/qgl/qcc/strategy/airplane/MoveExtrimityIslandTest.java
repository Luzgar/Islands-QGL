package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.GameEngine;
import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by user on 16/11/15.
 */
public class MoveExtrimityIslandTest extends TestCase {

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

        MoveExtrimityIsland Mei = new MoveExtrimityIsland(gameEngine.getCarte());
        Direction dir = gameEngine.getCarte().getPlaneDirection();

        JSONObject lastAction = Mei.takeDecision(new JSONObject());
        JSONObject expectedAction = new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \""+dir.anticlockwise().toString()+"\" } }");

        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));

        JSONObject actionResult = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        lastAction = Mei.takeDecision(actionResult);
        expectedAction = new JSONObject("{ \"action\": \"fly\" }");

        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));

        actionResult = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        lastAction = Mei.takeDecision(actionResult);
        expectedAction = new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \""+dir.anticlockwise().toString()+"\" } }");

        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));

        actionResult = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");
        lastAction = Mei.takeDecision(actionResult);
        expectedAction = new JSONObject("{ \"action\": \"fly\" }");

        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));

        actionResult = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        lastAction = Mei.takeDecision(actionResult);
        expectedAction = new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \""+dir.anticlockwise().toString()+"\" } }");

        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));

        actionResult = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        lastAction = Mei.takeDecision(actionResult);
        expectedAction = new JSONObject("{ \"action\": \"heading\", \"parameters\": { \"direction\": \""+dir.anticlockwise()+"\" } }");

        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));

        assertTrue(Mei.changedStep());


    }
}