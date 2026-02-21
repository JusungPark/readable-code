package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class NumberCellForAbstractCell extends AbstractCell {

    private final int nearbyLandMineCount;

    public NumberCellForAbstractCell(int nearbyLandMines) {
        this.nearbyLandMineCount = nearbyLandMines;
    }

    @Override
    public String openSign() {
        return String.valueOf(this.nearbyLandMineCount);
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
    }
}
