package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gamelevel.VeryBeginner;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class GameApplication {

    static void main() {
        final GameRunnable gameRunnable = new Mineswepper(new VeryBeginner(), new ConsoleInputHandler(), new ConsoleOutputHandler());
        gameRunnable.run();
    }
}
