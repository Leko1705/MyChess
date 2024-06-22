package myChess.pieces;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.team.Team;

import java.util.ArrayList;
import java.util.List;

public class Rook extends SimplePiece {

    public Rook(Team team) {
        super("Rook", team);
    }

    @Override
    public boolean isMove(Position currPos, Position movPos, Board board) {
        if (!board.inRange(movPos)) return false;
        if (currPos.equals(movPos)) return false;
        if (currPos.x != movPos.x && currPos.y != movPos.y) return false;
        if (targetTileHasTeamMember(movPos, board)) return false;

        int xOffs = Integer.compare(movPos.x, currPos.x);
        int yOffs = Integer.compare(movPos.y, currPos.y);

        return !PieceUtils.hasLineCuttingPiece(currPos, movPos, board, xOffs, yOffs);
    }

    @Override
    @SuppressWarnings("all")
    public List<Position> getMoves(Position pos, Board board) {
        List<Position> moves = new ArrayList<>();
        moves.addAll(PieceUtils.listMovesOnLine(pos, board, -1, 0));
        moves.addAll(PieceUtils.listMovesOnLine(pos, board, 1, 0));
        moves.addAll(PieceUtils.listMovesOnLine(pos,board, 0, -1));
        moves.addAll(PieceUtils.listMovesOnLine(pos, board, 0, 1));
        return moves;
    }

}
