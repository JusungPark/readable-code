package cleancode.minesweeper.tobe.minesweeper.board.cell;

public abstract class AbstractCell {

    private static final String FLAG_SIGN = "⚑";
    private static final String UNCHECKED_SIGH = "□";

    private boolean flagged;
    private boolean opened;


    public boolean isChecked() {
        if (isLandMine()) {
            return this.isFlagged() || this.isOpened();
        }
        return this.isOpened();
    }

    @Override
    public String toString() {
        return this.getSign();
    }

    public String getSign() {
        if (this.isOpened()) {
            return this.openSign();
        }

        if (this.isFlagged()) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGH;
    }

    protected abstract String openSign();

    public abstract boolean isLandMine();

    public abstract boolean hasLandMineCount();

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
