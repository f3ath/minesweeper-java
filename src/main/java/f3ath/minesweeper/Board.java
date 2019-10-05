package f3ath.minesweeper;

import java.util.stream.Stream;

final public class Board {
    private final Grid<Cell> cells;

    public Board(int width, int height, Stream<Coordinate> bombs) {

        final var bombMap = new Grid<>(width, height, c -> false)
                .modify(bombs, c -> true);
        cells = bombMap
                .map((isBomb, c) -> isBomb ? Cell.bomb() : Cell.free(countBombsAround(c, bombMap)));
    }

    public void click(Coordinate coordinate) {
        final var cell = cells.get(coordinate);
        cell.open();
        if (cell.hasNoBombsAround()) {
            propagateClicks(coordinate);
        }
    }

    private short countBombsAround(Coordinate coordinate, Grid<Boolean> bombs) {
        return (short) coordinate
                .neighbors()
                .filter(bombs::contains)
                .filter(bombs::get)
                .count();
    }

    private void propagateClicks(Coordinate coordinate) {
        coordinate
                .neighbors()
                .filter(cells::contains)
                .filter(n -> !cells.get(n).isOpen())
                .forEach(this::click);
    }

    public int getWidth() {
        return cells.getWidth();
    }

    public int getHeight() {
        return cells.getHeight();
    }

    public CellView getCell(Coordinate c) {
        return cells.get(c);
    }
}
