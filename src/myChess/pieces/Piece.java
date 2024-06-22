package myChess.pieces;

import myChess.boards.Board;
import myChess.team.Team;
import myChess.boards.Position;

import java.util.List;

public interface Piece {

    String getName();

    Team getTeam();

    boolean isInitial();

    void setInitial(boolean i);

    boolean isMove(Position currPos, Position movPos, Board board);

    List<Position> getMoves(Position pos, Board board);

}
