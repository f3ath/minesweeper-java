package f3ath.minesweeper;

import org.junit.jupiter.api.Test;

class GridTest {

    @Test
    void grid3x2ReadWrite() {

        final var grid = new Grid<>(3, 2, c -> c.toLinear(3));

    }

}
