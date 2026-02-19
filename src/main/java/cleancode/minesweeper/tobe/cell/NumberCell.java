package cleancode.minesweeper.tobe.cell;

public class NumberCell implements Cell {

    private final int nearbyLandMineCount;
    private final CellState state = CellState.initialize();

    public NumberCell(int nearbyLandMines) {
        this.nearbyLandMineCount = nearbyLandMines;
    }

    @Override
    public String getSign() {
        if (state.isOpened()) {
            return String.valueOf(this.nearbyLandMineCount);
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
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
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
        return this.isOpened();
    }
}
