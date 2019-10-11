package f3ath.minesweeper.game;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

final public class Coordinate {
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

    @Override
    public String toString() {
        return String.format("%d:%d", getX(), getY());
    }

    Stream<Coordinate> neighbors() {
        return neighbors(1);
    }

    Stream<Coordinate> neighbors(int distance) {
        return range(distance)
                .map(dX -> range(distance).map(dY -> move(dX, dY)))
                .flatMap(Function.identity())
                .filter(Predicate.not(this::isEqual));
    }

    private Coordinate move(int dX, int dY) {
        return new Coordinate(x + dX, y + dY);
    }

    private boolean isEqual(Coordinate c) {
        return x == c.x && y == c.y;
    }

    private Stream<Integer> range(int distance) {
        final var d = Math.max(0, distance);
        return Stream.iterate(-d, i -> i + 1).limit(2 * d + 1);
    }
}
