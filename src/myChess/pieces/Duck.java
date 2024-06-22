package myChess.pieces;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.team.Team;

import java.util.List;

public class Duck implements Piece {

    @Override
    public String getName() {
        return "Duck";
    }

    @Override
    public Team getTeam() {
        return null;
    }

    @Override
    public boolean isInitial() {
        return true;
    }

    @Override
    public void setInitial(boolean i) {
    }

    @Override
    public boolean isMove(Position currPos, Position movPos, Board board) {
        return false;
    }

    @Override
    public List<Position> getMoves(Position pos, Board board) {
        return List.of();
    }
}
