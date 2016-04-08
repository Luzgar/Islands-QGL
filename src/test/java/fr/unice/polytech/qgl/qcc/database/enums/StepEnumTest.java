package fr.unice.polytech.qgl.qcc.database.enums;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Kevin on 28/12/2015.
 */
public class StepEnumTest {

    @Test
    public void testNext() throws Exception {
        StepEnum stepEnum = StepEnum.MOVETODIRECTIONOFISLAND;

        assertEquals(StepEnum.MOVETODIRECTIONOFISLAND , stepEnum);

        stepEnum = stepEnum.next();
        assertEquals(StepEnum.MOVEEXTRIMITYISLAND , stepEnum);
        stepEnum = stepEnum.next();
        assertEquals(StepEnum.FINDCREEKS , stepEnum);
        stepEnum = stepEnum.next();
        assertEquals(StepEnum.LANDONISLAND , stepEnum);
        stepEnum = stepEnum.next();
        assertEquals(StepEnum.EXPLOREISLAND , stepEnum);
        stepEnum = stepEnum.next();
        assertEquals(null , stepEnum);
    }
}