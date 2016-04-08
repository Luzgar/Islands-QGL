package fr.unice.polytech.qgl.qcc.exceptions;

import fr.unice.polytech.qgl.qcc.GameEngine;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 28/12/2015.
 */
public class ExceptionTest {

    @Test(expected = NoBudgetException.class)
    public void testBudgetException() throws Exception {
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
    }

    @Test(expected = NoMenException.class)
    public void testNoMenException() throws Exception{
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
    }

    @Test(expected = NoHeadingException.class)
    public void testNoHeadingException() throws Exception{
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ]\n" +
                "}"));
    }

    @Test(expected = UnknownResourceException.class)
    public void testUnknownRessourceException() throws Exception{
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"UNKNOWN\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
    }

    @Test(expected = NoContractException.class)
    public void testNoContractException() throws Exception{
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
    }

}