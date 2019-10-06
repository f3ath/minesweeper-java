package f3ath.minesweeper;

public interface CellViewRenderer<T> {
    T bomb();

    T free(short bombsAround);

    T unopened();
}
