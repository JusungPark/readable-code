package cleancode.minesweeper.tobe.cell;

public class NumberCell implements Cell {

    private final int nearbyLandMineCount;
    private final CellState state = CellState.initialize();

    public NumberCell(int nearbyLandMines) {
        this.nearbyLandMineCount = nearbyLandMines;
    }

    @Override
    public CellSnapshot getSnapshot() {
        if (state.isOpened()) {
            return CellSnapshot.number(nearbyLandMineCount);
        }

        if (state.isFlagged()) {
            return CellSnapshot.flag();
        }

        return CellSnapshot.unchecked();
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
