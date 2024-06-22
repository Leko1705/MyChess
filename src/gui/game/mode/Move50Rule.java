package gui.game.mode;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.pieces.Pawn;
import myChess.pieces.Piece;

public class Move50Rule implements Rule {

    private int moves = 0;

    @Override
    public Ending gameFinished(Board board) {
        return moves == 50 ? Ending.DRAW : Ending.NONE;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

        Piece piece = board.getPiece(move.from());
        if (piece instanceof Pawn) moves = 0;
        else moves++;

        return true;
    }

    @Override
    public void onMovePerformed(Move move, Board board) {
    }

    @Override
    public boolean onTileClicked(Position pos, Board board) {
        return true;
    }
}
