package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.GameBoard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public record CellPositions(List<CellPosition> cellPositions) implements Iterable<CellPosition> {

    @Override
    public Iterator<CellPosition> iterator() {
        return this.cellPositions.iterator();
    }

    public Stream<CellPosition> stream() {
        return this.cellPositions.stream();
    }

    public static CellPositions from(GameBoard board) {
        final List<CellPosition> cells = new ArrayList<>();
        for (int row = 0; row < board.rowSize(); row++) {
            for (int col = 0; col < board.colSize(); col++) {
                cells.add(new CellPosition(row, col));
            }
        }
        return new CellPositions(cells);
    }

    public CellPositions extractRandomPositions(Integer landMineCount) {
        final List<CellPosition> temp = new ArrayList<>(cellPositions);
        Collections.shuffle(temp);
        return new CellPositions(temp.subList(0, landMineCount));
    }
}
