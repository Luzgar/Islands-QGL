package fr.unice.polytech.qgl.qcc.strategy.actions;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by renaud on 16/11/2015.
 */
public class ScanTest {

    public static JSONObject testActionScan = new JSONObject("{ \"action\": \"scan\" }");

    @Test
    public void testAct() throws Exception {

        Action scan = new Scan();
        JSONObject actReturned = scan.act();

        String action1 = actReturned.getString("action");
        String action2 = testActionScan.getString("action");
        assertEquals(action1, action2);

    }

    @Test
    public void testJSONArrayToTab() throws Exception {

        JSONArray jsonarray = new JSONArray();
        jsonarray.put("test1");
        jsonarray.put("test2");

        String [] tab = new String[2];
        tab[0] = "test1";
        tab[1] = "test2";

       // assertEquals(tab, Scan.JSONArrayToTab(jsonarray));

    }
}