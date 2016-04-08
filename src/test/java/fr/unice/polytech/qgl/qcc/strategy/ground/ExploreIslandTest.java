package fr.unice.polytech.qgl.qcc.strategy.ground;

import fr.unice.polytech.qgl.qcc.GameEngine;
import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.enums.Craftables;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 11/12/2015.
 */
public class ExploreIslandTest {


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

        gameEngine.getCarte().setCurrentCoord(10, 10);

        String[] ressources1 = {"TEMPERATE_DECIDUOUS_FOREST"};
        JSONArray ressource = new JSONArray(ressources1);
        gameEngine.getCarte().addBigCase(1, 1, ressource);

        String[] ressources3 = {"MANGROVE", "BEACH"};
        ressource = new JSONArray(ressources3);
        gameEngine.getCarte().addBigCase(4, 4, ressource);

        ExploreIsland ExI = new ExploreIsland(gameEngine.getCarte(), gameEngine.getContract());

        JSONObject result = new JSONObject();
        JSONObject actionTaken;

        actionTaken = ExI.takeDecision(result);

        int cpt = 13; //Case la plus proche
        while (cpt > 0){
            actionTaken = ExI.takeDecision(result);
            assertEquals("move_to", actionTaken.getString("action"));
            cpt--;
        }

        JSONObject resultExplore = new JSONObject("{\n" +
                "  \"cost\": 5,\n" +
                "  \"extras\": {\n" +
                "    \"resources\": [\n" +
                "      { \"amount\": \"HIGH\", \"resource\": \"FUR\", \"cond\": \"FAIR\" },\n" +
                "      { \"amount\": \"HIGH\", \"resource\": \"WOOD\", \"cond\": \"FAIR\" }\n" +
                "    ],\n" +
                "    \"pois\": [ \"creek-id\" ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}");

        actionTaken = ExI.takeDecision(result);
        assertEquals("explore", actionTaken.getString("action"));
        actionTaken = ExI.takeDecision(resultExplore);
        assertEquals("exploit", actionTaken.getString("action"));
        actionTaken = ExI.takeDecision(result);
        assertEquals("move_to", actionTaken.getString("action"));

        gameEngine.getContract().payPA(9800);
        actionTaken = ExI.takeDecision(result);
        assertEquals("stop", actionTaken.getString("action"));


        //System.out.println(actionTaken.getString("action"));

    }

    @Test
    public void testGetMakeAbleCraftable() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 400, \"resource\": \"PLANK\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        gameEngine.getCarte().setCurrentCoord(10, 10);

        String[] ressources1 = {"TEMPERATE_DECIDUOUS_FOREST"};
        JSONArray ressource = new JSONArray(ressources1);
        gameEngine.getCarte().addBigCase(1, 1, ressource);

        String[] ressources3 = {"MANGROVE", "BEACH"};
        ressource = new JSONArray(ressources3);
        gameEngine.getCarte().addBigCase(4, 4, ressource);

        ExploreIsland EI = new ExploreIsland(gameEngine.getCarte(), gameEngine.getContract());
        assertEquals(null, EI.getMakeAbleCraftable());
        gameEngine.getContract().stockResource(Biomes.Ressource.WOOD, 50);
        assertEquals(null, EI.getMakeAbleCraftable());
        gameEngine.getContract().stockResource(Biomes.Ressource.WOOD, 100);
        assertEquals(Craftables.PLANK, EI.getMakeAbleCraftable());


    }

    @Test
    public void testDecisionTransformRessource() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 40, \"resource\": \"PLANK\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        gameEngine.getContract().stockResource(Biomes.Ressource.WOOD, 60);

        ExploreIsland EI = new ExploreIsland(gameEngine.getCarte(), gameEngine.getContract());
        JSONObject action = EI.takeDecision(new JSONObject());

        assertEquals("transform", action.getString("action"));
        assertEquals(11, action.getJSONObject("parameters").getInt("WOOD"));
    }

    @Test
    public void testDecisionNextContract() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 10, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        gameEngine.getContract().stockResource(Biomes.Ressource.WOOD, 20);
        gameEngine.getCarte().setCurrentCoord(10, 10);

        String[] ressources1 = {"TEMPERATE_DECIDUOUS_FOREST"};
        JSONArray ressource = new JSONArray(ressources1);
        gameEngine.getCarte().addBigCase(1, 1, ressource);

        String[] ressources3 = {"MANGROVE", "BEACH"};
        ressource = new JSONArray(ressources3);
        gameEngine.getCarte().addBigCase(4, 4, ressource);
        ExploreIsland EI = new ExploreIsland(gameEngine.getCarte(), gameEngine.getContract());
        int actualRessourceToExploitValue = EI.ressourceToExploit;
        EI.takeDecision(new JSONObject());
        int newRessourceToExploitValue = EI.ressourceToExploit;
        assertEquals(actualRessourceToExploitValue+1, newRessourceToExploitValue);
    }

}