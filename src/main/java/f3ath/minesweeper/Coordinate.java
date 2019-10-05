package f3ath.minesweeper;

import java.util.function.Function;
import java.util.stream.Stream;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public <T> T getFrom(T[][] array) {
        return array[x][y];
    }

    public <T> void setIn(T[][] array, T value) {
        array[x][y] = value;
    }

    public boolean inBounds(Box box) {
        return x >= 0 && x < box.getWidth() && y >= 0 && y < box.getHeight();
    }

    public Coordinate move(int dX, int dY) {
        return new Coordinate(x + dX, y + dY);
    }

    public boolean isEqual(Coordinate c) {
        return x == c.x && y == c.y;
    }

    public boolean isNotEqual(Coordinate c) {
        return !isEqual(c);
    }

    public Stream<Coordinate> neighbors(Box box) {
        return deltas()
                .map(dX -> deltas().map(dY -> move(dX, dY)))
                .flatMap(Function.identity())
                .filter(this::isNotEqual)
                .filter(c -> c.inBounds(box));
    }

    private Stream<Integer> deltas() {
        return Stream.of(-1, 0, 1);
    }
}
