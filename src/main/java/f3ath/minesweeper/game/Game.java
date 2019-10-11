package f3ath.minesweeper.game;

import java.util.function.Predicate;
import java.util.stream.Stream;

final public class Game {
    private final Grid<Cell> board;
    private State state;

    public Game(int width, int height, Stream<Coordinate> mines) {
        board = new MineField(width, height, mines).generateBoard();
        state = new InProgress();
    }

    public void click(Coordinate coordinate) {
        state = state.click(coordinate, board);
    }

    public Grid<? extends CellView> getBoard() {
        return board;
    }

    public boolean isInProgress() {
        return state.isInProgress();
    }

    public boolean isLost() {
        return state.isLost();
    }

    public boolean isWon() {
        return state.isWon();
    }

    private static class MineField {
        private final Grid<Boolean> mines;

        MineField(int width, int height, Stream<Coordinate> mines) {
            this.mines = new Grid<>(width, height, c -> false).mutated(mines, c -> true);
        }

        Grid<Cell> generateBoard() {
            return mines.map((isMine, c) -> isMine ? Cell.mine() : Cell.free(minesAround(c)));
        }

        private short minesAround(Coordinate coordinate) {
            return (short) coordinate
                    .neighbors()
                    .filter(mines::contains)
                    .filter(mines::get)
                    .count();
        }

    }

    private interface State {
        State click(Coordinate coordinate, Grid<Cell> board);

        boolean isInProgress();

        boolean isLost();

        boolean isWon();
    }

    private static class GameWon implements State {
        @Override
        public State click(Coordinate coordinate, Grid<Cell> board) {
            return this;
        }

        @Override
        public boolean isInProgress() {
            return false;
        }

        @Override
        public boolean isLost() {
            return false;
        }

        @Override
        public boolean isWon() {
            return true;
        }
    }

    private static class GameLost implements State {
        @Override
        public State click(Coordinate coordinate, Grid<Cell> board) {
            return this;
        }

        @Override
        public boolean isInProgress() {
            return false;
        }

        @Override
        public boolean isLost() {
            return true;
        }

        @Override
        public boolean isWon() {
            return false;
        }
    }

    private static class InProgress implements State {

        @Override
        public State click(Coordinate coordinate, Grid<Cell> board) {
            final var cell = board.get(coordinate);
            cell.uncover();
            if (cell.isMine()) {
                revealAllMines(board);
                return new GameLost();
            }
            if (cell.hasNoMinesAround()) {
                propagateClicks(coordinate, board);
            }
            if (board.cells().filter(Predicate.not(Cell::isMine)).noneMatch(Cell::isCovered)) {
                return new GameWon();
            }
            return this;
        }

        @Override
        public boolean isInProgress() {
            return true;
        }

        @Override
        public boolean isLost() {
            return false;
        }

        @Override
        public boolean isWon() {
            return false;
        }

        private void revealAllMines(Grid<Cell> board) {
            board
                    .cells()
                    .filter(Cell::isMine)
                    .forEach(Cell::uncover);
        }

        private void propagateClicks(Coordinate coordinate, Grid<Cell> board) {
            coordinate
                    .neighbors()
                    .filter(board::contains)
                    .filter(n -> board.get(n).isCovered())
                    .forEach(c -> click(c, board));
        }
    }
}
