package myChess.pieces;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.team.Team;

import java.util.ArrayList;
import java.util.List;

public class King extends SimplePiece {

    private final List<Integer> offsets = List.of(-1, 0, 1);

    public King(Team team) {
        super("King", team);
    }

    @Override
    public boolean isMove(Position currPos, Position movPos, Board board) {
        if (!board.inRange(movPos)) return false;
        if (currPos.equals(movPos)) return false;
        if (Math.abs(movPos.x - currPos.x) > 1
                || Math.abs(movPos.y - currPos.y) > 1) return false;
        return !targetTileHasTeamMember(movPos, board);
    }

    @Override
    public List<Position> getMoves(Position pos, Board board) {
        List<Position> moves = new ArrayList<>();

        for (Integer offsX : offsets){
            for (Integer offsY : offsets){
                if (offsX == 0 && offsY == 0) continue;
                moves.add(new Position(pos.x + offsX, pos.y + offsY));
            }
        }

        return moves;
    }
}
