package myChess.images;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;

public class KingImage implements PieceImage {

    private static Image white = null;
    private static Image black = null;
    private static Image blue = null;
    private static Image red = null;

    @Override
    public Image loadImage(Object spec) {
        if (spec.equals(Color.WHITE)) {
            if (white != null) return white;
            return (white = new ImageIcon("/home/kali/IdeaProjects/MyChess/resources/images/king_white.png").getImage());
        }
        else if (spec.equals(Color.BLACK)){
            if (black != null) return black;
            return (black = new ImageIcon("/home/kali/IdeaProjects/MyChess/resources/images/king_black.png").getImage());
        }
        else if (spec.equals(Color.BLUE)){
            if (blue != null) return blue;
            return (blue = new ImageIcon("/home/kali/IdeaProjects/MyChess/resources/images/king_blue.png").getImage());
        }
        else if (spec.equals(Color.RED)){
            if (red != null) return red;
            return (red = new ImageIcon("/home/kali/IdeaProjects/MyChess/resources/images/king_red.png").getImage());
        }
        throw new IllegalArgumentException(spec.toString());
    }

}
