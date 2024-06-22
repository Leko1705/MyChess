package gui.game.mode;

import myChess.boards.Board;
import myChess.boards.Position;
import myChess.images.ChessPieceStyle;
import myChess.pieces.*;
import myChess.team.Team;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class MagicChess extends ClassicChess implements MoveListener {

    private static final int MAX_EVENT_DISTANCE = 15;

    private int probability = 0;

    private final Random random = new Random();

    private final List<RandomEvent> randomEvents =
            List.of(
                    new RandomLineDeletion(),
                    new RandomPositionSwapping(),
                    new RandomPieceReplacement(),
                    new RandomPieceRemoval()
                    );

    public MagicChess(List<Team> teams) {
        super(teams);
        addMoveListener(this);
    }

    @Override
    public void onMovePerformed(Move move, Board board) {
        if (randomActionTriggered()){
            performRandomAction(board);
            checkForEnding();
        }
    }

    private void performRandomAction(Board board) {
        RandomEvent event = randomEvents.get(random.nextInt(randomEvents.size()));
        boolean actionPerformed = event.onAction(board);
        if (actionPerformed) {
            JOptionPane.showMessageDialog(null, event.getMessage(), "Magic event", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            probability = MAX_EVENT_DISTANCE / 2;
        }
    }

    private boolean randomActionTriggered(){
        probability++;
        boolean triggered = random.nextInt(MAX_EVENT_DISTANCE) < probability;
        if (triggered) probability = 0;
        return triggered;
    }


    private interface RandomEvent {
        boolean onAction(Board board);

        String getMessage();
    }

    private class RandomLineDeletion implements RandomEvent {

        private String message;

        @Override
        public boolean onAction(Board board) {
            Dimension dimension = board.getDimension();
            boolean horizontal = random.nextBoolean();

            int line = random.nextInt(horizontal ? dimension.width : dimension.height);

            message = "all pieces on the " + (horizontal ? Integer.toString(line) : columNames[line]) + " are removed";

            for (Position position : board.getPositions().toArray(new Position[0])) {
                Piece piece = board.getPiece(position);
                if (piece == null) continue;
                if (piece instanceof King) continue;
                if ((horizontal && position.x == line) || (!horizontal && position.y == line))
                    board.putPiece(position, null);
            }

            return true;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    private class RandomPositionSwapping implements RandomEvent {

        String message;

        @Override
        public boolean onAction(Board board) {
            Class<? extends Piece> first =
                    getRandomPieceClass(false);

            Class<? extends Piece> second = getSecond(first);

            boolean actionPerformed = false;

            for (Position position : board.getPositions().toArray(new Position[0])){
                Piece piece = board.getPiece(position);
                if (piece == null) continue;
                if (piece.getClass() == first) {
                    Piece newPiece = newPiece(second, piece.getTeam());
                    board.putPiece(position, newPiece);
                    assert newPiece != null;
                    message = "all " + piece.getName() + " and " + newPiece.getName() + " switch their positions";
                    actionPerformed = true;
                }
                else if (piece.getClass() == second) {
                    Piece newPiece = newPiece(first, piece.getTeam());
                    board.putPiece(position, newPiece);
                    assert newPiece != null;
                    message = "all " + piece.getName() + " and " + newPiece.getName() + " switch their positions";
                    actionPerformed = true;
                }
            }

            return actionPerformed;
        }

        @Override
        public String getMessage() {
            return message;
        }

    }

    private class RandomPieceReplacement implements RandomEvent {

        String message;

        @Override
        public boolean onAction(Board board) {
            Class<? extends Piece> first =
                    getRandomPieceClass(true);

            Class<? extends Piece> second = getSecond(first);

            boolean actionPerformed = false;

            for (Position position : board.getPositions().toArray(new Position[0])){
                Piece piece = board.getPiece(position);
                if (piece == null) continue;
                if (piece.getClass() == first) {
                    Piece newPiece = newPiece(second, piece.getTeam());
                    board.putPiece(position, newPiece);
                    assert newPiece != null;
                    message = "all " + piece.getName() + " turn into " + newPiece.getName();
                    actionPerformed = true;
                }
            }

            return actionPerformed;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    private class RandomPieceRemoval implements RandomEvent {

        private String message;

        @Override
        public boolean onAction(Board board) {
            Class<? extends Piece> clazz = getRandomPieceClass(true);

            boolean actionPerformed = false;

            for (Position position : board.getPositions().toArray(new Position[0])){
                Piece piece = board.getPiece(position);
                if (piece == null) continue;
                if (piece.getClass() == clazz) {
                    board.putPiece(position, null);
                    message = "all " + piece.getName() + " left the board";
                    actionPerformed = true;
                }
            }

            return actionPerformed;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }



    private final List<Class<? extends Piece>> pieceClasses
            = List.of(Knight.class,
                    Bishop.class,
                    Rook.class,
                    Queen.class);

    private final String[] columNames = {"A", "B", "C", "D", "E", "F", "G", "H"};

    private Class<? extends Piece> getRandomPieceClass(boolean includePawn){
        Class<? extends Piece> clazz = pieceClasses.get(random.nextInt(pieceClasses.size()));
        if (!includePawn) return clazz;
        return random.nextInt(pieceClasses.size() + 1) == pieceClasses.size()
                ? Pawn.class
                : clazz;
    }

    private Class<? extends Piece> getSecond(Class<? extends Piece> first){
        Class<? extends Piece> second;
        do {
            second = getRandomPieceClass(false);
        } while (first == second);
        return second;
    }

    private Piece newPiece(Class<? extends Piece> clazz, Team team){
        try {
            return clazz
                    .getConstructor(Team.class)
                    .newInstance(team);
        }catch (Exception ignored){
            return null;
        }
    }

    @Override
    public String getHowToPlayText() {
        return """
                Magic chess works exactly as classic chess. But there is a catch:
                After a unknown time a random event occurs!
                """;
    }
}
