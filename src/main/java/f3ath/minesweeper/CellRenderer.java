package f3ath.minesweeper;

public interface CellRenderer<T> {
    T bomb();

    T free(short bombsAround);

    T unopened();
}
