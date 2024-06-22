package myChess.team;

public enum Side {

    TOP(0, -1),
    BOTTOM(0, 1),
    LEFT(1, 0),
    RIGHT(-1, 0);

    Side(int xDirection, int yDirection){
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }

    public final int xDirection;
    public final int yDirection;

}
