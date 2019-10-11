package f3ath.minesweeper.game;

public interface CellViewRenderer<T> {
    T mine();

    T free(short minesAround);

    T unopened();
}
