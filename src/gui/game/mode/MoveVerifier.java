package gui.game.mode;

import myChess.boards.Board;

public interface MoveVerifier {

    boolean isValidMove(Move move, Board board);

}
