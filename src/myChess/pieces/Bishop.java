package myChess.pieces;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.team.Team;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends SimplePiece {

    public Bishop(Team team) {
        super("Bishop", team);
    }

    @SuppressWarnings("all")
    @Override
    public boolean isMove(Position currPos, Position movPos, Board board) {
        if (!board.inRange(movPos)) return false;
        if (currPos.equals(movPos)) return false;

        if (Math.abs(movPos.x - currPos.x) != Math.abs(movPos.y - currPos.y))
            return false;

        if (targetTileHasTeamMember(movPos, board))
            return false;

        int xOffs = (movPos.x - currPos.x) > 0 ? 1 : -1;
        int yOffs = (movPos.y - currPos.y) > 0 ? 1 : -1;

        return !PieceUtils.hasLineCuttingPiece(currPos, movPos, board, xOffs, yOffs);
    }

    @Override
    @SuppressWarnings("all")
    public List<Position> getMoves(Position pos, Board board) {
        List<Position> moves = new ArrayList<>();
        moves.addAll(PieceUtils.listMovesOnLine(pos, board, -1, -1));
        moves.addAll(PieceUtils.listMovesOnLine(pos, board, 1, -1));
        moves.addAll(PieceUtils.listMovesOnLine(pos,board, -1, 1));
        moves.addAll(PieceUtils.listMovesOnLine(pos, board, 1, 1));
        return moves;
    }

}
