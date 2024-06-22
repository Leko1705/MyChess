package gui.game.mode;

import myChess.boards.Board;

public interface GameEndChecker {

    Ending gameFinished(Board board);
}
