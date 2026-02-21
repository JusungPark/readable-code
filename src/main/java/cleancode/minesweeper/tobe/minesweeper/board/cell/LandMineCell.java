package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class LandMineCell implements Cell {

    private final CellState state = CellState.initialize();

    @Override
    public CellSnapshot getSnapshot() {
        if (state.isOpened()) {
            return CellSnapshot.landMine();
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
