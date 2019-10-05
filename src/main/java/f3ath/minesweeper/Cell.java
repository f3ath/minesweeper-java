package f3ath.minesweeper;

class Cell implements CellView {
    private boolean isOpen = false;
    private final Content content;

    private Cell(Content content) {
        this.content = content;
    }

    static Cell bomb() {
        return new Cell(new Bomb());
    }

    static Cell empty(short bombsAround) {
        return new Cell(new Count(bombsAround));
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean hasBomb() {
        return content.isBomb();
    }

    public short numberOfBombsAround() {
        return content.getBombsAround();
    }

    void open() {
        isOpen = true;
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

    private static class Count implements Content {
        private final short bombsAround;

        Count(short bombsAround) {
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
