package myChess.pieces;

import myChess.boards.Board;
import myChess.boards.Position;

import java.util.ArrayList;
import java.util.List;

public final class PieceUtils {

    private PieceUtils(){
    }

    public static List<Position> listMovesOnLine(Position pos, Board board, int xOffs, int yOffs){
        List<Position> diagMoves = new ArrayList<>();
        for (int i = 0; true; i++){
            Position move = new Position(pos.x + (xOffs * i), pos.y + (yOffs * i));
            if (!board.inRange(move)) break;
            diagMoves.add(move);
        }
        return diagMoves;
    }

    public static boolean hasLineCuttingPiece(Position currPos, Position movPos, Board board, int xOffs, int yOffs){
        for (int i = 1; true; i++){
            Position move = new Position(currPos.x + (xOffs * i), currPos.y + (yOffs * i));
            if (move.equals(movPos)) return false;
            if (!board.inRange(move) || board.getPiece(move) != null) break;
        }
        return true;
    }

}
