package fr.unice.polytech.qgl.qcc;

import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import fr.unice.polytech.qgl.qcc.database.enums.StepEnum;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by renaud on 07/12/2015.
 */
public class GameEngineTest {

    @Test
    public void testTakeDecision() throws Exception {
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
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

        assertEquals(StepEnum.MOVETODIRECTIONOFISLAND, gameEngine.currentStep);

        gameEngine.actualStrategy = spy(gameEngine.actualStrategy);
        when(gameEngine.actualStrategy.changedStep()).thenReturn(true);

        gameEngine.takeDecision();
        assertEquals(StepEnum.MOVEEXTRIMITYISLAND, gameEngine.currentStep);

        gameEngine.actualStrategy = spy(gameEngine.actualStrategy);
        when(gameEngine.actualStrategy.changedStep()).thenReturn(true);
        gameEngine.takeDecision();
        assertEquals(StepEnum.FINDCREEKS, gameEngine.currentStep);

        gameEngine.actualStrategy = spy(gameEngine.actualStrategy);
        when(gameEngine.actualStrategy.changedStep()).thenReturn(true);
        gameEngine.takeDecision();
        assertEquals(StepEnum.LANDONISLAND, gameEngine.currentStep);

        gameEngine.actualStrategy = spy(gameEngine.actualStrategy);
        when(gameEngine.actualStrategy.changedStep()).thenReturn(true);
        gameEngine.takeDecision();
        assertEquals(StepEnum.EXPLOREISLAND, gameEngine.currentStep);
    }

    @Test
    public void testAcknowledgeResults() throws Exception {
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"PLANK\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        gameEngine.resultAnalyzer = spy(gameEngine.resultAnalyzer);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"fly\" }");
        JSONObject result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeFlyResult(result);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"heading\", \"parameters\": { \"direction\": \"N\" } }");
        result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeHeadingResult(result, Direction.N);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \"E\" } }");
        result = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeEchoResult(result);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"scan\" }");
        result = new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [], \"creeks\": []}, \"status\": \"OK\"}");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeScanResult(result);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"stop\" }");
        result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeStopResult(result);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"land\", \"parameters\": { \"creek\": \"id\", \"people\": 1 }}");
        result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeLandResult(result);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"move_to\", \"parameters\": { \"direction\": \"N\" } }");
        result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeMoveToResult(result, Direction.N);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"scout\", \"parameters\": { \"direction\": \"N\" } }");
        result = new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\", \"WOOD\"] }, \"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeScoutResult(result);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"explore\" }");
        result = new JSONObject("{\n" +
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
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeExploreResult(result);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"exploit\", \"parameters\": { \"resource\": \"WOOD\" }}");
        result = new JSONObject("{ \"cost\": 3, \"extras\": {\"amount\": 9}, \"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeExploitResult(result, Biomes.Ressource.WOOD);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"scout\", \"parameters\": { \"direction\": \"N\" } }");
        result = new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\", \"WOOD\"] }, \"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeScoutResult(result);

        gameEngine.lastTakenAction = new JSONObject("{ \"action\": \"transform\", \"parameters\": { \"WOOD\": 6}}");
        result = new JSONObject("{ \"cost\": 5, \"extras\": { \"production\": 1, \"kind\": \"PLANK\" },\"status\": \"OK\" }");
        gameEngine.acknowledgeResults(result);
        verify(gameEngine.resultAnalyzer).analyzeTransformResult(result);
    }
}