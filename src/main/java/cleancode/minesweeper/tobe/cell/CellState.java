package cleancode.minesweeper.tobe.cell;

public class CellState {

    private boolean flagged;
    private boolean opened;

    private CellState(boolean flagged, boolean opened) {
        this.flagged = flagged;
        this.opened = opened;
    }

    public static CellState initialize() {
        return new CellState(false, false);
    }

    public void flag() {
        this.flagged = true;
    }

    public void open() {
        this.opened = true;
    }

    public boolean isOpened() {
        return this.opened;
    }

    public boolean isFlagged() {
        return this.flagged;
    }
}
