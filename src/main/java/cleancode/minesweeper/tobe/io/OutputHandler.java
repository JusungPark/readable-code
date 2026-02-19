package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;

public interface OutputHandler {

    void showGameStartComments();

    void showBoard(GameBoard gameBoard);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();

    void printCommentForUserAction();

    void showExceptionMessage(Exception e);

    void showSimpleMessage(String message);

    void showCommentForWrongAction();
}
