package f3ath.minesweeper.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GridTest {

    @Test
    void grid3x2ReadWrite() {

        // 0 1 2 3
        // 4 5 6 7

        final var grid = new Grid<>(4, 2, c -> 4 * c.getY() + c.getX());

        assertEquals(0, (int) grid.get(new Coordinate(0, 0))); // upper left
        assertEquals(3, (int) grid.get(new Coordinate(3, 0))); // upper right
        assertEquals(4, (int) grid.get(new Coordinate(0, 1))); // lower left
        assertEquals(7, (int) grid.get(new Coordinate(3, 1))); // lower right
    }
}
