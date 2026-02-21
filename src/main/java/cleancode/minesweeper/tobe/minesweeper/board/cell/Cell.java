package cleancode.minesweeper.tobe.minesweeper.board.cell;

public interface Cell {

    boolean isChecked();

    boolean isLandMine();

    boolean hasLandMineCount();

    boolean isFlagged();

    boolean isOpened();

    void flag();

    void open();

    CellSnapshot getSnapshot();
}
