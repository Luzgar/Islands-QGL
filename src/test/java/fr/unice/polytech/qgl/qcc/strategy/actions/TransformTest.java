package fr.unice.polytech.qgl.qcc.strategy.actions;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Unreal Swing
 */
public class TransformTest {

    @Test
    public void testAct(){
        Action transform = new Transform("WOOD", 20);

        assertEquals(transform.act().getString("action"), "transform");
        assertEquals(transform.act().getJSONObject("parameters").getInt("WOOD"), 20);


        transform = new Transform("QUARTZ", "WOOD", 100, 50);

        assertEquals(transform.act().getString("action"), "transform");
        assertEquals(transform.act().getJSONObject("parameters").getInt("WOOD"), 50);
        assertEquals(transform.act().getJSONObject("parameters").getInt("QUARTZ"), 100);
    }

}