package myChess.boards;

import myChess.pieces.Piece;
import myChess.pieces.PieceFactory;
import myChess.team.Team;
import myChess.utils.InitialPosition;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GridBoard implements Board {

    private final Map<Position, Piece> board = new HashMap<>();

    private final int w, h;

    private final Set<PositionChangeListener> changeListeners = new HashSet<>();


    public GridBoard(int w, int h){
        this.w = w;
        this.h = h;
    }

    @Override
    public Piece getPiece(Position pos) {
        return board.get(pos);
    }

    @Override
    public Piece putPiece(Position pos, Piece piece, boolean notifyListeners) {
        Objects.requireNonNull(pos, "Position must not be null");

        Piece prev =  (piece == null)
                ? board.remove(pos)
                : board.put(pos, piece);

        if (notifyListeners) {
            for (PositionChangeListener listener : changeListeners) {
                listener.onPositionChange(this, pos);
            }
        }

        return prev;
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(w, h);
    }

    @Override
    public void addPositionChangeListener(PositionChangeListener listener) {
        changeListeners.add(listener);
    }

    @Override
    public Collection<Position> getPositions() {
        return board.keySet();
    }

    @Override
    public Position getPosition(Piece piece) {
        for (Map.Entry<Position, Piece> entry : board.entrySet()){
            if (piece.equals(entry.getValue()))
                return entry.getKey();
        }
        return null;
    }

    @Override
    public boolean inRange(Position pos) {
        return pos.x >= 0 && pos.x < w
                && pos.y >= 0 && pos.y < h;
    }



    @Override
    public BoardInitializer getInitializer() {
        return new Initializer();
    }

    private class Initializer implements BoardInitializer {

        @Override
        public void initialize(List<Team> teams, List<InitialPosition> positions, PieceFactory factory) {
            initTeam(teams.get(0), positions.get(0), 0, 1, factory);
            initTeam(teams.get(1), positions.get(1), h - 1, -1, factory);
        }

        private void initTeam(Team team, InitialPosition position, int baseLine, int offs, PieceFactory factory){
            for (InitialPosition.Rank rank : position.getRanks()){
                for (int pieceIndex = 0; pieceIndex < rank.get().size(); pieceIndex++){
                    Piece piece = factory.create(rank.get().get(pieceIndex), team);
                    team.getPieces(true).add(piece);
                    putPiece(new Position(rank.offset() + pieceIndex, baseLine), piece);
                }
                baseLine += offs;
            }
        }
    }

}
