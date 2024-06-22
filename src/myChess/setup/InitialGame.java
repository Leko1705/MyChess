package myChess.setup;

import gui.game.mode.GameController;
import myChess.images.ChessPieceStyle;

public class InitialGame {

    private InitialBoard initialBoard;

    private GameController controller;

    private ChessPieceStyle style;

    public InitialGame(InitialBoard initialBoard, GameController controller, ChessPieceStyle style) {
        this.initialBoard = initialBoard;
        this.controller = controller;
        this.style = style;
    }

    public InitialBoard getInitialBoard() {
        return initialBoard;
    }

    public GameController getController() {
        return controller;
    }

    public ChessPieceStyle getStyle() {
        return style;
    }


    public void setInitialBoard(InitialBoard initialBoard) {
        this.initialBoard = initialBoard;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void setStyle(ChessPieceStyle style) {
        this.style = style;
    }
}
