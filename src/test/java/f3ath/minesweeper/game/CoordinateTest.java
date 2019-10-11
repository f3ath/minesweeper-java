package f3ath.minesweeper.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinateTest {

    @Test
    void have8NeighborsAtDistance1() {
        assertEquals(8, new Coordinate(2, 3).neighbors().count());
    }

    @Test
    void have24NeighborsAtDistance2() {
        assertEquals(24, new Coordinate(2, 3).neighbors(2).count());
    }

    @Test
    void haveNoNeighborsAtDistance0() {
        assertEquals(0, new Coordinate(2, 3).neighbors(0).count());
    }

    @Test
    void haveNoNeighborsAtNegativeDistance() {
        assertEquals(0, new Coordinate(2, 3).neighbors(-5).count());
    }
}
