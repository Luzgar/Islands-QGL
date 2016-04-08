package fr.unice.polytech.qgl.qcc.strategy.actions;

import fr.unice.polytech.qgl.qcc.database.enums.Direction;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 26/12/2015.
 */
public class GlimpseTest {

    @Test
    public void testAct() throws Exception {
        Action action = new Glimpse(Direction.N, 4);
        JSONObject expectedAction = new JSONObject("{ \"action\": \"glimpse\", \"parameters\": { \"direction\": \"N\", \"range\": 4 } }");
        JSONObject actionResult = action.act();

        assertEquals(expectedAction.getString("action"), actionResult.getString("action"));
        assertEquals(expectedAction.getJSONObject("parameters").getString("direction"), actionResult.getJSONObject("parameters").getString("direction"));
        assertEquals(expectedAction.getJSONObject("parameters").getInt("range"), actionResult.getJSONObject("parameters").getInt("range"));

        action = new Glimpse(Direction.E, 2);
        expectedAction = new JSONObject("{ \"action\": \"glimpse\", \"parameters\": { \"direction\": \"E\", \"range\": 2 } }");
        actionResult = action.act();

        assertEquals(expectedAction.getString("action"), actionResult.getString("action"));
        assertEquals(expectedAction.getJSONObject("parameters").getString("direction"), actionResult.getJSONObject("parameters").getString("direction"));
        assertEquals(expectedAction.getJSONObject("parameters").getInt("range"), actionResult.getJSONObject("parameters").getInt("range"));
    }
}