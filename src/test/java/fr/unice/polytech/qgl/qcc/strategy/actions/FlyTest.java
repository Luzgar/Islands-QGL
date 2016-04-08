package fr.unice.polytech.qgl.qcc.strategy.actions;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by renaud on 16/11/2015.
 */
public class FlyTest {

    public static JSONObject testActionFly = new JSONObject("{ \"action\": \"fly\" }");
    @Test
    public void testAct() throws Exception {

        Action fly = new Fly();
        JSONObject actReturned = fly.act();

        String action1 = actReturned.getString("action");
        String action2 = testActionFly.getString("action");
        assertEquals(action1, action2);
    }
}