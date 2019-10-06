package f3ath.minesweeper;

final class Cell implements CellView {
    private State state = new Hidden();
    private final Content content;

    private Cell(Content content) {
        this.content = content;
    }

    static Cell bomb() {
        return new Cell(new Bomb());
    }

    static Cell free(short bombsAround) {
        return new Cell(new Free(bombsAround));
    }

    boolean isOpen() {
        return state.isOpen();
    }

    boolean hasBomb() {
        return content.isBomb();
    }

    void open() {
        state = new Open(content);
    }

    boolean hasNoBombsAround() {
        return content.getBombsAround() == 0;
    }

    @Override
    public <T> T render(CellViewRenderer<T> renderer) {
        return state.render(renderer);
    }

    private interface Content {

        boolean isBomb();

        short getBombsAround();

        <T> T render(CellViewRenderer<T> renderer);

    }

    private static class Bomb implements Content {

        @Override
        public boolean isBomb() {
            return true;
        }

        @Override
        public short getBombsAround() {
            throw new IllegalStateException();
        }

        @Override
        public <T> T render(CellViewRenderer<T> renderer) {
            return renderer.bomb();
        }

    }

    private static class Free implements Content {

        private final short bombsAround;

        Free(short bombsAround) {
            this.bombsAround = bombsAround;
        }

        @Override
        public boolean isBomb() {
            return false;
        }

        @Override
        public short getBombsAround() {
            return bombsAround;
        }

        @Override
        public <T> T render(CellViewRenderer<T> renderer) {
            return renderer.free(bombsAround);
        }

    }

    private interface State {
        boolean isOpen();

        <T> T render(CellViewRenderer<T> renderer);
    }

    private static class Hidden implements State {
        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public <T> T render(CellViewRenderer<T> renderer) {
            return renderer.unopened();
        }
    }

    private static class Open implements State {
        private final Content content;

        Open(Content content) {
            this.content = content;
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public <T> T render(CellViewRenderer<T> renderer) {
            return content.render(renderer);
        }
    }
}
