package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.AbstractCell;
import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

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

        for (int row = 0; row < boardRowSize; row++) {
            for (int col = 0; col < boardColSize; col++) {
                board[row][col] = new EmptyCell();
            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int landMineRow = ThreadLocalRandom.current().nextInt(boardRowSize);
            int landMineCol = ThreadLocalRandom.current().nextInt(boardColSize);
            board[landMineRow][landMineCol] = new LandMineCell();
            System.out.println("지뢰가 " + (char) ('a' + landMineCol) + (landMineRow + 1) + "에 설치되었습니다.");
        }

        for (int row = 0; row < boardRowSize; row++) {
            for (int col = 0; col < boardColSize; col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }
                final int nearbyLandMines = countNearbyLandMines(row, col);
                if (nearbyLandMines == 0) {
                    continue;
                }
                board[row][col] = new NumberCell(nearbyLandMines);
            }
        }
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return findCell(selectedRowIndex, selectedColIndex).isLandMine();
    }

    private int countNearbyLandMines(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < boardColSize && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < boardColSize && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < boardRowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < boardRowSize && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < boardRowSize && col + 1 < boardColSize && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    public int rowSize() {
        return this.boardRowSize;
    }

    public int colSize() {
        return this.boardColSize;
    }

    public String getSign(int rowIndex, int colIndex) {
        return findCell(rowIndex, colIndex).getSign();
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public void flag(int rowIndex, int colIndex) {
        findCell(rowIndex, colIndex).flag();
    }

    public void open(int rowIndex, int colIndex) {
        findCell(rowIndex, colIndex).open();
    }

    public void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= boardRowSize || col < 0 || col >= boardColSize) {
            return;
        }
        if (isOpenedCell(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        open(row, col);

        if (hasLandMineCountCell(row, col)) {
            return;
        }

        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

    private boolean hasLandMineCountCell(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
            .flatMap(Arrays::stream)
            .allMatch(Cell::isChecked);
    }
}
