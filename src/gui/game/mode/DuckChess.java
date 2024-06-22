package gui.game.mode;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.pieces.Duck;
import myChess.pieces.Piece;
import myChess.team.Team;

import java.util.List;

public class DuckChess extends ClassicChess implements TileClickListener, MoveVerifier {

    private final Piece duck = new Duck();

    private static final int TILE_SELECTION = 0;
    private static final int DUCK_PLACEMENT = 2;

    private int duckPlaceAction = 0;

    private Position duckPosition = null;


    public DuckChess(List<Team> teams) {
        super(teams);
        addTileClickListener(this);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        boolean res = super.isValidMove(move, board)
                && !move.to().equals(duckPosition);

        if (res) {
            if (duckPosition != null) {
                board.putPiece(duckPosition, null);
            }
            duckPlaceAction = DUCK_PLACEMENT;
            duckPosition = null;
        }
        else {
            duckPlaceAction = TILE_SELECTION;
        }

        return res;
    }

    @Override
    public boolean onTileClicked(Position pos, Board board) {

        if (pos.equals(duckPosition))
            return false;

        if (duckPlaceAction == DUCK_PLACEMENT){
            Piece curr = board.getPiece(pos);
            if (curr != null) return false;
            duckPosition = pos;
            board.putPiece(pos, duck);
            duckPlaceAction = TILE_SELECTION;
            return false;
        }

        return true;
    }

    @Override
    public String getHowToPlayText() {
        return """
                Duck chess works like classic chess.
                However if a player has made his move he places
                a duck at some other tile without a piece. This tile is
                now blocked for the next player.
                If the next player made his move he takes duck and places it
                for his own goals.
                """;
    }
}
