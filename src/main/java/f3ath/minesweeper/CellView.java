package f3ath.minesweeper;

public interface CellView {
    <T> T render(CellRenderer<T> renderer);
}
