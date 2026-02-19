package cleancode.minesweeper.tobe.cell;

public class EmptyCell implements Cell {

    private static final String EMPTY_SIGH = "■";
    private final CellState state = CellState.initialize();

    @Override
    public String getSign() {
        if (state.isOpened()) {
            return EMPTY_SIGH;
        }

        if (state.isFlagged()) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGH;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
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
