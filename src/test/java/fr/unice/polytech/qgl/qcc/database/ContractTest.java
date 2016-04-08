package fr.unice.polytech.qgl.qcc.database;

import fr.unice.polytech.qgl.qcc.GameEngine;
import fr.unice.polytech.qgl.qcc.database.enums.Biomes;
import fr.unice.polytech.qgl.qcc.database.recipes.Glass;
import fr.unice.polytech.qgl.qcc.database.recipes.Ingot;
import fr.unice.polytech.qgl.qcc.database.recipes.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 11/12/2015.
 */
public class ContractTest {

    @Test
    public void testInitContract() throws Exception {
        Contract contract = new Contract(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        Contract contract2 = new Contract(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        Contract contract3 = new Contract(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 200, \"resource\": \"GLASS\" },\n" +
                "    { \"amount\": -10, \"resource\": \"FLOWER\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));


        Map<Biomes.Ressource,Integer> ct = new HashMap<>();
        ct.put(Biomes.Ressource.WOOD,1700);
        ct.put(Biomes.Ressource.QUARTZ, 2200);



        assertEquals(contract.getPa(), 10000);
        assertEquals(contract.getNbrOfMen(), 12);
        assertEquals(contract.getContract(), ct);
        assertEquals(contract2.getContract(), ct);
        assertEquals(contract3.getContract(), ct);
    }

    @Test
    public void testSecondaryRessourceContract() throws Exception{
        Contract contract = new Contract(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 200, \"resource\": \"PLANK\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        assertEquals(57, contract.getAmount(Biomes.Ressource.WOOD));
    }


    @Test
    public void testStockResource() throws Exception {
        Contract contract = new Contract(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 10, \"resource\": \"FLOWER\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        contract.stockResource(Biomes.Ressource.WOOD, 23);
        assertEquals(577, contract.getAmount(Biomes.Ressource.WOOD));
        assertEquals(23, contract.getHarvested(Biomes.Ressource.WOOD));
        assertEquals(-1, contract.getHarvested(Biomes.Ressource.QUARTZ));

        contract.stockResource(Biomes.Ressource.FLOWER, 15);
        assertEquals(-5, contract.getAmount(Biomes.Ressource.FLOWER)); //Normal, le nombre en negatif représente les ressource qu'on a en plus du contrat initial

    }

    @Test
    public void testDoNextContract() throws Exception
    {
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 4000, \"resource\": \"FISH\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        for (int i = 50; i<5000; i += 9)
        {
            gameEngine.getCarte().addBigCase(i,i,new JSONArray(new String[] {"OCEAN"}));
        }


        assertTrue(gameEngine.getContract().doNextContract(gameEngine.getCarte(), Biomes.Ressource.FISH));

    }

    @Test
    public void testClosest() throws Exception
    {

        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 4000, \"resource\": \"FISH\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        String [] biome = {"OCEAN"};
        Case c = new Case(50,50,biome);
        List<Case> list = new ArrayList<>();
        Case c2 = new Case(60,60,biome);
        list.add(c2);
        list.add(new Case(45,20,biome));
        list.add(new Case(100,100,biome));

        assertTrue(gameEngine.getContract().closestCaseOf(list, c).equals(c2));
        assertFalse(gameEngine.getContract().closestCaseOf(list, c).equals(new Case(45,20)));
    }


    @Test
    public void testNumberOfBiomes() throws Exception
    {
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));


        gameEngine.getCarte().addBigCase(50,50,new JSONArray(new String[] {"OCEAN"}));

        assertEquals(9, gameEngine.getContract().numberOfBiomes(gameEngine.getCarte(), Biomes.Ressource.FISH));

    }

    @Test
    public void testUseResource() throws Exception {
        Contract contract = new Contract(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 10, \"resource\": \"FLOWER\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        assertEquals(600, contract.getAmount(Biomes.Ressource.WOOD));
        assertEquals(10, contract.getAmount(Biomes.Ressource.FLOWER));
    }

    @Test
    public void testStockCraftable() throws Exception {
        Contract contract = new Contract(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 10, \"resource\": \"GLASS\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));
        assertEquals(10, contract.getHarvested(new Glass()));
        contract.craft(new Glass());
        assertEquals(10, contract.getHarvested(new Glass()));
        contract.stockResource(Biomes.Ressource.QUARTZ, 50);
        contract.stockResource(Biomes.Ressource.WOOD, 50);
        contract.craft(new Glass());
        assertEquals(-1, contract.getHarvested(new Ingot()));
        assertEquals(-1, contract.getHarvested(new Glass()));
    }

    @Test
    public void testPayPA() throws Exception {
        Contract contract = new Contract(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "    { \"amount\": 10, \"resource\": \"FLOWER\" }\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        contract.payPA(100);
        assertEquals(9900, contract.getPa());

        contract.payPA(26);
        assertEquals(9874, contract.getPa());
    }

    @Test
    public void testCraft() throws Exception {
        Contract contract = new Contract(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 10, \"resource\": \"GLASS\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        contract.stockResource(Biomes.Ressource.QUARTZ, 50);
        contract.stockResource(Biomes.Ressource.WOOD, 50);
        Recipe recipe = new Glass();
        contract.craft(recipe);
        assertEquals(10, contract.getAmount(Biomes.Ressource.WOOD));
    }

}