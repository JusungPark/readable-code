package cleancode.minesweeper.tobe.io.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;

public interface CellSignProvider {

    boolean support(CellSnapshot cellSnapshot);

    String provide(CellSnapshot cellSnapshot);
}
