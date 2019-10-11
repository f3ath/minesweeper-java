package f3ath.minesweeper.game;

final class Cell implements CellView {
    private State state = new Covered();
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

    boolean isCovered() {
        return state.isCovered();
    }

    boolean isMine() {
        return content.isMine();
    }

    void uncover() {
        state = new Uncovered(content);
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
        boolean isCovered();

        <T> T render(CellViewRenderer<T> renderer);
    }

    private static class Covered implements State {
        @Override
        public boolean isCovered() {
            return true;
        }

        @Override
        public <T> T render(CellViewRenderer<T> renderer) {
            return renderer.unopened();
        }
    }

    private static class Uncovered implements State {
        private final Content content;

        Uncovered(Content content) {
            this.content = content;
        }

        @Override
        public boolean isCovered() {
            return false;
        }

        @Override
        public <T> T render(CellViewRenderer<T> renderer) {
            return content.render(renderer);
        }
    }
}
