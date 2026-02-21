package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.user.UserAction;
import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    private static final Scanner SCANNER = new Scanner(System.in);
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    @Override
    public CellPosition getCellPositionFromUser() {
        final String input = SCANNER.nextLine();
        return new CellPosition(
            boardIndexConverter.getSelectedRowIndex(input),
            boardIndexConverter.getSelectedColIndex(input)
        );
    }

    @Override
    public UserAction getActionFromUser() {
        return switch (SCANNER.nextLine()) {
            case "1" -> UserAction.OPEN;
            case "2" -> UserAction.FLAG;
            default -> UserAction.UNKNOWN;
        };
    }
}
