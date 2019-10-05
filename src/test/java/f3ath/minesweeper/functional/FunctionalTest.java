package f3ath.minesweeper.functional;

import f3ath.minesweeper.Board;
import f3ath.minesweeper.CellView;
import f3ath.minesweeper.Coordinate;
import f3ath.minesweeper.Grid;
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

        board.click(new Coordinate(1, 1));

        assertBoard(new Character[][]{
                {' ', ' ', ' '},
                {' ', '1', ' '},
                {' ', ' ', ' '},
        }, board);

        board.click(new Coordinate(2, 2));

        assertBoard(new Character[][]{
                {' ', ' ', ' '},
                {' ', '1', ' '},
                {' ', ' ', '*'},
        }, board);
    }

    @Test
    void game3x3UserWins() {
        final var board = new Board(3, 3, Stream.of(new Coordinate(1, 2), new Coordinate(2, 2)));
        assertBoard(new Character[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '},
        }, board);

        board.click(new Coordinate(1, 1));

        assertBoard(new Character[][]{
                {' ', ' ', ' '},
                {' ', '2', ' '},
                {' ', ' ', ' '},
        }, board);

        board.click(new Coordinate(0, 0));

        assertBoard(new Character[][]{
                {'0', '0', '0'},
                {'1', '2', '2'},
                {' ', ' ', ' '},
        }, board);

        board.click(new Coordinate(0, 2));

        assertBoard(new Character[][]{
                {'0', '0', '0'},
                {'1', '2', '2'},
                {'1', ' ', ' '},
        }, board);
    }

    private void assertBoard(Character[][] expected, Board board) {
        final var actual = new Character[board.getWidth()][board.getHeight()];
        new Grid<>(board.getWidth(), board.getHeight(), board::getCell)
                .map(this::cellToChar)
                .forEachCell((c, cell) -> actual[c.getY()][c.getX()] = cell);

        assertArrayEquals(expected, actual);
    }

    private char cellToChar(CellView cell) {
        if (!cell.isOpen()) {
            return ' ';
        }
        if (cell.hasBomb()) {
            return '*';
        }
        return String.valueOf(cell.numberOfBombsAround()).charAt(0);

    }
}
