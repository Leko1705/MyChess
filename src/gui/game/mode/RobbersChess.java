package gui.game.mode;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.pieces.Piece;
import myChess.team.Team;

import java.util.List;

public class RobbersChess extends ChessBasics implements MoveVerifier, GameEndChecker {
    public RobbersChess(List<Team> teams) {
        super(teams);
        addMoveVerifier(this);
        addGameEndChecker(this);

        addRule(new Move50Rule());
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

        Piece targetPosPiece = board.getPiece(move.to());
        if (targetPosPiece != null && targetPosPiece.getTeam() != getCurrentTeam()) {
            return true;
        }

        Team currTeam = getCurrentTeam();
        for (Piece piece : currTeam.getPieces(true)){
            for (Team otherTeam : getTeams()){
                if (currTeam == otherTeam) continue;
                for (Piece otherPiece : otherTeam.getPieces(true)){
                    Position from = board.getPosition(piece);
                    Position to = board.getPosition(otherPiece);
                    if (piece.isMove(from, to, board))
                        return false;
                }
            }
        }

        return true;
    }

    @Override
    public Ending gameFinished(Board board) {
        Team next = getSuccessorTeam();
        if (next.getPieces(true).isEmpty()) return Ending.LOSE;
        return Ending.NONE;
    }

    @Override
    public String getHowToPlayText() {
        return """
                Robber's chess is the exact opposite of classical chess.
                The aim is to have no pieces left on the board at the end of the game.
                 The player who achieves this first wins.
                In order for pieces to be captured, a player MUST
                 capture a piece if he can. Even the king can be captured.
                """;
    }
}
