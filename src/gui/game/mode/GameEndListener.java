package gui.game.mode;

import myChess.team.Team;

public interface GameEndListener {

    void onGameEnd(Team winner, Ending ending);
}
