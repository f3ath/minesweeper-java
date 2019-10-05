package f3ath.minesweeper;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

public class Grid<T> implements Box {
    private final int width;
    private final int height;
    private final ArrayList<ArrayList<T>> grid;

    public Grid(int width, int height, Function<Coordinate, T> init) {
        this.width = width;
        this.height = height;
        grid = new ArrayList<>();
        coordinateStream().forEach(c -> c.setIn(grid, init.apply(c)));
    }

    public <U> Grid<U> map(Function<T, U> mapper) {
        return map((t, c) -> mapper.apply(t));
    }

    public <U> Grid<U> map(Mapper<T, Coordinate, U> mapper) {
        return new Grid<>(width, height, c -> mapper.apply(c.getFrom(grid), c));
    }

    public Grid<T> modify(Stream<Coordinate> coordinates, Mapper<T, Coordinate, T> mutator) {
        final var newGrid = map(Function.identity());
        coordinates.forEach(c -> c.setIn(newGrid.grid, mutator.apply(c.getFrom(newGrid.grid), c)));
        return newGrid;
    }

    public Grid<T> modify(Stream<Coordinate> coordinates, Function<T, T> mutator) {
        return modify(coordinates, (t, c) -> mutator.apply(t));
    }

    public void forEachCell(Walker<Coordinate, T> walker) {
        coordinateStream().forEach(c -> walker.apply(c, c.getFrom(grid)));
    }


    public Stream<Coordinate> coordinateStream() {
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

    public T get(Coordinate c) {
        return c.getFrom(grid);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
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
