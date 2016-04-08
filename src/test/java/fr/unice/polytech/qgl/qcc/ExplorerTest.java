package fr.unice.polytech.qgl.qcc;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by user on 16/11/15.
 */
public class ExplorerTest extends TestCase {

    @Test
    public void testInitialize() throws Exception {

        Explorer explorer = new Explorer();
        explorer.initialize("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLSS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}");
        assertTrue(explorer.error);
    }

    @Test
    public void testTakeDecision() throws Exception {
        Explorer explorer = new Explorer();
        explorer.initialize("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLSS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}");
        JSONObject result = new JSONObject(explorer.takeDecision());
        assertEquals("stop", result.getString("action"));

        explorer = new Explorer();
        explorer.initialize("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}");
        result = new JSONObject(explorer.takeDecision());
        assertTrue(result.has("action"));
        assertFalse(explorer.error);

        explorer = new Explorer();
        explorer.initialize("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}");
        explorer.gameEngine = spy(explorer.gameEngine);
        when(explorer.gameEngine.takeDecision()).thenThrow(new RuntimeException());
        result = new JSONObject(explorer.takeDecision());
        assertEquals("stop", result.getString("action"));

    }

    @Test
    public void testAcknowledgeResults() throws Exception {
        Explorer explorer = new Explorer();
        explorer.initialize("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}");
        explorer.gameEngine = spy(explorer.gameEngine);
        JSONObject result = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        doThrow(new RuntimeException()).when(explorer.gameEngine).acknowledgeResults(result);
        explorer.acknowledgeResults("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
        assertTrue(explorer.error);
    }
}