package f3ath.minesweeper;

import java.util.function.Function;
import java.util.stream.Stream;

public class Grid<T> {
    private final int width;
    private final int height;
    private final T[][] grid;

    public Grid(int width, int height, Function<Coordinate, T> init) {
        this.width = width;
        this.height = height;
        grid = (T[][]) new Object[height][width];
        coordinates().forEach(c -> grid[c.getY()][c.getX()] = init.apply(c));
    }

    public void forEachCell(Walker<Coordinate, T> walker) {
        coordinates().forEach(c -> walker.apply(c, get(c)));
    }

    <U> Grid<U> map(Mapper<T, Coordinate, U> mapper) {
        return new Grid<>(width, height, c -> mapper.apply(get(c), c));
    }

    Grid<T> modify(Stream<Coordinate> coordinates, Function<T, T> mutator) {
        return modify(coordinates, (t, c) -> mutator.apply(t));
    }


    T get(Coordinate c) {
        return grid[c.getY()][c.getX()];
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    boolean contains(Coordinate c) {
        return c.getX() >= 0 && c.getX() < getWidth() && c.getY() >= 0 && c.getY() < getHeight();
    }

    Stream<T> cells() {
        return coordinates().map(this::get);
    }

    private Grid<T> modify(Stream<Coordinate> coordinates, Mapper<T, Coordinate, T> mutator) {
        final var newGrid = map(Function.identity());
        coordinates.forEach(c -> newGrid.grid[c.getY()][c.getX()] = mutator.apply(newGrid.get(c), c));
        return newGrid;
    }

    private <U> Grid<U> map(Function<T, U> mapper) {
        return map((t, c) -> mapper.apply(t));
    }

    private Stream<Coordinate> coordinates() {
        return range(width)
                .map(x ->
                        range(height)
                                .map(y -> new Coordinate(x, y))
                )
                .flatMap(Function.identity());
    }

    private Stream<Integer> range(int limit) {
        return Stream
                .iterate(0, i -> i + 1)
                .limit(limit);
    }

    @FunctionalInterface
    public interface Walker<C, T> {
        void apply(C c, T t);
    }

    @FunctionalInterface
    public interface Mapper<C, T, U> {
        U apply(C c, T t);
    }
}
