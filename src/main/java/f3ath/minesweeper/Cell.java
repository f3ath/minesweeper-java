package f3ath.minesweeper;

final class Cell implements CellView {
    private State state = new Hidden();
    private final Content content;

    private Cell(Content content) {
        this.content = content;
    }

    static Cell mine() {
        return new Cell(new Mine());
    }

    static Cell free(short minesAround) {
        return new Cell(new Free(minesAround));
    }

    boolean isOpen() {
        return state.isOpen();
    }

    boolean isMine() {
        return content.isMine();
    }

    void open() {
        state = new Open(content);
    }

    boolean hasNoMinesAround() {
        return content.getMinesAround() == 0;
    }

    @Override
    public <T> T render(CellViewRenderer<T> renderer) {
        return state.render(renderer);
    }

    private interface Content {

        boolean isMine();

        short getMinesAround();

        <T> T render(CellViewRenderer<T> renderer);

    }

    private static class Mine implements Content {

        @Override
        public boolean isMine() {
            return true;
        }

        @Override
        public short getMinesAround() {
            throw new IllegalStateException();
        }

        @Override
        public <T> T render(CellViewRenderer<T> renderer) {
            return renderer.mine();
        }

    }

    private static class Free implements Content {

        private final short minesAround;

        Free(short minesAround) {
            this.minesAround = minesAround;
        }

        @Override
        public boolean isMine() {
            return false;
        }

        @Override
        public short getMinesAround() {
            return minesAround;
        }

        @Override
        public <T> T render(CellViewRenderer<T> renderer) {
            return renderer.free(minesAround);
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
