package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.minesweeper.board.cell.Cell;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.Cells;
import cleancode.minesweeper.tobe.minesweeper.board.cell.EmptyCell;
import cleancode.minesweeper.tobe.minesweeper.board.cell.LandMineCell;
import cleancode.minesweeper.tobe.minesweeper.board.cell.NumberCell;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class GameBoard implements GameInitializable<Integer> {

    private final Cell[][] board;
    private final int boardRowSize;
    private final int boardColSize;

    private GameStatus gameStatus;

    public GameBoard(GameLevel gameLevel) {
        this.boardRowSize = gameLevel.getRowSize();
        this.boardColSize = gameLevel.getColSize();
        this.board = new Cell[boardRowSize][boardColSize];
        this.initialize(gameLevel.getLandMineCount());
    }

    @Override
    public void initialize(Integer landMineCount) {

        initializeGameStatus();

        final CellPositions allPositions = CellPositions.from(this);

        initializeEmptyCells(allPositions);

        initializeLandMineCells(landMineCount, allPositions);

        initializeNumberCells(allPositions);

    }

    public void flagAt(CellPosition cellPosition) {
        findCell(cellPosition).flag();
        checkIfGameIsOver();
    }

    public void openAt(CellPosition cellPosition) {
        if (this.isLandMineCellAt(cellPosition)) {
            this.openOneCellAt(cellPosition);
            this.changeGameStatusToLose();
            return;
        }

        this.openSurroundedCells(cellPosition);
        this.checkIfGameIsOver();
    }

    private void recursiveOpenSurroundedCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCellAt(cellPosition)) {
            return;
        }

        openOneCellAt(cellPosition);

        if (hasLandMineCountCell(cellPosition)) {
            return;
        }

        calculateSurroundedPositions(cellPosition)
            .forEach(this::recursiveOpenSurroundedCells);
    }

    private void openSurroundedCells(CellPosition cellPosition) {
        final Deque<CellPosition> stack = new ArrayDeque<>();
        stack.push(cellPosition);
        while (!stack.isEmpty()) {
            openAndPushCellAt(stack);
        }
    }

    private void openAndPushCellAt(Deque<CellPosition> stack) {
        final CellPosition currentCellPosition = stack.pop();
        if (isOpenedCell(currentCellPosition)) {
            return;
        }
        if (isLandMineCellAt(currentCellPosition)) {
            return;
        }

        openOneCellAt(currentCellPosition);

        if (hasLandMineCountCell(currentCellPosition)) {
            return;
        }

        calculateSurroundedPositions(currentCellPosition)
            .forEach(stack::push);
    }

    private void openOneCellAt(CellPosition cellPosition) {
        findCell(cellPosition).open();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        return cellPosition.isRowIndexGTE(boardRowSize) || cellPosition.isColIndexGTE(boardColSize);
    }

    public boolean isInProgress() {
        return gameStatus == GameStatus.IN_PROGRESS;
    }

    public boolean isWinStatus() {
        return gameStatus == GameStatus.WIN;
    }

    public boolean isLoseStatus() {
        return gameStatus == GameStatus.LOSE;
    }

    public CellSnapshot getSnapshot(CellPosition cellPosition) {
        return findCell(cellPosition).getSnapshot();
    }

    public int rowSize() {
        return this.boardRowSize;
    }

    public int colSize() {
        return this.boardColSize;
    }

    private void initializeGameStatus() {
        gameStatus = GameStatus.IN_PROGRESS;
    }

    private void initializeEmptyCells(CellPositions allPositions) {
        for (CellPosition cellPosition : allPositions) {
            updateCellAt(cellPosition, new EmptyCell());
        }
    }

    private void initializeLandMineCells(Integer landMineCount, CellPositions allPositions) {
        final CellPositions landMinePositions = allPositions.extractRandomPositions(landMineCount);

        for (CellPosition cellPosition : landMinePositions) {
            updateCellAt(cellPosition, new LandMineCell());
            System.out.println("지뢰가 " + (char) ('a' + cellPosition.colIndex()) + (cellPosition.rowIndex() + 1) + "에 설치되었습니다.");
        }
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

    private int countNearbyLandMines(CellPosition cellPosition) {
        return (int) calculateSurroundedPositions(cellPosition)
            .filter(this::isLandMineCellAt)
            .count();
    }

    private void updateCellAt(CellPosition cellPosition, Cell cell) {
        board[cellPosition.rowIndex()][cellPosition.colIndex()] = cell;
    }

    private Stream<CellPosition> calculateSurroundedPositions(CellPosition cellPosition) {
        return RelativePosition.SURROUNDED_POSITION.stream()
            .filter(cellPosition::canCalculatePositionBy)
            .map(cellPosition::calculatePositionBy)
            .filter(Predicate.not(this::isInvalidCellPosition));
    }

    private boolean isLandMineCellAt(CellPosition cellPosition) {
        return findCell(cellPosition).isLandMine();
    }

    private boolean hasLandMineCountCell(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }

    private Cells getCells() {
        return new Cells(Arrays.stream(board).flatMap(Arrays::stream).toList());
    }

    private boolean isAllCellChecked() {
        return this.getCells().isAllCellChecked();
    }

    private void changeGameStatusToWin() {
        gameStatus = GameStatus.WIN;
    }

    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.rowIndex()][cellPosition.colIndex()];
    }

    private void checkIfGameIsOver() {
        if (this.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }
}
