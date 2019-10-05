package f3ath.minesweeper;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public Coordinate move(int dX, int dY) {
        return new Coordinate(x + dX, y + dY);
    }

    public boolean isEqual(Coordinate c) {
        return x == c.x && y == c.y;
    }

    public Stream<Coordinate> neighbors() {
        return neighbors(1);
    }

    public Stream<Coordinate> neighbors(int distance) {
        return range(distance)
                .map(dX -> range(distance).map(dY -> move(dX, dY)))
                .flatMap(Function.identity())
                .filter(Predicate.not(this::isEqual));
    }

    int toLinear(int width) {
        return width * y + x;
    }

    @Override
    public String toString() {
        return String.format("%d:%d", getX(), getY());
    }

    private Stream<Integer> range(int distance) {
        final var d = Math.max(0, distance);
        return Stream.iterate(-d, i -> i + 1).limit(2 * d + 1);
    }
}
