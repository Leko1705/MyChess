package myChess.boards;

import myChess.pieces.PieceFactory;
import myChess.team.Team;
import myChess.utils.InitialPosition;

import java.util.List;
import java.util.Map;

public interface BoardInitializer {

    default void initialize(List<Team> teams, List<InitialPosition> positions){
        initialize(teams, positions, PieceFactory.getDefaultFactory());
    }

    void initialize(List<Team> teams, List<InitialPosition> positions, PieceFactory factory);

}
