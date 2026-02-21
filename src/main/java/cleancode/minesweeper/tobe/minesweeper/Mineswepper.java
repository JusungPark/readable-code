package cleancode.minesweeper.tobe.minesweeper;

import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import cleancode.minesweeper.tobe.minesweeper.exception.GameException;
import cleancode.minesweeper.tobe.minesweeper.io.InputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.OutputHandler;
import cleancode.minesweeper.tobe.minesweeper.user.UserAction;

public class Mineswepper implements GameRunnable {

    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public Mineswepper(GameConfig gameConfig) {
        this.gameBoard = new GameBoard(gameConfig.gameLevel());
        this.inputHandler = gameConfig.inputHandler();
        this.outputHandler = gameConfig.outputHandler();
    }

    @Override
    public void run() {
        outputHandler.showGameStartComments();

        while (gameBoard.isInProgress()) {
            outputHandler.showBoard(gameBoard);

            try {
                CellPosition cellPosition = getCellInputFromUser();
                UserAction actionInput = getActionInputFromUser();
                actOnCell(cellPosition, actionInput);
            } catch (GameException e) {
                outputHandler.showExceptionMessage(e);
            } catch (Exception e) {
                outputHandler.showSimpleMessage("문제가 생겼습니다.");
                outputHandler.showExceptionMessage(e);
            }
        }

        outputHandler.showBoard(gameBoard);

        if (gameBoard.isLoseStatus()) {
            outputHandler.showGameLosingComment();
        }

        if (gameBoard.isWinStatus()) {
            outputHandler.showGameWinningComment();
        }
    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        final CellPosition cellPosition = inputHandler.getCellPositionFromUser();

        if (gameBoard.isInvalidCellPosition(cellPosition)) {
            throw new GameException(new IllegalArgumentException("잘못된 좌표입니다. 입력값: " + cellPosition));
        }

        return cellPosition;
    }

    private UserAction getActionInputFromUser() {
        outputHandler.printCommentForUserAction();
        return inputHandler.getActionFromUser();
    }

    private void actOnCell(CellPosition cellPosition, UserAction actionInput) {
        if (doesUserChooseToPlantFlag(actionInput)) {
            gameBoard.flagAt(cellPosition);
            return;
        }

        if (doesUserChooseToOpenCell(actionInput)) {
            gameBoard.openAt(cellPosition);
            return;
        }

        outputHandler.showCommentForWrongAction();
    }

    private boolean doesUserChooseToPlantFlag(UserAction actionInput) {
        return actionInput == UserAction.FLAG;
    }

    private boolean doesUserChooseToOpenCell(UserAction actionInput) {
        return actionInput == UserAction.OPEN;
    }
}
