package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;
import java.util.Arrays;

public enum CellSign implements CellSignProvider {

    EMPTY(CellSnapshotStatus.EMPTY) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return EMPTY_SIGH;
        }
    },
    FLAG(CellSnapshotStatus.FLAG) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return FLAG_SIGN;
        }
    },
    LAND_MINE(CellSnapshotStatus.LAND_MINE) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return LAND_MINE_SIGN;
        }
    },
    NUMBER(CellSnapshotStatus.NUMBER) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return String.valueOf(cellSnapshot.nearByLandMineCount());
        }
    },
    UNCHECKED(CellSnapshotStatus.UNCHECKED) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return UNCHECKED_SIGH;
        }
    };

    private static final String EMPTY_SIGH = "■";
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGH = "□";

    private final CellSnapshotStatus cellSnapshotStatus;

    CellSign(CellSnapshotStatus cellSnapshotStatus) {
        this.cellSnapshotStatus = cellSnapshotStatus;
    }

    @Override
    public boolean support(CellSnapshot cellSnapshot) {
        return cellSnapshot.status() == cellSnapshotStatus;
    }

    public static String findCellSignFrom(CellSnapshot cellSnapshot) {
        final CellSignProvider cellSignProvider = findBy(cellSnapshot);
        return cellSignProvider.provide(cellSnapshot);
    }

    private static CellSignProvider findBy(CellSnapshot cellSnapshot) {
        return Arrays.stream(values())
            .filter(cellSignProvider -> cellSignProvider.support(cellSnapshot))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 셀 상태입니다. cellSnapshot: " + cellSnapshot));
    }


}
