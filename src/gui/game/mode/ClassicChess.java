package gui.game.mode;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.images.ChessPieceStyle;
import myChess.pieces.*;
import myChess.team.Team;
import mylib.swingx.JRecycler;
import mylib.swingx.RecyclerView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClassicChess extends ChessBasics implements MoveVerifier, GameEndChecker {

    public ClassicChess(List<Team> teams) {
        super(teams);
        addMoveVerifier(this);
        addGameEndChecker(this);

        addRule(new Move50Rule());
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        return !impliesKingAttack(move, getCurrentTeam(), board) && !move.from().equals(move.to());
    }

    public boolean impliesKingAttack(Move move, Team team, Board board){

        Position currPos = move.from();
        Position nextPos = move.to();
        Piece piece = board.putPiece(currPos, null, false);

        Piece nextPosPiece = board.putPiece(nextPos, piece, false);

        boolean kingAttacked = kingIsUnderAttack(team, board);

        board.putPiece(currPos, piece, false);
        board.putPiece(nextPos, nextPosPiece, false);

        return kingAttacked;
    }

    public boolean kingIsUnderAttack(Team team, Board board){
        List<King> kings = team.getKings();

        for (Team otherTeam : getTeams()){
            if (otherTeam == team) continue;

            for (Piece piece : otherTeam.getPieces(true)){
                Position position = board.getPosition(piece);
                if (position == null) continue;
                for (King king : kings){
                    Position kingPos = board.getPosition(king);
                    if (kingPos == null) continue;
                    if (piece.isMove(position, kingPos, board))
                        return true;
                }
            }
        }

        return false;
    }

    @Override
    public Ending gameFinished(Board board) {
        boolean nextCanMove = hasLegalMoves(getSuccessorTeam(), board);

        if (kingIsUnderAttack(getSuccessorTeam(), board)){
            return nextCanMove ? Ending.NONE : Ending.WIN;
        }

        return nextCanMove ? Ending.NONE : Ending.DRAW;
    }

    public boolean hasLegalMoves(Team team, Board board){

        for (Piece piece : team.getPieces(true)){
            Position position = board.getPosition(piece);
            if (position == null) continue;

            List<Position> moves = piece.getMoves(position, board);
            for (Position move : moves){
                if (!piece.isMove(position, move, board)) continue;
                if (!impliesKingAttack(new Move(position, move), team, board))
                    return true;
            }
        }

        return false;
    }

    @Override
    public String getHowToPlayText() {
        return """
                The classic chess mode contains all chess elements for a standard
                chess game.
                
                A king is in check if it can be captured by a piece on the next move.
                The game is over when the king is checkmated, i.e. it is in check
                and cannot make any more moves without being captured.
                """;
    }
}

