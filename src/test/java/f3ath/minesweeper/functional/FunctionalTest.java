package f3ath.minesweeper.functional;

import f3ath.minesweeper.Game;
import f3ath.minesweeper.CellViewRenderer;
import f3ath.minesweeper.Coordinate;
import f3ath.minesweeper.Grid;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalTest {
    private static final CharRenderer renderer = new CharRenderer();

    @Test
    void game3x3UserLoses() {
        final var game = new Game(3, 3, Stream.of(new Coordinate(2, 2), new Coordinate(2, 0)));

        assertTrue(game.isInProgress());
        assertBoard(new Character[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '},
        }, game);

        game.click(new Coordinate(1, 1));

        assertTrue(game.isInProgress());
        assertBoard(new Character[][]{
                {' ', ' ', ' '},
                {' ', '2', ' '},
                {' ', ' ', ' '},
        }, game);

        game.click(new Coordinate(2, 2));

        assertFalse(game.isInProgress());
        assertTrue(game.isLost());
        assertBoard(new Character[][]{
                {' ', ' ', '*'},
                {' ', '2', ' '},
                {' ', ' ', '*'},
        }, game);
    }

    @Test
    void game3x3UserWins() {
        final var board = new Game(3, 3, Stream.of(new Coordinate(1, 2), new Coordinate(2, 2)));
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

    private void assertBoard(Character[][] expected, Game game) {
        final var actual = new Character[game.getBoardHeight()][game.getBoardWidth()];
        new Grid<>(game.getBoardWidth(), game.getBoardHeight(), game::getCell)
                .forEachCell((c, cell) -> actual[c.getY()][c.getX()] = cell.render(renderer));

        assertArrayEquals(expected, actual);
    }

    private static class CharRenderer implements CellViewRenderer<Character> {

        @Override
        public Character bomb() {
            return '*';
        }

        @Override
        public Character free(short bombsAround) {
            return String.valueOf(bombsAround).charAt(0);
        }

        @Override
        public Character unopened() {
            return ' ';
        }
    }
}
