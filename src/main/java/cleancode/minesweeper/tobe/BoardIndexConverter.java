package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COLUMN = 'a';

    public int getSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow, rowSize);
    }

    public int getSelectedColIndex(String cellInput, int colSize) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol, colSize);
    }

    private int convertRowFrom(String cellInputRow, int rowSize) {
        final int parsedInputRow = Integer.parseInt(cellInputRow) - 1;
        if (parsedInputRow >= rowSize || parsedInputRow < 0) {
            throw new GameException(new IllegalArgumentException("잘못된 행 번호입니다. 입력값: " + parsedInputRow));
        }
        return parsedInputRow;
    }

    private int convertColFrom(char cellInputCol, int colSize) {
        final int parsedInputCol = Character.toLowerCase(cellInputCol) - BASE_CHAR_FOR_COLUMN;
        if (parsedInputCol >= colSize || parsedInputCol < 0) {
            throw new GameException(new IllegalArgumentException("잘못된 열 번호입니다. 입력값: " + parsedInputCol));
        }
        return parsedInputCol;
    }
}
