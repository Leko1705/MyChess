package gui.game.mode;

import myChess.boards.Board;

public interface MoveListener {

    void onMovePerformed(Move move, Board board);

}
