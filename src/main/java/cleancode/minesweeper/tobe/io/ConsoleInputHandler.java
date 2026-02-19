package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.BoardIndexConverter;
import cleancode.minesweeper.tobe.position.CellPosition;
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
    public String getInputFromUser() {
        return SCANNER.nextLine();
    }
}
