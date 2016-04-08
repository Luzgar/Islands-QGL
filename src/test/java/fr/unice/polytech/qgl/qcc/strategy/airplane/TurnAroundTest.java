package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.GameEngine;
import fr.unice.polytech.qgl.qcc.strategy.actions.Heading;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 08/12/2015.
 */
public class TurnAroundTest {

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
        TurnAround Ta = new TurnAround(gameEngine.getCarte(), gameEngine.getContract().getFirstDirection());
        Direction dir = gameEngine.getCarte().getPlaneDirection();

        int range = 10;
        JSONObject actionResult = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": "+range+", \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");

        JSONObject lastAction = Ta.takeDecision(actionResult);
        JSONObject expectedAction = new Heading(gameEngine.getContract().getFirstDirection()).act();
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));


        actionResult = new JSONObject("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");

        lastAction = Ta.takeDecision(actionResult);
        expectedAction = new Heading(dir.clockwise().clockwise()).act();
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));

        assertTrue(Ta.changedStep());
    }
}