package myChess.pieces;

import myChess.boards.Board;
import myChess.team.Team;
import myChess.boards.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends SimplePiece {

    private final List<Integer> offs1 = List.of(-1, 1);
    private final List<Integer> offs2 = List.of(-2, 2);

    public Knight(Team team) {
        super("Knight", team);
    }

    @Override
    public boolean isMove(Position currPos, Position movPos, Board board) {
        if (!board.inRange(movPos)) return false;
        if (currPos.equals(movPos)) return false;
        return !targetTileHasTeamMember(movPos, board)
                && getMoves(currPos, board).contains(movPos);
    }

    @Override
    public List<Position> getMoves(Position pos, Board board) {
        List<Position> moves = new ArrayList<>();
        for (Integer xOffs : offs1) {
            for (Integer yOffs : offs2) {
                moves.add(new Position(pos.x + xOffs, pos.y + yOffs));
                moves.add(new Position(pos.x + yOffs, pos.y + xOffs));
            }
        }
        return moves;
    }

}
