package fr.unice.polytech.qgl.qcc.strategy.actions;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by renaud on 16/11/2015.
 */
public class HeadingTest {

    public static JSONObject testActionHeading = new JSONObject("{ \"action\": \"heading\", \"parameters\": { \"direction\": \"N\" } }");
    @Test
    public void testAct() throws Exception {

        Action heading = new Heading(Direction.N);
        JSONObject actReturned = heading.act();

        String action1 = actReturned.getString("action");
        String action2 = testActionHeading.getString("action");
        assertEquals(action1, action2);

        String direction1 = actReturned.getJSONObject("parameters").getString("direction");
        String direction2 = testActionHeading.getJSONObject("parameters").getString("direction");
        assertEquals(direction1, direction2);
    }
}