package f3ath.minesweeper;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FunctionalTest {

    @Test
    void game3x3UserLoses() {
        final var board = new Board(3, 3, Stream.of(new Coordinate(2, 2)));
        assertBoard(new Character[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '},
        }, board);

        board.click(new Coordinate(0, 1));

        assertBoard(new Character[][]{
                {'0', '0', '0'},
                {'0', '1', '1'},
                {'0', '1', ' '},
        }, board);

    }

    private void assertBoard(Character[][] expected, Board board) {
        final var actual = new Character[board.getWidth()][board.getHeight()];
        new Grid<>(board.getWidth(), board.getHeight(), board::getCell)
                .map(this::cellToChar)
                .forEachCell((c, cell) -> c.setIn(actual, cell));

        assertArrayEquals(expected, actual);
    }

    private char cellToChar(CellView cell) {
        if (cell.isOpen()) {
            if (cell.hasBomb()) {
                throw new IllegalStateException();
            }
            return String.valueOf(cell.numberOfBombsAround()).charAt(0);
        }
        return ' ';
    }
}
