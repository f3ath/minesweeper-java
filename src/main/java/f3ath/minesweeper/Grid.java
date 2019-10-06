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

    public <U> Grid<U> map(Function<T, U> mapper) {
        return map((t, c) -> mapper.apply(t));
    }

    public <U> Grid<U> map(Mapper<T, Coordinate, U> mapper) {
        return new Grid<>(width, height, c -> mapper.apply(get(c), c));
    }

    public Grid<T> modify(Stream<Coordinate> coordinates, Mapper<T, Coordinate, T> mutator) {
        final var newGrid = map(Function.identity());
        coordinates.forEach(c -> newGrid.grid[c.getY()][c.getX()] = mutator.apply(newGrid.get(c), c));
        return newGrid;
    }

    public Grid<T> modify(Stream<Coordinate> coordinates, Function<T, T> mutator) {
        return modify(coordinates, (t, c) -> mutator.apply(t));
    }

    public void forEachCell(Walker<Coordinate, T> walker) {
        coordinates().forEach(c -> walker.apply(c, get(c)));
    }


    public T get(Coordinate c) {
        return grid[c.getY()][c.getX()];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(Coordinate c) {
        return c.getX() >= 0 && c.getX() < getWidth() && c.getY() >= 0 && c.getY() < getHeight();
    }

    public Stream<T> cells() {
        return coordinates().map(this::get);
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
