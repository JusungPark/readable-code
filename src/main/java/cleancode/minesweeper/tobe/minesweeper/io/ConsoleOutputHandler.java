package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.io.sign.CellSign;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler {

    @Override
    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showBoard(GameBoard gameBoard) {
        final String alphabets = generateColumnAlphabets(gameBoard);
        System.out.println("    " + alphabets);
        for (int row = 0; row < gameBoard.rowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < gameBoard.colSize(); col++) {
                CellSnapshot cellSnapshot = gameBoard.getSnapshot(new CellPosition(row, col));
                String cellSign = CellSign.findCellSignFrom(cellSnapshot);
                System.out.print(cellSign + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void showGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    @Override
    public void showGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void showCommentForSelectingCell() {
        System.out.println();
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    @Override
    public void printCommentForUserAction() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showExceptionMessage(Exception e) {
        System.err.println(e.getMessage());
    }

    @Override
    public void showSimpleMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showCommentForWrongAction() {
        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private static String generateColumnAlphabets(GameBoard gameBoard) {
        return IntStream.range(0, gameBoard.colSize()).mapToObj(index -> (char) ('a' + index)).map(String::valueOf).collect(Collectors.joining(" "));
    }
}
