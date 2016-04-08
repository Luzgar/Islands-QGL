package fr.unice.polytech.qgl.qcc;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.enums.Craftables;
import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.database.recipes.Plank;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Kevin on 02/12/2015.
 */
public class AnalyzeResultTest {

    private GameEngine gameEngine;
    private AnalyzeResult analyzeResult;

    @Test
    public void testEchoResult() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        analyzeResult = new AnalyzeResult(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());
        JSONObject result = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        analyzeResult.analyzeEchoResult(result);
        assertEquals(gameEngine.getContract().getPa(), 9999);
    }

    @Test
    public void testFlyResult() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"N\"\n" +
                "}"));
        analyzeResult = new AnalyzeResult(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());
        JSONObject result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        int x = gameEngine.getCarte().getCurrentX();
        int y = gameEngine.getCarte().getCurrentY();
        analyzeResult.analyzeFlyResult(result);
        assertEquals(gameEngine.getContract().getPa(), 9998);


        assertEquals(gameEngine.getCarte().getCurrentX(), x);
        assertEquals(gameEngine.getCarte().getCurrentY(), y+3);


        gameEngine.getCarte().setPlaneDirection(Direction.E);
        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        analyzeResult.analyzeFlyResult(result);
        assertEquals(gameEngine.getCarte().getCurrentX(), x+3);
        assertEquals(gameEngine.getCarte().getCurrentY(), y);


        gameEngine.getCarte().setPlaneDirection(Direction.W);
        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        analyzeResult.analyzeFlyResult(result);
        assertEquals(gameEngine.getCarte().getCurrentX(), x-3);
        assertEquals(gameEngine.getCarte().getCurrentY(), y);

        gameEngine.getCarte().setPlaneDirection(Direction.S);
        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        analyzeResult.analyzeFlyResult(result);
        assertEquals(gameEngine.getCarte().getCurrentX(), x);
        assertEquals(gameEngine.getCarte().getCurrentY(), y-3);

    }

    @Test
    public void testHeadingResult() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        analyzeResult = new AnalyzeResult(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());
        JSONObject result = new JSONObject("{ \"cost\": 4, \"extras\": {}, \"status\": \"OK\" }");

        int x = gameEngine.getCarte().getCurrentX();
        int y = gameEngine.getCarte().getCurrentY();
        Direction dir = gameEngine.getCarte().getPlaneDirection();

        analyzeResult.analyzeHeadingResult(result, Direction.S);
        assertEquals(gameEngine.getCarte().getPlaneDirection(), Direction.S);
        /*if(dir.equals(Direction.E)) {
            assertEquals(gameEngine.getCarte().getCurrentX(), x + 3);
            assertEquals(gameEngine.getCarte().getCurrentY(), y - 3);
        }
        else if(dir.equals(Direction.W)){*/
            assertEquals(gameEngine.getCarte().getCurrentX(), x - 3);
            assertEquals(gameEngine.getCarte().getCurrentY(), y - 3);
        //}

        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        dir = gameEngine.getCarte().getPlaneDirection();

        analyzeResult.analyzeHeadingResult(result, Direction.E);
        assertEquals(gameEngine.getCarte().getPlaneDirection(), Direction.E);
        /*if(dir.equals(Direction.N)) {
            assertEquals(gameEngine.getCarte().getCurrentX(), x + 3);
            assertEquals(gameEngine.getCarte().getCurrentY(), y + 3);
        }
        else if(dir.equals(Direction.S)){*/
            assertEquals(gameEngine.getCarte().getCurrentX(), x + 3);
            assertEquals(gameEngine.getCarte().getCurrentY(), y - 3);
        //}

        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        dir = gameEngine.getCarte().getPlaneDirection();

        analyzeResult.analyzeHeadingResult(result, Direction.N);
        assertEquals(gameEngine.getCarte().getPlaneDirection(), Direction.N);
        //if(dir.equals(Direction.E)) {
            assertEquals(gameEngine.getCarte().getCurrentX(), x + 3);
            assertEquals(gameEngine.getCarte().getCurrentY(), y + 3);
        /*}
        else if(dir.equals(Direction.W)){
            assertEquals(gameEngine.getCarte().getCurrentX(), x - 3);
            assertEquals(gameEngine.getCarte().getCurrentY(), y + 3);
        }*/

        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        dir = gameEngine.getCarte().getPlaneDirection();

        analyzeResult.analyzeHeadingResult(result, Direction.W);
        assertEquals(gameEngine.getCarte().getPlaneDirection(), Direction.W);
        //if(dir.equals(Direction.N)) {
            assertEquals(gameEngine.getCarte().getCurrentX(), x - 3);
            assertEquals(gameEngine.getCarte().getCurrentY(), y + 3);
        /*}
        else if(dir.equals(Direction.S)){
            assertEquals(gameEngine.getCarte().getCurrentX(), x - 3);
            assertEquals(gameEngine.getCarte().getCurrentY(), y - 3);
        }*/

        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        dir = gameEngine.getCarte().getPlaneDirection();

        analyzeResult.analyzeHeadingResult(result, Direction.N);
        assertEquals(gameEngine.getCarte().getPlaneDirection(), Direction.N);
        assertEquals(gameEngine.getCarte().getCurrentX(), x - 3);
        assertEquals(gameEngine.getCarte().getCurrentY(), y + 3);

        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        dir = gameEngine.getCarte().getPlaneDirection();

        analyzeResult.analyzeHeadingResult(result, Direction.E);
        assertEquals(gameEngine.getCarte().getPlaneDirection(), Direction.E);
        assertEquals(gameEngine.getCarte().getCurrentX(), x + 3);
        assertEquals(gameEngine.getCarte().getCurrentY(), y + 3);

        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        dir = gameEngine.getCarte().getPlaneDirection();

        analyzeResult.analyzeHeadingResult(result, Direction.S);
        assertEquals(gameEngine.getCarte().getPlaneDirection(), Direction.S);
        assertEquals(gameEngine.getCarte().getCurrentX(), x + 3);
        assertEquals(gameEngine.getCarte().getCurrentY(), y - 3);

        x = gameEngine.getCarte().getCurrentX();
        y = gameEngine.getCarte().getCurrentY();
        dir = gameEngine.getCarte().getPlaneDirection();

        analyzeResult.analyzeHeadingResult(result, Direction.W);
        assertEquals(gameEngine.getCarte().getPlaneDirection(), Direction.W);
        assertEquals(gameEngine.getCarte().getCurrentX(), x - 3);
        assertEquals(gameEngine.getCarte().getCurrentY(), y - 3);

    }

    @Test
    public void testScanResult() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        analyzeResult = new AnalyzeResult(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());
        JSONObject result = new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [\"id\"]}, \"status\": \"OK\"}");
        analyzeResult.analyzeScanResult(result);

        assertEquals(gameEngine.getContract().getPa(), 9998);
        assertEquals(gameEngine.getCarte().getListCreeks().get(0).getId(), "id");
        /*ToDO : Biomes*/
    }


    @Test
    public void testLandResult() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        analyzeResult = new AnalyzeResult(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());
        JSONObject result = new JSONObject("{ \"cost\": 3, \"extras\": {}, \"status\": \"OK\" }");
        analyzeResult.analyzeLandResult(result);
        assertEquals(gameEngine.getContract().getPa(), 9997);
    }

    @Test
    public void testAnalyzeMoveToResult() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        analyzeResult = new AnalyzeResult(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());
        JSONObject result = new JSONObject("{ \"cost\": 6, \"extras\": { }, \"status\": \"OK\" }");
        int x = gameEngine.getCarte().getCurrentX();
        int y = gameEngine.getCarte().getCurrentY();

        analyzeResult.analyzeMoveToResult(result, Direction.N);
        ++y;

        assertEquals(gameEngine.getContract().getPa(), 9994);
        assertEquals(y, gameEngine.getCarte().getCurrentY());
        assertEquals(x, gameEngine.getCarte().getCurrentX());

        analyzeResult.analyzeMoveToResult(result, Direction.W);
        --x;

        assertEquals(gameEngine.getContract().getPa(), 9988);
        assertEquals(y, gameEngine.getCarte().getCurrentY());
        assertEquals(x, gameEngine.getCarte().getCurrentX());

        analyzeResult.analyzeMoveToResult(result, Direction.S);
        --y;

        assertEquals(gameEngine.getContract().getPa(), 9982);
        assertEquals(y, gameEngine.getCarte().getCurrentY());
        assertEquals(x, gameEngine.getCarte().getCurrentX());

        analyzeResult.analyzeMoveToResult(result, Direction.E);
        ++x;

        assertEquals(gameEngine.getContract().getPa(), 9976);
        assertEquals(y, gameEngine.getCarte().getCurrentY());
        assertEquals(x, gameEngine.getCarte().getCurrentX());
    }

    @Test
    public void testAnalyzeExploreResult() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        analyzeResult = new AnalyzeResult(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());

        JSONObject result = new JSONObject("{\n" +
                "  \"cost\": 5,\n" +
                "  \"extras\": {\n" +
                "    \"resources\": [\n" +
                "      { \"amount\": \"HIGH\", \"resource\": \"FUR\", \"cond\": \"FAIR\" },\n" +
                "      { \"amount\": \"LOW\", \"resource\": \"WOOD\", \"cond\": \"HARSH\" }\n" +
                "    ],\n" +
                "    \"pois\": [ \"creek-id\" ]\n" +
                "  },\n" +
                "  \"status\": \"OK\"\n" +
                "}");


        int x = gameEngine.getCarte().getCurrentX();
        int y = gameEngine.getCarte().getCurrentY();
        //analyzeResult.analyzeExploreResult(result);

        //assertEquals(gameEngine.getContract().getPa(), 9995);

        //Case currentCase = gameEngine.getCarte().getCase(x, y);

        //ToDo : Test de la recuperation de la liste des ressources après resolution problème NullPointerException
        //ToDo : Test recuperation de la crique
    }

    @Test
    public void testAnalyzeExploitResult() throws Exception {
        //ToDo : test decrementation des ressources exploité dans contrat
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        analyzeResult = new AnalyzeResult(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());

        JSONObject result = new JSONObject("{ \"cost\": 3, \"extras\": {\"amount\": 18}, \"status\": \"OK\" }");

        analyzeResult.analyzeExploitResult(result, Biomes.Ressource.WOOD);
        int i = gameEngine.getContract().getContract().get(Biomes.Ressource.WOOD);
        assertEquals(582, i);
    }


    @Test
    public void testAnalyzeTransformResult() throws Exception {
        gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"PLANK\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        analyzeResult = new AnalyzeResult(gameEngine.getCarte(), gameEngine.getContract(), gameEngine.getCarteStats());
        JSONObject result = new JSONObject("{ \"cost\": 5, \"extras\": { \"production\": 1, \"kind\": \"PLANK\" },\"status\": \"OK\" }");
        analyzeResult.analyzeTransformResult(result);

        assertEquals(9995, gameEngine.getContract().getPa());
    }
}


