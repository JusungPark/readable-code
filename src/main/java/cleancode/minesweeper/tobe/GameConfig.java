package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public record GameConfig(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {

}
