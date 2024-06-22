package myChess.setup;

import gui.game.view.BoardView;
import myChess.boards.Board;

public class InitialBoard {

    private Board model;
    private BoardView view;

    public InitialBoard(Board model, BoardView view) {
        this.model = model;
        this.view = view;
    }

    public Board getModel() {
        return model;
    }

    public BoardView getView() {
        return view;
    }

    public void setModel(Board model) {
        this.model = model;
    }

    public void setView(BoardView view) {
        this.view = view;
    }
}
