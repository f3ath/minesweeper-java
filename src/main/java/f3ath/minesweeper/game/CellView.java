package f3ath.minesweeper.game;

public interface CellView {
    <T> T render(CellViewRenderer<T> renderer);
}
