package myChess.images;

import javax.swing.*;
import java.awt.*;

public class DuckImage implements PieceImage {

    private Image image;

    @Override
    public Image loadImage(Object spec) {
        if (image != null) return image;
        return (image = new ImageIcon("/home/kali/IdeaProjects/MyChess/resources/images/duck.png").getImage());
    }
}
