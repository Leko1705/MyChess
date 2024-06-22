package gui.game.mode;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.pieces.King;
import myChess.pieces.Pawn;
import myChess.pieces.Piece;
import myChess.team.Team;

import java.util.List;

public class AtomChess extends ClassicChess implements MoveListener, MoveVerifier {

    boolean isTakingAction;

    public AtomChess(List<Team> teams) {
        super(teams);
        addMoveListener(this);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        isTakingAction = board.getPiece(move.to()) != null;
        return true;
    }

    @Override
    public void onMovePerformed(Move move, Board board) {
        if (!isTakingAction) return;
        Piece mover = board.getPiece(move.to());

        if (!(mover instanceof Pawn) && !(mover instanceof King)) {
            board.putPiece(move.to(), null);
        }

        boolean kingIsNeighbour = false;

        Position center = move.to();
        for (int i : List.of(-1, 1)){
            for (int j : List.of(-1, 1)){
                Position neighbourPos = new Position(center.x + i, center.y + j);
                if (!board.inRange(neighbourPos)) continue;
                Piece neigbourPiece = board.getPiece(neighbourPos);
                if (neigbourPiece instanceof Pawn) continue;
                if (neigbourPiece instanceof King) kingIsNeighbour = true;
                board.putPiece(neighbourPos, null);
            }
        }

        if (kingIsNeighbour) endGame(Ending.WIN);
    }

    @Override
    public String getHowToPlayText() {
        return """
                Atom chess works like classic chess.
                But in addition a piece captures all surrounding
                pieces one tile away if it captures another way.
                And even the move piece taken out the others is taken out
                itself.
                This applies to every piece except the pawn and king.
                """;
    }
}
