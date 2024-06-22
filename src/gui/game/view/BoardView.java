package gui.game.view;

import gui.game.mode.GameController;
import myChess.boards.Board;

import javax.swing.*;
import java.awt.*;

public abstract class BoardView extends JPanel {

    private GameController controller;

    public GameController getController() {
        return controller;
    }

    public abstract Tile getTile(int x, int y);

    public abstract Dimension getVisibleDimension();

    public abstract Point getOffset();

    public void setController(GameController controller){
        this.controller = controller;
    }

}
