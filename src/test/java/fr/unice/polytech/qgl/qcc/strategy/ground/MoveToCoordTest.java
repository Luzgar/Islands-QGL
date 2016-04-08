package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.GameEngine;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 28/12/2015.
 */
public class MoveToCoordTest {

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

        int currentX = 53;
        int currentY = 24;
        gameEngine.getCarte().setCurrentCoord(currentX, currentY);
        int xToMove = 10;
        int yToMove = 10;
        MoveToCoord MTC = new MoveToCoord(gameEngine.getCarte(), xToMove, yToMove);
        int xDelta = Math.abs(gameEngine.getCarte().getCurrentX() - xToMove);
        int yDelta = Math.abs(gameEngine.getCarte().getCurrentY() - yToMove);

        JSONObject actionTaken;
        JSONObject expectedAction;
        JSONObject result = new JSONObject("{ \"cost\": 6, \"extras\": { }, \"status\": \"OK\" }");

        while(xDelta > 0){
            actionTaken = MTC.takeDecision(result);
            assertEquals("W", actionTaken.getJSONObject("parameters").getString("direction"));
            assertEquals("move_to", actionTaken.getString("action"));
            --xDelta;
        }
        while(yDelta > 0){
            actionTaken = MTC.takeDecision(result);
            assertEquals("S", actionTaken.getJSONObject("parameters").getString("direction"));
            assertEquals("move_to", actionTaken.getString("action"));
            --yDelta;
        }

        actionTaken = MTC.takeDecision(result);
        assertTrue(MTC.changedStep());


        /*************** SCENARIO 2 ***************/
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        currentX = 1;
        currentY = 1;
        gameEngine.getCarte().setCurrentCoord(currentX, currentY);
        xToMove = 10;
        yToMove = 10;
        MTC = new MoveToCoord(gameEngine.getCarte(), xToMove, yToMove);
        xDelta = Math.abs(gameEngine.getCarte().getCurrentX() - xToMove);
        yDelta = Math.abs(gameEngine.getCarte().getCurrentY() - yToMove);

        result = new JSONObject("{ \"cost\": 6, \"extras\": { }, \"status\": \"OK\" }");

        while(xDelta > 0){
            actionTaken = MTC.takeDecision(result);
            assertEquals("E", actionTaken.getJSONObject("parameters").getString("direction"));

            assertEquals("move_to", actionTaken.getString("action"));
            --xDelta;
        }
        while(yDelta > 0){
            actionTaken = MTC.takeDecision(result);
            assertEquals("N", actionTaken.getJSONObject("parameters").getString("direction"));

            assertEquals("move_to", actionTaken.getString("action"));
            --yDelta;
        }

        actionTaken = MTC.takeDecision(result);
        assertTrue(MTC.changedStep());
    }
}