package myChess.images;

public class DefaultStyle implements ChessPieceStyle {

    @Override
    public PieceImage getPawnImage() {
        return new PawnImage();
    }

    @Override
    public PieceImage getKnightImage() {
        return new KnightImage();
    }

    @Override
    public PieceImage getBishopImage() {
        return new BishopImage();
    }

    @Override
    public PieceImage getRookImage() {
        return new RookImage();
    }

    @Override
    public PieceImage getQueenImage() {
        return new QueenImage();
    }

    @Override
    public PieceImage getKingImage() {
        return new KingImage();
    }

}
