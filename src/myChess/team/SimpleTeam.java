package myChess.team;

import myChess.pieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleTeam implements Team {

    private final String name;
    private final Side side;
    private final Object key;
    private final List<Piece> piecesAlive = new ArrayList<>();
    private final List<Piece> deadPieces = new ArrayList<>();

    public SimpleTeam(String name, Side side, Object key) {
        this.name = name;
        this.side = side;
        this.key = key;
    }

    @Override
    public Side getSide() {
        return side;
    }

    @Override
    public Object getKey() {
        return key;
    }

    @Override
    public List<Piece> getPieces(boolean alive) {
        return alive ? piecesAlive : deadPieces;
    }

    @Override
    public String toString() {
        return name;
    }
}
