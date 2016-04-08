package fr.unice.polytech.qgl.qcc.strategy.actions;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Kevin on 27/11/2015.
 */
public class MoveToTest {

    public static JSONObject testActionMoveTo = new JSONObject("{ \"action\": \"move_to\", \"parameters\": { \"direction\": \"N\" } }");
    @Test
    public void testAct() throws Exception {
        Action action = new MoveTo(Direction.N);
        JSONObject actReturned = action.act();

        String action1 = actReturned.getString("action");
        String action2 = testActionMoveTo.getString("action");
        assertEquals(action1, action2);

        String direction1 = actReturned.getJSONObject("parameters").getString("direction");
        String direction2 = testActionMoveTo.getJSONObject("parameters").getString("direction");
        assertEquals(direction1, direction2);
    }
}
