package f3ath.minesweeper;

import java.util.stream.Stream;

public class Board {
    private final Grid<Cell> cells;

    public Board(int width, int height, Stream<Coordinate> bombs) {

        final var bombMap = new Grid<>(width, height, c -> false)
                .modify(bombs, c -> true);
        cells = bombMap
                .map((isBomb, c) -> isBomb ? Cell.bomb() : Cell.empty(countBombsAround(c, bombMap)));
    }

    private short countBombsAround(Coordinate coordinate, Grid<Boolean> bombs) {
        return (short) coordinate
                .neighbors(bombs)
                .filter(bombs::get)
                .count();
    }


    public void click(Coordinate coordinate) {
        final var cell = cells.get(coordinate);
        cell.open();
        if (!cell.hasBomb() && cell.numberOfBombsAround() == 0) {
            propagateClicks(coordinate);
        }

    }

    private void propagateClicks(Coordinate coordinate) {
        coordinate
                .neighbors(cells)
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

    public Grid<? extends CellView> getView() {
        return cells;
    }
}
