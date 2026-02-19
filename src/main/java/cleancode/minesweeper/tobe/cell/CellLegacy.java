package cleancode.minesweeper.tobe.cell;

public class CellLegacy {

    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGH = "□";
    private static final String EMPTY_SIGH = "■";

    private int nearbyLandMineCount;

    private boolean landMine;
    private boolean flagged;
    private boolean opened;

    public CellLegacy() {
        this.nearbyLandMineCount = 0;
        this.landMine = false;
        this.flagged = false;
        this.opened = false;
    }

    public boolean isChecked() {
        if (isLandMine()) {
            return this.flagged || this.opened;
        }
        return this.opened;
    }

    @Override
    public String toString() {
        return this.getSign();
    }

    public String getSign() {
        if (this.isOpened()) {
            if (this.isLandMine()) {
                return LAND_MINE_SIGN;
            }
            if (this.hasLandMineCount()) {
                return String.valueOf(nearbyLandMineCount);
            }
            return EMPTY_SIGH;
        }

        if (this.flagged) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGH;
    }

    public void turnOnLandMine() {
        this.landMine = true;
    }

    public void updateNearbyLandMineCount(int nearbyLandMineCount) {
        this.nearbyLandMineCount = nearbyLandMineCount;
    }

    public void flag() {
        this.flagged = true;
    }

    public boolean isLandMine() {
        return this.landMine;
    }

    public void open() {
        this.opened = true;
    }

    public boolean isOpened() {
        return this.opened;
    }

    public boolean hasLandMineCount() {
        return this.nearbyLandMineCount > 0;
    }
}
