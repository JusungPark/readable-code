package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.Cells;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class GameBoard implements GameInitializable<Integer> {

    private final Cell[][] board;
    private final int boardRowSize;
    private final int boardColSize;

    public GameBoard(GameLevel gameLevel) {
        this.boardRowSize = gameLevel.getRowSize();
        this.boardColSize = gameLevel.getColSize();
        this.board = new Cell[boardRowSize][boardColSize];
        this.initialize(gameLevel.getLandMineCount());
    }

    @Override
    public void initialize(Integer landMineCount) {

        final CellPositions allPositions = CellPositions.from(this);

        initializeEmptyCells(allPositions);

        initializeLandMineCells(landMineCount, allPositions);

        initializeNumberCells(allPositions);

    }

    private void initializeNumberCells(CellPositions allPositions) {
        allPositions.stream()
            .filter(Predicate.not(this::isLandMineCellAt))
            .forEach(cellPosition -> {
                final int nearbyLandMines = countNearbyLandMines(cellPosition);
                if (nearbyLandMines > 0) {
                    this.updateCellAt(cellPosition, new NumberCell(nearbyLandMines));
                }
            });
    }

    private void initializeLandMineCells(Integer landMineCount, CellPositions allPositions) {
        final CellPositions landMinePositions = allPositions.extractRandomPositions(landMineCount);

        for (CellPosition cellPosition : landMinePositions) {
            updateCellAt(cellPosition, new LandMineCell());
            System.out.println("지뢰가 " + (char) ('a' + cellPosition.colIndex()) + (cellPosition.rowIndex() + 1) + "에 설치되었습니다.");
        }
    }

    private void initializeEmptyCells(CellPositions allPositions) {
        for (CellPosition cellPosition : allPositions) {
            updateCellAt(cellPosition, new EmptyCell());
        }
    }

    private void updateCellAt(CellPosition cellPosition, Cell cell) {
        board[cellPosition.rowIndex()][cellPosition.colIndex()] = cell;
    }

    public boolean isLandMineCellAt(CellPosition cellPosition) {
        return findCell(cellPosition).isLandMine();
    }

    private int countNearbyLandMines(CellPosition cellPosition) {
        return (int) calculateSurroundedPositions(cellPosition)
            .filter(this::isLandMineCellAt)
            .count();
    }

    private Stream<CellPosition> calculateSurroundedPositions(CellPosition cellPosition) {
        return RelativePosition.SURROUNDED_POSITION.stream()
            .filter(cellPosition::canCalculatePositionBy)
            .map(cellPosition::calculatePositionBy)
            .filter(Predicate.not(this::isInvalidCellPosition));
    }

    public int rowSize() {
        return this.boardRowSize;
    }

    public int colSize() {
        return this.boardColSize;
    }

    public String getSign(CellPosition cellPosition) {
        return findCell(cellPosition).getSign();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.rowIndex()][cellPosition.colIndex()];
    }

    public void flagAt(CellPosition cellPosition) {
        findCell(cellPosition).flag();
    }

    public void openAt(CellPosition cellPosition) {
        findCell(cellPosition).open();
    }

    public void openSurroundedCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCellAt(cellPosition)) {
            return;
        }

        openAt(cellPosition);

        if (hasLandMineCountCell(cellPosition)) {
            return;
        }

        calculateSurroundedPositions(cellPosition)
            .forEach(this::openSurroundedCells);
    }

    private boolean hasLandMineCountCell(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        return cellPosition.isRowIndexGTE(boardRowSize) || cellPosition.isColIndexGTE(boardColSize);
    }

    public Cells getCells() {
        return new Cells(Arrays.stream(board).flatMap(Arrays::stream).toList());
    }

    public boolean isAllCellChecked() {
        return this.getCells().isAllCellChecked();
    }
}
