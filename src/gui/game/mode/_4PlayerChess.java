package gui.game.mode;

import myChess.boards.Board;
import myChess.team.Team;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _4PlayerChess extends ClassicChess {

    private final Set<Team> losers = new HashSet<>();

    public _4PlayerChess(List<Team> teams) {
        super(teams);
        addGameEndChecker(this);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        return !move.from().equals(move.to());
    }

    @Override
    public Ending gameFinished(Board board) {
        Team successor = getSuccessorTeam();

        boolean nextCanMove = hasLegalMoves(successor, board);
        if (!nextCanMove) return Ending.DRAW;
        if (losers.contains(successor)) {
            while (losers.contains(getSuccessorTeam())){
                nextPlayersTurn();
            }
            return Ending.NONE;
        }

        if (successor.getKings().isEmpty()){
            losers.add(successor);
            nextPlayersTurn();
            JOptionPane.showMessageDialog(null, "", successor + " is out", JOptionPane.INFORMATION_MESSAGE);
        }

        if (losers.size() + 1 == getTeams().size()){
            return Ending.WIN;
        }

        return Ending.NONE;
    }
}
