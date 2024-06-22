package myChess.utils;

import myChess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class InitialPosition {

    public record Rank(int offset, List<Class<? extends Piece>> get) {
    }


    private final List<Rank> ranks = new ArrayList<>();

    public InitialPosition add(int offs, List<Class<? extends Piece>> pieces){
        return add(new Rank(offs, pieces));
    }

    public InitialPosition add(Rank rank){
        ranks.add(rank);
        return this;
    }

    public InitialPosition add(InitialPosition position){
        ranks.addAll(position.ranks);
        return this;
    }

    public List<Rank> getRanks() {
        return ranks;
    }
}
