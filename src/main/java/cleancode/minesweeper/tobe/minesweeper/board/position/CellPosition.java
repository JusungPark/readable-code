package cleancode.minesweeper.tobe.minesweeper.board.position;

public record CellPosition(int rowIndex, int colIndex) {

    public CellPosition {
        if (rowIndex < 0 || colIndex < 0) {
            throw new IllegalArgumentException("CellPosition의 rowIndex와 colIndex는 음수일 수 없습니다.");
        }
    }

    public boolean isRowIndexGTE(int boardRowSize) {
        return this.rowIndex >= boardRowSize;
    }

    public boolean isColIndexGTE(int boardColSize) {
        return this.colIndex >= boardColSize;
    }

    public CellPosition calculatePositionBy(RelativePosition relativePosition) {
        if (!this.canCalculatePositionBy(relativePosition)) {
            throw new IllegalArgumentException("CellPosition의 rowIndex와 colIndex는 음수일 수 없습니다.");
        }

        return new CellPosition(
            this.rowIndex() + relativePosition.deltaRow(),
            this.colIndex() + relativePosition.deltaCol()
        );
    }

    public boolean canCalculatePositionBy(RelativePosition relativePosition) {
        return this.rowIndex() + relativePosition.deltaRow() >= 0 && this.colIndex() + relativePosition.deltaCol() >= 0;
    }
}
