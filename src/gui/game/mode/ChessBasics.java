package gui.game.mode;

import gui.game.view.BoardView;
import gui.game.view.GridBoardView;
import gui.game.view.Tile;
import myChess.boards.Board;
import myChess.boards.Position;
import myChess.boards.PositionChangeListener;
import myChess.images.ChessPieceStyle;
import myChess.images.DuckImage;
import myChess.images.PieceImage;
import myChess.pieces.*;
import myChess.team.Side;
import myChess.team.Team;
import mylib.swingx.JRecycler;
import mylib.swingx.RecyclerView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ChessBasics implements GameController, PositionChangeListener {

    private BoardView boardView;
    private Board board;
    private final List<Team> teams;
    private int turnPointer = 0;

    private boolean canCastle = true;

    private boolean enabled = true;

    private Tile prevClickedTile = null;

    private ChessPieceStyle pieceImageProvider;

    private final Set<MoveVerifier> verifiers = new HashSet<>();
    private final Set<MoveListener> moveListeners = new HashSet<>();
    private final Set<GameEndListener> gameEndListeners = new HashSet<>();

    private final Set<GameEndChecker> gameEndCheckers = new HashSet<>();

    private final Set<TileClickListener> tileClickListeners = new HashSet<>();

    public ChessBasics(List<Team> teams){
        this.teams = teams;

        PawnConversion conversion = new PawnConversion();
        addMoveVerifier(conversion);
        addMoveListener(conversion);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setCanCastle(boolean canCastle) {
        this.canCastle = canCastle;
    }

    public boolean canCastle() {
        return canCastle;
    }

    public void addMoveVerifier(MoveVerifier verifier){
        verifiers.add(verifier);
    }

    public void removeMoveVerifier(MoveVerifier verifier){
        verifiers.remove(verifier);
    }

    public void addMoveListener(MoveListener l){
        moveListeners.add(l);
    }

    public void removeMoveListener(MoveListener l){
        moveListeners.remove(l);
    }

    public void addGameEndListener(GameEndListener l) {
        gameEndListeners.add(l);
    }

    public void removeGameEndListener(GameEndListener l){
        gameEndListeners.remove(l);
    }

    public void addGameEndChecker(GameEndChecker checker){
        gameEndCheckers.add(checker);
    }

    public void removeGameEndChecker(GameEndChecker checker){
        gameEndCheckers.remove(checker);
    }

    public void addTileClickListener(TileClickListener l) {
        tileClickListeners.add(l);
    }

    public void removeTileClickListener(TileClickListener l) {
        tileClickListeners.remove(l);
    }

    public void addRule(Rule rule) {
        addGameEndChecker(rule);
        addMoveVerifier(rule);
        addMoveListener(rule);
        addTileClickListener(rule);
    }

    public void removeRule(Rule rule) {
        removeGameEndChecker(rule);
        removeMoveVerifier(rule);
        removeMoveListener(rule);
        removeTileClickListener(rule);
    }

    private boolean isValidMoveByVerifiers(Position from, Position to){
        Move move = new Move(from, to);
        for (MoveVerifier verifier : verifiers)
            if (!verifier.isValidMove(move, board))
                return false;
        return true;
    }

    private void notifyMovePerformed(Position from, Position to){
        Move move = new Move(from, to);
        for (MoveListener listener : moveListeners)
            listener.onMovePerformed(move, board);
    }

    @Override
    public void setBoardView(BoardView view) {
        this.boardView = view;
    }

    @Override
    public void setBoardModel(Board model) {
        this.board = model;
        board.addPositionChangeListener(this);
    }

    @Override
    public void setPieceStyle(ChessPieceStyle style) {
        this.pieceImageProvider = style;
    }

    @Override
    public void onTileClicked(Tile tile) {
        if (!isEnabled()) return;

        for (TileClickListener listener : tileClickListeners)
            if (!listener.onTileClicked(tile.getPosition(), board))
                return;

        if (prevClickedTile == null){
            if (isSelectablePiece(tile)) {
                tile.setSelected(true);
                prevClickedTile = tile;
            }
        }
        else {
            handleMove(tile);
        }
    }

    private boolean isSelectablePiece(Tile tile){
        Piece piece = pieceOf(tile);
        if (piece == null) return false;
        return piece.getTeam() == getCurrentTeam();
    }

    private void handleMove(Tile tile){
        Piece piece = pieceOf(prevClickedTile);
        Position currPos = prevClickedTile.getPosition();
        Position nextPos = tile.getPosition();

        if (piece.isMove(currPos, nextPos, board)
                && isValidMoveByVerifiers(currPos, nextPos)){
            performMove(tile);
            nextTurn();
        }
        else {
            Move castleRookMove = validCastleMove(piece, currPos, nextPos);
            if (castleRookMove != null){
                performMove(tile);
                performMove(castleRookMove);
                nextTurn();
            }
            else {
                prevClickedTile.setSelected(false);
                prevClickedTile = null;
            }
        }
    }

    private Move validCastleMove(Piece piece, Position currPos, Position nextPos){
        if (!canCastle) return null;
        if (!(piece instanceof King king)) return null;
        if (!king.isInitial()) return null;

        Side side = piece.getTeam().getSide();

        if (side.xDirection == 0 && currPos.y != nextPos.y) return null;
        if (side.yDirection == 0 && currPos.x != nextPos.x) return null;

        Position from = null;
        Position to = null;

        if (side.xDirection == 0){
            if (Math.abs(currPos.x - nextPos.x) != 2) return null;
            int offs = (currPos.x - nextPos.x) < 0 ? 1 : -1;

            to = new Position(currPos.x + offs, currPos.y);

            for (Piece rookCandidate : king.getTeam().getPieces(true)){
                if (rookCandidate instanceof Rook rook) {
                    if (!rook.isInitial()) continue;
                    Position rookPos = board.getPosition(rook);
                    if (rookPos.y != currPos.y) continue;
                    if (!PieceUtils.hasLineCuttingPiece(currPos, rookPos, board, offs, 0)){
                        from = rookPos;
                    }
                }
            }

        }

        if (from == null) return null;
        return new Move(from, to);
    }

    private void performMove(Tile tile){
        Position currPos = prevClickedTile.getPosition();
        Position nextPos = tile.getPosition();
        performMove(new Move(currPos, nextPos));
    }

    private void performMove(Move move){
        Position currPos = move.from();
        Position nextPos = move.to();
        Piece piece = board.putPiece(currPos, null);
        piece.setInitial(false);
        boardView.getTile(currPos.x, currPos.y).setIcon(null);
        Piece captured = board.putPiece(nextPos, piece);
        if (captured != null) {
            Team team = captured.getTeam();
            team.getPieces(true).remove(captured);
            team.getPieces(false).add(captured);
        }

        notifyMovePerformed(currPos, nextPos);
    }

    private void nextTurn(){
        if (prevClickedTile != null)
            prevClickedTile.setSelected(false);
        prevClickedTile = null;

        checkForEnding();

        nextPlayersTurn();
    }

    public void nextPlayersTurn(){
        turnPointer = (turnPointer + 1) % teams.size();
    }

    public void checkForEnding(){
        for (GameEndChecker checker : gameEndCheckers){
            Ending ending = checker.gameFinished(board);
            if (ending != null && ending != Ending.NONE){
                endGame(ending);
                break;
            }
        }
    }

    public void endGame(Ending ending){
        for (GameEndListener listener : gameEndListeners)
            listener.onGameEnd(getCurrentTeam(), ending);
        setEnabled(false);
    }

    public void onPositionChange(Board board, Position position){
        Tile tile = boardView.getTile(position.x, position.y);
        Piece piece = board.getPiece(position);
        PieceImage pieceImage = getPieceImage(piece);

        if (piece != null && pieceImage != null){
            Team team = piece.getTeam();
            Image image = pieceImage.loadImage(team.getKey());

            boardView.getTile(position.x, position.y);
            if (tile.getWidth() != 0 && tile.getHeight() != 0)
                image = image.getScaledInstance(tile.getWidth(), tile.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            tile.setIcon(icon);
        }

        else {
            tile.setIcon(null);
        }

    }

    private PieceImage getPieceImage(Piece piece){
        if (piece == null) return null;

        return switch (piece.getName()){
            case "Pawn" -> pieceImageProvider.getPawnImage();
            case "Knight" -> pieceImageProvider.getKnightImage();
            case "Bishop" -> pieceImageProvider.getBishopImage();
            case "Rook" -> pieceImageProvider.getRookImage();
            case "Queen" -> pieceImageProvider.getQueenImage();
            case "King" -> pieceImageProvider.getKingImage();
            case "Duck" -> new DuckImage();
            default -> throw new IllegalStateException(piece.getClass().toString());
        };
    }

    private Piece pieceOf(Tile tile){
        Position position = tile.getPosition();
        return board.getPiece(position);
    }

    public List<Team> getTeams(){
        return teams;
    }

    public Team getCurrentTeam(){
        return teams.get(turnPointer);
    }

    public Team getSuccessorTeam() {
        return teams.get((turnPointer + 1) % teams.size());
    }


    public void onPawnConversion(List<Piece> pieces, Team team){
        pieces.add(new Knight(team));
        pieces.add(new Bishop(team));
        pieces.add(new Rook(team));
        pieces.add(new Queen(team));
    }


    private class PawnConversion implements MoveVerifier, MoveListener {

        private Piece selected = null;

        @Override
        public boolean isValidMove(Move move, Board board) {
            selected = null;
            Piece piece = board.getPiece(move.from());

            if (piece instanceof Pawn && isOnEndFile(move.to(), piece, board)){
                JOptionPane.showMessageDialog(null, new PiecePanel(piece.getTeam()), "choose a piece", JOptionPane.PLAIN_MESSAGE);
                return selected != null;
            }
            else {
                return true;
            }
        }

        private boolean isOnEndFile(Position to, Piece piece, Board board){
            Side side = piece.getTeam().getSide();
            Position nextPos = new Position(to.x + side.xDirection, to.y + side.yDirection);
            return !board.inRange(nextPos);
        }

        @Override
        public void onMovePerformed(Move move, Board board) {
            if (selected != null){
                board.putPiece(move.to(), selected);
            }
        }

        private class PiecePanel extends JRecycler {

            public PiecePanel(Team team){
                List<Piece> pieces = new ArrayList<>();
                onPawnConversion(pieces, team);

                for (Piece piece : pieces){
                    addComponent(new PieceCell(piece));
                }
            }

        }

        private class PieceCell extends RecyclerView {

            private final Piece piece;
            private final JLabel label;

            public PieceCell(Piece piece){
                this.piece = piece;
                this.label = new JLabel(piece.getName(), JLabel.CENTER);
                this.label.setOpaque(true);
                setLayout(new BorderLayout());
                add(label);
            }

            @Override
            protected void onSelect(JList<? extends RecyclerView> list, int index, boolean isSelected, boolean cellHasFocus) {
                if (isSelected){
                    selected = piece;
                    label.setBackground(Color.CYAN);
                }
                else {
                    label.setBackground(Color.WHITE);
                }
            }
        }

    }
}
