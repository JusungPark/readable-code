package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class Mineswepper implements GameRunnable {

    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Mineswepper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        this.gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    public void run() {
        outputHandler.showGameStartComments();

        while (true) {
            outputHandler.showBoard(gameBoard);

            if (doesUserWinTheGame()) {
                break;
            }
            if (doesUserLoseTheGame()) {
                break;
            }

            String cellInput = getCellInputFromUser();
            String actionInput = getActionInputFromUser();
            try {
                actOnCell(cellInput, actionInput);
            } catch (GameException e) {
                outputHandler.showExceptionMessage(e);
            } catch (Exception e) {
                outputHandler.showSimpleMessage("문제가 생겼습니다.");
                outputHandler.showExceptionMessage(e);
            }
        }
    }

    private void actOnCell(String cellInput, String actionInput) {
        final int selectedColIndex = boardIndexConverter.getSelectedColIndex(cellInput, gameBoard.colSize());
        final int selectedRowIndex = boardIndexConverter.getSelectedRowIndex(cellInput, gameBoard.rowSize());

        if (doesUserChooseToPlantFlag(actionInput)) {
            gameBoard.flag(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(actionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex, selectedColIndex);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        outputHandler.showCommentForWrongAction();
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserChooseToOpenCell(String actionInput) {
        return actionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String actionInput) {
        return actionInput.equals("2");
    }

    private String getActionInputFromUser() {
        outputHandler.printCommentForUserAction();
        return inputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        return inputHandler.getUserInput();
    }

    private boolean doesUserLoseTheGame() {
        if (gameStatus == -1) {
            outputHandler.showGameLosingComment();
            return true;
        }
        return false;
    }

    private boolean doesUserWinTheGame() {
        if (gameStatus == 1) {
            outputHandler.showGameWinningComment();
            return true;
        }
        return false;
    }

    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }
}
