package fr.unice.polytech.qgl.qcc.strategy.actions;


import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by renaud on 16/11/2015.
 */
public class EchoTest {

    public static JSONObject testActionEcho = new JSONObject("{ \"action\": \"echo\", \"parameters\": { \"direction\": \"E\" } }");

    @Test
    public void testAct() throws Exception {
        Action echo = new Echo(Direction.E);
        JSONObject actReturned = echo.act();

        String action1 = actReturned.getString("action");
        String action2 = testActionEcho.getString("action");
        assertEquals(action1, action2);

        String direction1 = actReturned.getJSONObject("parameters").getString("direction");
        String direction2 = testActionEcho.getJSONObject("parameters").getString("direction");
        assertEquals(direction1, direction2);
    }
}