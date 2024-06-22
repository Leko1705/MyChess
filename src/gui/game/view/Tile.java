package gui.game.view;

import myChess.boards.Position;

import javax.swing.*;
import java.awt.*;

public class Tile extends JLabel {

    private final int x, y;

    private boolean isSelected = false;

    private final Color color;

    private final Color selectColor;

    public Tile(Color color, Color selectColor, int x, int y){
        this.color = color;
        this.selectColor = selectColor;
        setOpaque(true);
        setVisible(true);
        this.x = x;
        this.y = y;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        repaint();
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        setBackground(isSelected ? selectColor : color);
        super.paintComponent(g);
    }

    @Override
    public String toString() {
        return "Tile=[color=" + getForeground() + ", pos=" + getPosition() + "]";
    }
}
