package myChess.utils;

import myChess.pieces.*;

import java.util.List;

public interface InitialPositions {

    InitialPosition DEFAULT = new InitialPosition()
            .add(0, List.of(Rook.class, Knight.class, Bishop.class, Queen.class, King.class, Bishop.class, Knight.class, Rook.class))
            .add(0, List.of(Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class));

    InitialPosition GRID_5x5 = new InitialPosition()
            .add(0, List.of(Rook.class, Knight.class, Queen.class, Bishop.class, King.class))
            .add(0, List.of(Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class));

    static InitialPosition getDefaultPosition(int baseOffs, int pawnOffs){
        if (baseOffs == 0 && pawnOffs == 0) return DEFAULT;
        return new InitialPosition()
                .add(baseOffs, List.of(Rook.class, Knight.class, Bishop.class, Queen.class, King.class, Bishop.class, Knight.class, Rook.class))
                .add(pawnOffs, List.of(Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class));
    }

    default InitialPosition get5x5Position(){
        return GRID_5x5;
    }

}
