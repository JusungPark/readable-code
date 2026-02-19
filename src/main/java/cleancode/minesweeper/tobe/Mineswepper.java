package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;

public class Mineswepper implements GameRunnable {

    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
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

            try {
                CellPosition cellPosition = getCellInputFromUser();
                String actionInput = getActionInputFromUser();
                actOnCell(cellPosition, actionInput);
            } catch (GameException e) {
                outputHandler.showExceptionMessage(e);
            } catch (Exception e) {
                outputHandler.showSimpleMessage("문제가 생겼습니다.");
                outputHandler.showExceptionMessage(e);
            }
        }
    }

    private void actOnCell(CellPosition cellPosition, String actionInput) {
        if (doesUserChooseToPlantFlag(actionInput)) {
            gameBoard.flagAt(cellPosition);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(actionInput)) {
            if (gameBoard.isLandMineCellAt(cellPosition)) {
                gameBoard.openAt(cellPosition);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(cellPosition);
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
        return inputHandler.getInputFromUser();
    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        final CellPosition cellPosition = inputHandler.getCellPositionFromUser();

        if (gameBoard.isInvalidCellPosition(cellPosition)) {
            throw new GameException(new IllegalArgumentException("잘못된 좌표입니다. 입력값: " + cellPosition));
        }

        return cellPosition;
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
