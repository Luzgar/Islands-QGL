package fr.unice.polytech.qgl.qcc.strategy.actions;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by renaud on 16/11/2015.
 */
public class StopTest {

    public static JSONObject testActionStop = new JSONObject("{ \"action\": \"stop\" }");

    @Test
    public void testAct() throws Exception {

        Action stop = new Stop();
        JSONObject actReturned = stop.act();

        String action1 = actReturned.getString("action");
        String action2 = testActionStop.getString("action");

        assertEquals(action1, action2);
    }
}