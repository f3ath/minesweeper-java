package f3ath.minesweeper;

public interface CellView {
    boolean isOpen();

    boolean hasBomb();

    short bombsAround();
}
