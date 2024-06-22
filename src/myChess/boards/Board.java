package myChess.boards;

import myChess.pieces.Piece;
import myChess.pieces.PieceFactory;
import myChess.team.Team;
import myChess.utils.InitialPosition;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public interface Board {

    Piece getPiece(Position pos);

    default Piece putPiece(Position pos, Piece piece){
        return putPiece(pos, piece, true);
    }

    Piece putPiece(Position pos, Piece piece, boolean notifyListeners);

    Dimension getDimension();

    void addPositionChangeListener(PositionChangeListener listener);

    Collection<Position> getPositions();

    Position getPosition(Piece piece);

    boolean inRange(Position pos);


    BoardInitializer getInitializer();


}
