package cleancode.minesweeper.tobe.minesweeper.board.cell;

public record CellSnapshot(CellSnapshotStatus status, int nearByLandMineCount) {

    public static CellSnapshot empty() {
        return new CellSnapshot(CellSnapshotStatus.EMPTY, 0);
    }

    public static CellSnapshot unchecked() {
        return new CellSnapshot(CellSnapshotStatus.UNCHECKED, 0);
    }

    public static CellSnapshot flag() {
        return new CellSnapshot(CellSnapshotStatus.FLAG, 0);
    }

    public static CellSnapshot landMine() {
        return new CellSnapshot(CellSnapshotStatus.LAND_MINE, 0);
    }

    public static CellSnapshot number(int nearByLandMineCount) {
        return new CellSnapshot(CellSnapshotStatus.NUMBER, nearByLandMineCount);
    }
}
