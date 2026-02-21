package cleancode.minesweeper.tobe.minesweeper.io;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COLUMN = 'a';

    public int getSelectedRowIndex(String cellInput) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow);
    }

    public int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    private int convertRowFrom(String cellInputRow) {
        return Integer.parseInt(cellInputRow) - 1;
    }

    private int convertColFrom(char cellInputCol) {
        return Character.toLowerCase(cellInputCol) - BASE_CHAR_FOR_COLUMN;
    }
}
