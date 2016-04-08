package fr.unice.polytech.qgl.qcc.strategy.airplane;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.GameEngine;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 07/12/2015.
 */
public class MoveToDirectionOfIslandTest {

    private GameEngine gameEngine;

    @Test
    public void testTakeDecision() throws Exception {

        /**
         * SCENARIO 1
         */
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        Direction firstDirectiondir = gameEngine.getCarte().getPlaneDirection();
        MoveToDirectionOfIsland Mtdi = new MoveToDirectionOfIsland(gameEngine.getCarte(), firstDirectiondir);

        JSONObject lastAction = Mtdi.takeDecision(new JSONObject());
        JSONObject expectedAction = new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \""+firstDirectiondir.clockwise().toString()+"\" } }");
        String actionTaken = lastAction.getString("action");
        String actionExpected = expectedAction.getString("action");
        assertEquals(actionTaken, actionExpected);

        String directionTaken = lastAction.getJSONObject("parameters").getString("direction");
        String directionExpected = expectedAction.getJSONObject("parameters").getString("direction");
        assertEquals(directionTaken, directionExpected);

        JSONObject actionResult = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");

        lastAction = Mtdi.takeDecision(actionResult);
        expectedAction = new JSONObject("{ \"action\": \"fly\" }");
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertTrue(Mtdi.changedStep());

        /**
         * SCENARIO 2
         */
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        firstDirectiondir = gameEngine.getCarte().getPlaneDirection();
        Mtdi = new MoveToDirectionOfIsland(gameEngine.getCarte(), firstDirectiondir);

        lastAction = Mtdi.takeDecision(new JSONObject());
        expectedAction = new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \""+firstDirectiondir.clockwise().toString()+"\" } }");
        actionTaken = lastAction.getString("action");
        actionExpected = expectedAction.getString("action");
        assertEquals(actionTaken, actionExpected);

        directionTaken = lastAction.getJSONObject("parameters").getString("direction");
        directionExpected = expectedAction.getJSONObject("parameters").getString("direction");
        assertEquals(directionTaken, directionExpected);

        int distanceToBorder = 5;
        actionResult = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\":"+distanceToBorder+", \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }");

        lastAction = Mtdi.takeDecision(actionResult);
        expectedAction = new JSONObject("{ \"action\": \"heading\", \"parameters\": { \"direction\": \""+firstDirectiondir.clockwise().toString()+"\" } }");
        actionTaken = lastAction.getString("action");
        actionExpected = expectedAction.getString("action");
        assertEquals(actionTaken, actionExpected);

        directionTaken = lastAction.getJSONObject("parameters").getString("direction");
        directionExpected = expectedAction.getJSONObject("parameters").getString("direction");
        assertEquals(directionTaken, directionExpected);

        distanceToBorder--;
        assertEquals(distanceToBorder, Mtdi.distanceToBorder);

        actionResult = new JSONObject("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");
        while(distanceToBorder > 3){
            lastAction = Mtdi.takeDecision(actionResult);
            expectedAction = new JSONObject("{ \"action\": \"fly\" }");
            assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
            --distanceToBorder;
            assertEquals(distanceToBorder, Mtdi.distanceToBorder);
        }

        lastAction = Mtdi.takeDecision(actionResult);
        expectedAction = new JSONObject("{ \"action\": \"heading\", \"parameters\": { \"direction\": \""+firstDirectiondir+"\" } }");
        assertEquals(lastAction.getString("action"), expectedAction.getString("action"));
        assertEquals(lastAction.getJSONObject("parameters").getString("direction"), expectedAction.getJSONObject("parameters").getString("direction"));

        assertTrue(Mtdi.changedStep());
    }
}