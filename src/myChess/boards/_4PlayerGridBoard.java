package myChess.boards;

import myChess.pieces.Piece;
import myChess.pieces.PieceFactory;
import myChess.team.Team;
import myChess.utils.InitialPosition;

import java.awt.*;
import java.util.*;
import java.util.List;

public class _4PlayerGridBoard implements Board {


    private final Map<Position, Piece> board = new HashMap<>();

    private final int baseLineSize;
    private final int offset;

    private final Set<PositionChangeListener> changeListeners = new HashSet<>();

    public _4PlayerGridBoard(int baseLineSize, int offs) {
        this.baseLineSize = baseLineSize;
        this.offset = offs;
    }

    public int getBaseLineSize() {
        return baseLineSize;
    }

    public int getOffset() {
        return offset;
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
        return new Dimension(baseLineSize + 4 + (offset * 2), baseLineSize + 4 + (offset * 2));
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
        return
                pos.x >= 0
                && pos.x < getDimension().width
                && pos.y >= 0
                && pos.y < getDimension().height
                && !(isInvalidTileCandidate(pos.x) && isInvalidTileCandidate(pos.y));
    }


    private boolean isInvalidTileCandidate(int val){
        return val < 2 + offset || val > 1 + baseLineSize + offset;
    }


    @Override
    public BoardInitializer getInitializer() {
        return new Initializer();
    }

    private class Initializer implements BoardInitializer {

        @Override
        public void initialize(List<Team> teams, List<InitialPosition> positions, PieceFactory factory) {
            initHorizontal(teams.get(0), positions.get(0), 0, 1, factory);
            initHorizontal(teams.get(1), positions.get(1), getDimension().height - 1, -1, factory);
            initVertical(teams.get(2), positions.get(2), 0, 1, factory);
            if (teams.size() == 4)
                initVertical(teams.get(3), positions.get(3), getDimension().width - 1, -1, factory);
        }

        private void initHorizontal(Team team, InitialPosition position, int baseLine, int offs, PieceFactory factory){
            for (InitialPosition.Rank rank : position.getRanks()){
                for (int pieceIndex = 0; pieceIndex < rank.get().size(); pieceIndex++){
                    putPiece(new Position(rank.offset() + pieceIndex, baseLine), factory.create(rank.get().get(pieceIndex), team));
                }
                baseLine += offs;
            }
        }

        private void initVertical(Team team, InitialPosition position, int baseLine, int offs, PieceFactory factory){
            for (InitialPosition.Rank rank : position.getRanks()){
                for (int pieceIndex = 0; pieceIndex < rank.get().size(); pieceIndex++){
                    Piece piece = factory.create(rank.get().get(pieceIndex), team);
                    team.getPieces(true).add(piece);
                    putPiece(new Position(baseLine, rank.offset() + pieceIndex), piece);
                }
                baseLine += offs;
            }
        }
    }
}
