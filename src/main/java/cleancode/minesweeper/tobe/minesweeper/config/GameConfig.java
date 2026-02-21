package cleancode.minesweeper.tobe.minesweeper.config;

import cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.minesweeper.io.InputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.OutputHandler;

public record GameConfig(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {

}
