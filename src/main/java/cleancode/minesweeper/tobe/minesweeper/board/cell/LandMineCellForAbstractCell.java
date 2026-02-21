package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class LandMineCellForAbstractCell extends AbstractCell {

    private static final String LAND_MINE_SIGN = "☼";

    @Override
    public String openSign() {
        return LAND_MINE_SIGN;
    }

    @Override
    public boolean isLandMine() {
        return true;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }
}
