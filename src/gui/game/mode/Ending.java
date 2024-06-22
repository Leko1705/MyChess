package gui.game.mode;

import myChess.team.Team;

public enum Ending {

    NONE,
    LOSE,
    WIN,
    DRAW;

    public String getMessage(Team team){
        return switch (this){
            case NONE -> null;
            case LOSE -> team + " loses!";
            case WIN ->  team + " wins!";
            case DRAW -> "draw!";
        };
    }

}
