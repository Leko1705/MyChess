package myChess.pieces;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.team.Team;

import java.util.ArrayList;
import java.util.List;

public class Queen extends SimplePiece {

    public Queen(Team team) {
        super("Queen", team);
    }

    @Override
    public boolean isMove(Position currPos, Position movPos, Board board) {
        if (!board.inRange(movPos)) return false;
        if (currPos.equals(movPos)) return false;
        if (targetTileHasTeamMember(movPos, board)) return false;

        return currPos.x == movPos.x || currPos.y == movPos.y
                ? checkHorizontalMove(currPos, movPos, board)
                : checkDiagonalMove(currPos, movPos, board);
    }

    private boolean checkHorizontalMove(Position currPos, Position movPos, Board board){
        int xOffs = Integer.compare(movPos.x, currPos.x);
        int yOffs = Integer.compare(movPos.y, currPos.y);
        return !PieceUtils.hasLineCuttingPiece(currPos, movPos, board, xOffs, yOffs);
    }

    @SuppressWarnings("all")
    private boolean checkDiagonalMove(Position currPos, Position movPos, Board board){
        if (Math.abs(movPos.x - currPos.x) != Math.abs(movPos.y - currPos.y)) return false;
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
        moves.addAll(PieceUtils.listMovesOnLine(pos, board, -1, 0));
        moves.addAll(PieceUtils.listMovesOnLine(pos, board, 1, 0));
        moves.addAll(PieceUtils.listMovesOnLine(pos,board, 0, -1));
        moves.addAll(PieceUtils.listMovesOnLine(pos, board, 0, 1));
        return moves;
    }
}
