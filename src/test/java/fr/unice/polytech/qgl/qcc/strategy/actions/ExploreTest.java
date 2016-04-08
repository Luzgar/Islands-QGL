package fr.unice.polytech.qgl.qcc.strategy.actions;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 26/12/2015.
 */
public class ExploreTest {

    @Test
    public void testAct() throws Exception {
        Action action = new Explore();
        JSONObject expectedAction = new JSONObject("{ \"action\": \"explore\" }");
        JSONObject actionResult = action.act();

        assertEquals(expectedAction.getString("action"), actionResult.getString("action"));
    }
}