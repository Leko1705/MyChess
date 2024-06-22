package gui.game.mode;

import myChess.boards.Board;
import myChess.boards.Position;

public interface TileClickListener {

    boolean onTileClicked(Position pos, Board board);
}
