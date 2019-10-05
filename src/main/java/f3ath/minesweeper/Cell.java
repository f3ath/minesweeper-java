package f3ath.minesweeper;

final class Cell implements CellView {
    private boolean isOpen = false;
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
        return isOpen;
    }

    boolean hasBomb() {
        return content.isBomb();
    }

    void open() {
        isOpen = true;
    }

    boolean hasNoBombsAround() {
        return !hasBomb() && content.getBombsAround() == 0;
    }

    @Override
    public <T> T render(CellRenderer<T> renderer) {
        if (!isOpen()) {
            return renderer.unopened();
        }
        if (hasBomb()) {
            return renderer.bomb();
        }
        return renderer.free(content.getBombsAround());
    }

    private interface Content {
        boolean isBomb();

        short getBombsAround();
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
    }
}
