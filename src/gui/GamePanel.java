package gui;

import gui.game.mode.GameController;
import gui.game.view.BoardView;
import gui.game.view.RectangleLayout;
import myChess.boards.Board;
import myChess.boards.Position;
import myChess.images.DefaultStyle;
import myChess.setup.InitialBoard;
import myChess.setup.InitialGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel {

    public GamePanel(StackPanel sceneManager,
                     InitialGame initialGame){
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new FlowLayout());
        JButton quitButton = new JButton("quit");
        quitButton.addActionListener(e -> sceneManager.pop());
        northPanel.add(quitButton);
        JButton howToPlayButton = new JButton("How to play");
        northPanel.add(howToPlayButton);
        add(northPanel, BorderLayout.NORTH);

        InitialBoard board = initialGame.getInitialBoard();
        Board boardModel = board.getModel();
        BoardView boardView = board.getView();
        GameController controller = initialGame.getController();

        howToPlayButton.addActionListener(e ->
                JOptionPane.showMessageDialog(null, controller.getHowToPlayText(), "How to play", JOptionPane.INFORMATION_MESSAGE));

        boardView.setController(controller);


        boardView.setController(controller);
        controller.setBoardView(boardView);
        controller.setBoardModel(boardModel);
        controller.setPieceStyle(initialGame.getStyle());

        JPanel wrapper = new GameWrapperPanel(boardView);
        add(wrapper, BorderLayout.CENTER);

        controller.addGameEndListener((winner, ending)
                -> JOptionPane.showMessageDialog(null, ending.getMessage(winner), "end", JOptionPane.INFORMATION_MESSAGE));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (Position position : boardModel.getPositions())
                    boardModel.putPiece(position, boardModel.getPiece(position), true);
            }
        });
    }



    private static class GameWrapperPanel extends JPanel {

        public GameWrapperPanel(BoardView view){
            setLayout(new RectangleLayout());
            add(view);
        }

    }


}
