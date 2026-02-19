package cleancode.minesweeper.tobe.cell;

public interface Cell {

    String FLAG_SIGN = "⚑";
    String UNCHECKED_SIGH = "□";

    boolean isChecked();

    String getSign();

    boolean isLandMine();

    boolean hasLandMineCount();

    boolean isFlagged();

    boolean isOpened();

    void flag();

    void open();
}
