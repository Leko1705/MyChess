package myChess.pieces;

import myChess.boards.Board;
import myChess.team.Team;
import myChess.boards.Position;

import java.util.List;

public abstract class SimplePiece implements Piece {

    private final String name;
    private final Team team;

    private boolean initial = true;

    public SimplePiece(String name, Team team){
        this.name = name;
        this.team = team;
    }

    protected boolean targetTileHasTeamMember(Position movPos, Board board){
        Piece targetPosPiece = board.getPiece(movPos);
        return targetPosPiece != null && targetPosPiece.getTeam() != null && targetPosPiece.getTeam().equals(getTeam());
    }

    @Override
    public boolean isInitial() {
        return initial;
    }

    @Override
    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Team getTeam() {
        return team;
    }

}
