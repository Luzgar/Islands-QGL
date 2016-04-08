package fr.unice.polytech.qgl.qcc.strategy.actions;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Kevin on 28/11/2015.
 */
public class ScoutTest {

    public static JSONObject testActionScout = new JSONObject("{ \"action\": \"scout\", \"parameters\": { \"direction\": \"N\" } }");

    @Test
    public void testAct() throws Exception {
        Action scout = new Scout(Direction.N);
        JSONObject actReturned = scout.act();

        String action1 = actReturned.getString("action");
        String action2 = testActionScout.getString("action");
        assertEquals(action1, action2);

        String direction1 = actReturned.getJSONObject("parameters").getString("direction");
        String direction2 = testActionScout.getJSONObject("parameters").getString("direction");
        assertEquals(direction1, direction2);

    }
}
