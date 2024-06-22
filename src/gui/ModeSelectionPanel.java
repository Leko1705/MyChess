package gui;

import gui.game.mode.*;
import gui.game.view.BoardView;
import gui.game.view.GridBoardView;
import gui.game.view._4PlayerBoardView;
import myChess.boards.Board;
import myChess.boards.GridBoard;
import myChess.boards._4PlayerGridBoard;
import myChess.images.DefaultStyle;
import myChess.setup.InitialBoard;
import myChess.setup.InitialGame;
import myChess.team.Side;
import myChess.team.SimpleTeam;
import myChess.team.Team;
import myChess.utils.InitialPosition;
import myChess.utils.InitialPositions;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModeSelectionPanel extends JPanel {

    public ModeSelectionPanel(StackPanel sceneManager) {

        JButton classicButton = new JButton("classic");
        classicButton.addActionListener(e -> {
            Team white = new SimpleTeam("white", Side.BOTTOM, Color.WHITE);
            Team black = new SimpleTeam("black", Side.TOP, Color.BLACK);
            Board boardModel = new GridBoard(8, 8);
            BoardView boardView = new GridBoardView(8, 8);

            sceneManager.push(new GamePanel(
                    sceneManager,
                    new InitialGame(new InitialBoard(boardModel, boardView), new ClassicChess(List.of(white, black)), new DefaultStyle())
            ));

            boardModel.getInitializer().initialize(List.of(white, black), List.of(InitialPositions.DEFAULT, InitialPositions.DEFAULT));
        });
        add(classicButton);


        JButton antiButton = new JButton("robber's chess");
        antiButton.addActionListener(e -> {
            Team white = new SimpleTeam("white", Side.BOTTOM, Color.WHITE);
            Team black = new SimpleTeam("black", Side.TOP, Color.BLACK);
            Board boardModel = new GridBoard(8, 8);
            BoardView boardView = new GridBoardView(8, 8);

            sceneManager.push(new GamePanel(
                    sceneManager,
                    new InitialGame(new InitialBoard(boardModel, boardView), new RobbersChess(List.of(white, black)), new DefaultStyle())
            ));

            boardModel.getInitializer().initialize(List.of(white, black), List.of(InitialPositions.DEFAULT, InitialPositions.DEFAULT));
        });
        add(antiButton);


        JButton magicButton = new JButton("magic chess");
        magicButton.addActionListener(e -> {
            Team white = new SimpleTeam("white", Side.BOTTOM, Color.WHITE);
            Team black = new SimpleTeam("black", Side.TOP, Color.BLACK);
            Board boardModel = new GridBoard(8, 8);
            BoardView boardView = new GridBoardView(8, 8);

            sceneManager.push(new GamePanel(
                    sceneManager,
                    new InitialGame(new InitialBoard(boardModel, boardView), new MagicChess(List.of(white, black)), new DefaultStyle())
            ));

            boardModel.getInitializer().initialize(List.of(white, black), List.of(InitialPositions.DEFAULT, InitialPositions.DEFAULT));
        });
        add(magicButton);



        JButton atomButton = new JButton("atom chess");
        atomButton.addActionListener(e -> {
            Team white = new SimpleTeam("white", Side.BOTTOM, Color.WHITE);
            Team black = new SimpleTeam("black", Side.TOP, Color.BLACK);
            Board boardModel = new GridBoard(8, 8);
            BoardView boardView = new GridBoardView(8, 8);

            sceneManager.push(new GamePanel(
                    sceneManager,
                    new InitialGame(new InitialBoard(boardModel, boardView), new AtomChess(List.of(white, black)), new DefaultStyle())
            ));

            boardModel.getInitializer().initialize(List.of(white, black), List.of(InitialPositions.DEFAULT, InitialPositions.DEFAULT));
        });
        add(atomButton);



        JButton duckChess = new JButton("duck chess");
        duckChess.addActionListener(e -> {
            Team white = new SimpleTeam("white", Side.BOTTOM, Color.WHITE);
            Team black = new SimpleTeam("black", Side.TOP, Color.BLACK);
            Board boardModel = new GridBoard(8, 8);
            BoardView boardView = new GridBoardView(8, 8);

            sceneManager.push(new GamePanel(
                    sceneManager,
                    new InitialGame(new InitialBoard(boardModel, boardView), new DuckChess(List.of(white, black)), new DefaultStyle())
            ));

            boardModel.getInitializer().initialize(List.of(white, black), List.of(InitialPositions.DEFAULT, InitialPositions.DEFAULT));
        });
        add(duckChess);


        JButton chess5x5 = new JButton("5x5 chess");
        chess5x5.addActionListener(e -> {
            Team white = new SimpleTeam("white", Side.BOTTOM, Color.WHITE);
            Team black = new SimpleTeam("black", Side.TOP, Color.BLACK);
            Board boardModel = new GridBoard(5, 5);
            BoardView boardView = new GridBoardView(5, 5);

            sceneManager.push(new GamePanel(
                    sceneManager,
                    new InitialGame(new InitialBoard(boardModel, boardView), new ClassicChess(List.of(white, black)), new DefaultStyle())
            ));

            boardModel.getInitializer().initialize(List.of(white, black), List.of(InitialPositions.GRID_5x5, InitialPositions.GRID_5x5));
        });
        add(chess5x5);


        JButton _3PlayerChess = new JButton("3 player chess");
        _3PlayerChess.addActionListener(e -> {
            Team white = new SimpleTeam("white", Side.BOTTOM, Color.WHITE);
            Team black = new SimpleTeam("black", Side.TOP, Color.BLACK);
            Team blue = new SimpleTeam("red", Side.RIGHT, Color.BLUE);
            Board boardModel = new _4PlayerGridBoard(8, 1);
            BoardView boardView = new _4PlayerBoardView(8, 1);

            sceneManager.push(new GamePanel(
                    sceneManager,
                    new InitialGame(new InitialBoard(boardModel, boardView), new _4PlayerChess(List.of(white, black)), new DefaultStyle())
            ));


            InitialPosition pos = InitialPositions.getDefaultPosition(3, 3);
            boardModel.getInitializer().initialize(List.of(white, black, blue), List.of(pos, pos, pos));
        });
        add(_3PlayerChess);


        JButton _4PlayerChess = new JButton("4 player chess");
        _4PlayerChess.addActionListener(e -> {
            Team white = new SimpleTeam("white", Side.BOTTOM, Color.WHITE);
            Team black = new SimpleTeam("black", Side.TOP, Color.BLACK);
            Team blue = new SimpleTeam("blue", Side.LEFT, Color.BLUE);
            Team red = new SimpleTeam("red", Side.RIGHT, Color.RED);
            Board boardModel = new _4PlayerGridBoard(8, 1);
            BoardView boardView = new _4PlayerBoardView(8, 1);

            sceneManager.push(new GamePanel(
                    sceneManager,
                    new InitialGame(new InitialBoard(boardModel, boardView), new _4PlayerChess(List.of(white, black)), new DefaultStyle())
            ));

            InitialPosition pos = InitialPositions.getDefaultPosition(3, 3);
            boardModel.getInitializer().initialize(List.of(white, black, red, blue), List.of(pos, pos, pos, pos));
        });
        add(_4PlayerChess);
    }

}
