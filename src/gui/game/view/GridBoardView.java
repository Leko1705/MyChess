package gui.game.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridBoardView extends BoardView {

    private final Tile[][] tiles;

    private final int w, h;

    public GridBoardView(int w, int h) {
        this.w = h;
        this.h = w;
        tiles = new Tile[w][h];
        setLayout(new GridLayout(h, w));
        initBoard(h, w);
    }

    @Override
    public Tile getTile(int x, int y) {
        return tiles[tiles.length - x - 1][y];
    }

    @Override
    public Dimension getVisibleDimension() {
        return new Dimension(w, h);
    }

    @Override
    public Point getOffset() {
        return new Point(0, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, getWidth(), getHeight());
    }

    private void initBoard(int w, int h){
        for (int x = w-1; x >= 0; x--){
            for (int y = h-1; y >= 0; y--){
                Tile tile = createTile(x, h - y - 1);
                add(tile);
                tiles[y][x] = tile;
            }
        }
    }

    private Tile createTile(int x, int y){
        Tile tile = new Tile(
                (x + y) % 2 != 0 ? Color.WHITE : Color.decode("#769656"),
                new Color(1, 0, 0, 0.5f),
                y,
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
