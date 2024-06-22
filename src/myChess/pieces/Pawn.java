package myChess.pieces;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.team.Side;
import myChess.team.Team;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends SimplePiece {

    public Pawn(Team team) {
        super("Pawn", team);
    }

    @Override
    public boolean isMove(Position currPos, Position movPos, Board board) {
        if (!board.inRange(movPos)) return false;
        if (currPos.equals(movPos)) return false;
        if (targetTileHasTeamMember(movPos, board)) return false;
        return getMoves(currPos, board).contains(movPos);
    }

    @Override
    public List<Position> getMoves(Position pos, Board board) {
        List<Position> moves = new ArrayList<>();

        Side side = getTeam().getSide();

        Position move = new Position(pos.x + side.xDirection, pos.y + side.yDirection);
        if (!board.inRange(move)) return moves;
        if (board.getPiece(move) == null)
            moves.add(move);

        if (isInitial()){
            move = new Position(pos.x + (side.xDirection * 2), pos.y + (side.yDirection * 2));
            if (!PieceUtils.hasLineCuttingPiece(pos, move, board, side.xDirection, side.yDirection)
                && board.getPiece(move) == null)
                moves.add(move);
        }

        return addCapturingMoves(moves, pos, board);
    }

    private List<Position> addCapturingMoves(List<Position> moves, Position pos, Board board) {
        Side side = getTeam().getSide();

        int xOffs = Math.abs(side.xDirection) - 1;
        int yOffs = Math.abs(side.yDirection) - 1;

        Position captureMove =
                new Position(pos.x + side.xDirection + xOffs, pos.y + side.yDirection + yOffs);

        if (board.inRange(captureMove) && board.getPiece(captureMove) != null && !targetTileHasTeamMember(captureMove, board))
            moves.add(captureMove);

        captureMove =
                new Position(pos.x + side.xDirection - xOffs, pos.y + side.yDirection - yOffs);

        if (board.inRange(captureMove) && board.getPiece(captureMove) != null && !targetTileHasTeamMember(captureMove, board))
            moves.add(captureMove);

        return moves;
    }

}
