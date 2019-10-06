package f3ath.minesweeper;

import java.util.stream.Stream;

final public class Game {
    private final Grid<Cell> board;
    private State state;

    public Game(int width, int height, Stream<Coordinate> bombs) {
        final var bombMap = new Grid<>(width, height, c -> false)
                .modify(bombs, c -> true);
        board = bombMap
                .map((isBomb, c) -> isBomb ? Cell.bomb() : Cell.free(bombsAround(c, bombMap)));

        state = new InProgress();
    }

    public void click(Coordinate coordinate) {
        state = state.click(coordinate, board);
    }


    private short bombsAround(Coordinate coordinate, Grid<Boolean> bombs) {
        return (short) coordinate
                .neighbors()
                .filter(bombs::contains)
                .filter(bombs::get)
                .count();
    }

    public int getBoardWidth() {
        return board.getWidth();
    }

    public int getBoardHeight() {
        return board.getHeight();
    }

    public CellView getCell(Coordinate c) {
        return board.get(c);
    }

    public boolean isInProgress() {
        return state.isInProgress();
    }

    public boolean isLost() {
        return state.isLost();
    }

    private interface State {
        State click(Coordinate coordinate, Grid<Cell> board);

        boolean isInProgress();

        boolean isLost();
    }

    private static class InProgress implements State {

        @Override
        public State click(Coordinate coordinate, Grid<Cell> board) {
            final var cell = board.get(coordinate);
            cell.open();
            if (cell.hasBomb()) {
                revealBombs(board);
                return new GameLost();
            }
            if (cell.hasNoBombsAround()) {
                propagateClicks(coordinate, board);
            }
            return this;
        }

        private void revealBombs(Grid<Cell> board) {
            board
                    .cells()
                    .filter(Cell::hasBomb)
                    .forEach(Cell::open);
        }

        @Override
        public boolean isInProgress() {
            return true;
        }

        @Override
        public boolean isLost() {
            return false;
        }

        private void propagateClicks(Coordinate coordinate, Grid<Cell> board) {
            coordinate
                    .neighbors()
                    .filter(board::contains)
                    .filter(n -> !board.get(n).isOpen())
                    .forEach(c -> click(c, board));
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
        }
    }
}
