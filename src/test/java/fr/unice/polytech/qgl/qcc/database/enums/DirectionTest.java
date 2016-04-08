package fr.unice.polytech.qgl.qcc.database.enums;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Kevin on 28/12/2015.
 */
public class DirectionTest {

    @Test
    public void testClockwise() throws Exception {
        Direction direction = Direction.N;
        assertEquals(Direction.N, direction);

        direction = direction.clockwise();
        assertEquals(Direction.E, direction);
        direction = direction.clockwise();
        assertEquals(Direction.S, direction);
        direction = direction.clockwise();
        assertEquals(Direction.W, direction);
        direction = direction.clockwise();
        assertEquals(Direction.N, direction);
    }

    @Test
    public void testAnticlockwise() throws Exception {
        Direction direction = Direction.N;
        assertEquals(Direction.N, direction);

        direction = direction.anticlockwise();
        assertEquals(Direction.W, direction);
        direction = direction.anticlockwise();
        assertEquals(Direction.S, direction);
        direction = direction.anticlockwise();
        assertEquals(Direction.E, direction);
        direction = direction.anticlockwise();
        assertEquals(Direction.N, direction);
    }

    @Test
    public void testToString() throws Exception {
        Direction direction = Direction.N;
        assertEquals("N", direction.toString());
        direction = Direction.E;
        assertEquals("E", direction.toString());
        direction = Direction.S;
        assertEquals("S", direction.toString());
        direction = Direction.W;
        assertEquals("W", direction.toString());
    }
}