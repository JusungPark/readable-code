package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.List;

public record Cells(List<Cell> cells) {

    public boolean isAllCellChecked() {
        return cells.stream().allMatch(Cell::isChecked);
    }
}
