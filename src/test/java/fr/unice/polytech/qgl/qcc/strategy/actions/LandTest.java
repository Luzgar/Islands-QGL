package fr.unice.polytech.qgl.qcc.strategy.actions;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 16/11/2015.
 */
public class LandTest {

    private static JSONObject testActionLand = new JSONObject("{ \"action\": \"land\", \"parameters\": { \"creek\": \"id\", \"people\": 20 }}");
    @Test
    public void testAct() throws Exception {
        Action land = new Land("id", 20);
        JSONObject actReturned = land.act();

        String action1 = actReturned.getString("action");
        String action2 = testActionLand.getString("action");
        assertEquals(action1, action2);

        String creek1 = actReturned.getJSONObject("parameters").getString("creek");
        String creek2 = testActionLand.getJSONObject("parameters").getString("creek");
        assertEquals(creek1, creek2);

        int people1 = actReturned.getJSONObject("parameters").getInt("people");
        int people2 = testActionLand.getJSONObject("parameters").getInt("people");
        assertEquals(people1, people2);
    }
}