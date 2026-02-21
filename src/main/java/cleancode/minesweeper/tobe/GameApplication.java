package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.minesweeper.Minesweeper;
import cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.VeryBeginner;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleOutputHandler;

public class GameApplication {

    private GameApplication() {
    }

    static void main() {

        final GameConfig gameConfig = new GameConfig(
//            new StubGameLevel(120, 120, 9),
            new VeryBeginner(),
            new ConsoleInputHandler(),
            new ConsoleOutputHandler()
        );

        final GameRunnable gameRunnable = new Minesweeper(gameConfig);
        gameRunnable.run();
    }
}