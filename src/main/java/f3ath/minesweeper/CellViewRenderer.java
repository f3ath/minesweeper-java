package f3ath.minesweeper;

public interface CellViewRenderer<T> {
    T mine();

    T free(short minesAround);

    T unopened();
}
