package gui.game.mode;

import gui.game.view.BoardView;
import gui.game.view.Tile;
import myChess.boards.Board;
import myChess.images.ChessPieceStyle;

import java.awt.*;

public interface GameController {

    String getHowToPlayText();

    void setBoardView(BoardView view);

    void setBoardModel(Board model);

    void setPieceStyle(ChessPieceStyle style);

    void onTileClicked(Tile tile);

    void addGameEndListener(GameEndListener l);

    void removeGameEndListener(GameEndListener l);

}
