package cleancode.minesweeper.tobe.cell;

public class LandMineCell implements Cell {

    private static final String LAND_MINE_SIGN = "☼";
    private final CellState state = CellState.initialize();

    @Override
    public String getSign() {
        if (state.isOpened()) {
            return LAND_MINE_SIGN;
        }

        if (state.isFlagged()) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGH;
    }

    @Override
    public void flag() {
        state.flag();
    }

    @Override
    public void open() {
        state.open();
    }

    @Override
    public boolean isLandMine() {
        return true;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public boolean isFlagged() {
        return state.isFlagged();
    }

    @Override
    public boolean isOpened() {
        return state.isOpened();
    }

    @Override
    public boolean isChecked() {
        return this.isFlagged() || this.isOpened();
    }
}
