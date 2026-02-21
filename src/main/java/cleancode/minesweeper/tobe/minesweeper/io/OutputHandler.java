package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;

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
