package fr.unice.polytech.qgl.qcc.database;

import fr.unice.polytech.qgl.qcc.GameEngine;
import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by renaud on 01/02/2016.
 */
public class CarteTest {

    @Test
    public void testSpeed()
    {
        double time = System.currentTimeMillis();
        List<Case> list = new ArrayList<>();
        for (int i = 0; i< 15000; i++)
        {
            list.add(new Case());
        }
        assertTrue((System.currentTimeMillis() - time) < 2000);
    }

    @Test
    public void testIsExcluded() throws Exception
    {
        int [] exc = new int[3];
        exc[0] = 1;
        exc[1] = 4;
        exc[2] = 7;

        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        Carte map = gameEngine.getCarte();

        assertTrue(map.isExcluded(1,exc));
        assertTrue(map.isExcluded(4,exc));
        assertTrue(map.isExcluded(7,exc));
        assertTrue(map.isExcluded(10,exc));
        assertTrue(map.isExcluded(13,exc));

        assertFalse(map.isExcluded(2, exc));
        assertFalse(map.isExcluded(3,exc));
        assertFalse(map.isExcluded(26, exc));

    }

    @Test
    public void testUsefulCasesToExtrapolate() throws Exception
    {
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        Carte map = gameEngine.getCarte();
        map.addBigCase(1, 1, new JSONArray());
        List<Case> asser = new ArrayList<>();
        asser.add(new Case(0, 2));
        asser.add(new Case(2, 2));
        asser.add(new Case(0, 3));
        asser.add(new Case(2, 3));
        asser.add(new Case(0, 4));
        asser.add(new Case(2, 4));
        /*for (int i = 0; i < asser.size(); i++)
        {
            assertTrue((map.usefulCasesToExtrapolate(Direction.W).get(i)).equals(asser.get(i)));
        }*/

    }

    @Test
    public void testExtrapolateVertical() throws Exception
    {
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"W\"\n" +
                "}"));

        Carte map = gameEngine.getCarte();

        String [] strBiomes = {"MANGROVE"};
        JSONArray biomes = new JSONArray(strBiomes);

        map.addBigCase(2, 5, biomes);
        map.addBigCase(8, 5, biomes);

        int prevSize = map.getListCases().size();
        map.extrapolate(Direction.W);
        assertEquals(map.getListCases().size() - prevSize, 9); // 9 cases ajoutées

        String [] strBiomes2 = {"BEACH"};
        JSONArray biomes2 = new JSONArray(strBiomes2);
        map.addBigCase(14, 5, biomes2);

        int prevSize2 = map.getListCases().size();
        map.extrapolate(Direction.W);
        assertEquals(prevSize2, map.getListCases().size()); // la taille ne doit pas avoir changé car le biomes n'est pas le meme, on ne peut pas extrapoler plus

        int prevSize3 = map.getListCases().size();
        String [] strBiomes3 = {"BEACH"};
        JSONArray biomes3 = new JSONArray(strBiomes3);
        map.addBigCase(14,-1,biomes3);
        map.extrapolate(Direction.W);
        assertEquals(map.getListCases().size()-prevSize3,9); // 9 nouvelles cases en horizontal


    }

    @Test
    public void testExtrapolateHorizontal() throws Exception{
        GameEngine gameEngine = new GameEngine(new JSONObject("{ \n" +
                "  \"men\": 12,\n" +
                "  \"budget\": 10000,\n" +
                "  \"contracts\": [\n" +
                "    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
                "  ],\n" +
                "  \"heading\": \"N\"\n" +
                "}"));

        Carte map = gameEngine.getCarte();

        String [] strBiomes = {"MANGROVE"};
        JSONArray biomes = new JSONArray(strBiomes);

        map.addBigCase(2, 5, biomes);
        map.addBigCase(2, 11, biomes);

        int prevSize = map.getListCases().size();
        map.extrapolate(Direction.N);
        assertEquals(map.getListCases().size() - prevSize, 9); // 9 cases ajoutées

        String [] strBiomes2 = {"BEACH"};
        JSONArray biomes2 = new JSONArray(strBiomes2);
        map.addBigCase(14, 5, biomes2);

        int prevSize2 = map.getListCases().size();
        map.extrapolate(Direction.N);
        assertEquals(prevSize2, map.getListCases().size()); // la taille ne doit pas avoir changé car le biomes n'est pas le meme, on ne peut pas extrapoler plus

        int prevSize3 = map.getListCases().size();
        String [] strBiomes3 = {"BEACH"};
        JSONArray biomes3 = new JSONArray(strBiomes3);
        map.addBigCase(11,5,biomes3);
        map.extrapolate(Direction.N);
        assertEquals(map.getListCases().size()-prevSize3,9); // 9 nouvelles cases en horizontal
    }

}