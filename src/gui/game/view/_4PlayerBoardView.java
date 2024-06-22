package gui.game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class _4PlayerBoardView extends BoardView {

    private final int baseLineSize;
    private final Tile[][] tiles;
    private final int offset;

    public _4PlayerBoardView(int baseLineSize, int offs){
        this.baseLineSize = baseLineSize;
        this.offset = offs;
        int size = baseLineSize+4+(offs*2);
        tiles = new Tile[size][size];
        setLayout(new GridLayout(size, size));
        initBoard(baseLineSize);
    }

    @Override
    public Tile getTile(int x, int y) {
        return tiles[tiles.length - x - 1][y];
    }

    @Override
    public Dimension getVisibleDimension() {
        return new Dimension(baseLineSize+4, baseLineSize+4);
    }

    @Override
    public Point getOffset() {
        return new Point(0, 0);
    }

    private void initBoard(int size){
        for (int x = size+3+offset*2; x >= 0; x--){
            for (int y = size+3+offset*2; y >= 0; y--){
                if (isInvalidTileCandidate(x, size, offset) && isInvalidTileCandidate(y, size, offset)) {
                    add(new JLabel());
                }
                else {
                    Tile tile = createTile(x, size + offset + 2 - y);
                    add(tile);
                    tiles[y][x] = tile;
                }
            }
        }
    }

    private boolean isInvalidTileCandidate(int val, int baseLineSize, int offs){
        return val < 2 + offs || val > 1 + baseLineSize + offs;
    }


    private Tile createTile(int x, int y){
        Tile tile = new Tile(
                (x + y) % 2 != 0 ? Color.WHITE : Color.decode("#769656"),
                new Color(1, 0, 0, 0.5f),
                y + 1 + offset,
                x);
        tile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getController().onTileClicked(tile);
            }
        });
        return tile;
    }
}
