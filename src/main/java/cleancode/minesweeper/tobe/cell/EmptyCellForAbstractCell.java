package cleancode.minesweeper.tobe.cell;

public class EmptyCellForAbstractCell extends AbstractCell {

    private static final String EMPTY_SIGH = "■";

    @Override
    public String openSign() {
        return EMPTY_SIGH;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }
}
