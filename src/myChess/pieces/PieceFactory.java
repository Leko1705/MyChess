package myChess.pieces;

import myChess.team.Team;

public interface PieceFactory {

    Piece create(Class<? extends Piece> clazz, Team team);


    static PieceFactory getDefaultFactory(){
        return (clazz, team) -> {
                try {
                    return clazz.getConstructor(Team.class).newInstance(team);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
    }
}

