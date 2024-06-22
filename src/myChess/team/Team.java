package myChess.team;

import myChess.pieces.King;
import myChess.pieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface Team {

    Side getSide();

    Object getKey();

    default List<Piece> getAllPieces(){
        List<Piece> pieces = getPieces(true);
        pieces.addAll(getPieces(false));
        return pieces;
    }

    default List<King> getKings() {
        List<King> kings = new ArrayList<>();
        for (Piece piece : getPieces(true))
            if (piece instanceof King king)
                kings.add(king);
        return kings;
    }

    List<Piece> getPieces(boolean alive);
    
}
