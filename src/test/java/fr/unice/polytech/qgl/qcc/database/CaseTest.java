package fr.unice.polytech.qgl.qcc.database;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 11/12/2015.
 */
public class CaseTest {

    @Test
    public void testGetX() throws Exception {
        Case cases = new Case();
        assertEquals(cases.getX(), 0);

        cases = new Case(10, 5);
        assertEquals(cases.getX(), 10);
    }

    @Test
    public void testGetY() throws Exception {
        Case cases = new Case();
        assertEquals(cases.getY(), 0);

        cases = new Case(10, 5);
        assertEquals(cases.getY(), 5);
    }

    @Test
    public void testGetRessources() throws Exception {
        Case cases = new Case();
        assertEquals(null, cases.getBiomes());

        cases = new Case(10, 5);
        assertEquals(null, cases.getBiomes());

        String[] ressources = {"FOREST", "BEACH"};
        cases = new Case(10, 5, ressources);
        assertEquals(ressources, cases.getBiomes());
    }

    @Test
    public void testCompare() throws Exception {
        Case case1 = new Case(0, 0);
        Case case2 = new Case(0, 1);
        Case case3 = new Case(0, 0);

        assertTrue(case1.equals(case3));
        assertFalse(case2.equals(case1));
    }
}