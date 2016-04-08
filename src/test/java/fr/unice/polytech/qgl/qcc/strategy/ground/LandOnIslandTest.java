package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.database.Creek;
import fr.unice.polytech.qgl.qcc.GameEngine;
import fr.unice.polytech.qgl.qcc.strategy.actions.Land;
import fr.unice.polytech.qgl.qcc.strategy.actions.Stop;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 11/12/2015.
 */
public class LandOnIslandTest {

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

        LandOnIsland Loi = new LandOnIsland(gameEngine.getCarte(), gameEngine.getContract());
        JSONObject actionTaken = Loi.takeDecision(new JSONObject());
        JSONObject expectedAction = new Stop().act();
        assertEquals(actionTaken.getString("action"), expectedAction.getString("action"));

        int coordX = 10;
        int coordY = 10;
        gameEngine.getCarte().addCreeks(new Creek("id", coordX, coordY));
        actionTaken = Loi.takeDecision(new JSONObject());
        expectedAction = new Land("id", gameEngine.getContract().getNbrOfMen()-1).act();

        assertEquals(actionTaken.getString("action"), expectedAction.getString("action"));
        assertEquals(actionTaken.getJSONObject("parameters").getString("creek"), expectedAction.getJSONObject("parameters").getString("creek"));
        assertEquals(actionTaken.getJSONObject("parameters").getInt("people"), 1);

        assertEquals(gameEngine.getCarte().getCurrentX(), coordX);
        assertEquals(gameEngine.getCarte().getCurrentY(), coordY);
        assertEquals(1, gameEngine.getContract().getLandedMen());
        assertTrue(Loi.changedStep());

    }
}