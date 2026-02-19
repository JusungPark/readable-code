package cleancode.minesweeper.tobe.cell;

import java.util.List;

public record Cells(List<Cell> cells) {

    public boolean isAllCellChecked() {
        return cells.stream().allMatch(Cell::isChecked);
    }
}
