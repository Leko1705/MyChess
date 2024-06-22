package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame(){
        super("MyChess");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setSize(width, height);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setContentPane(new MainPanel());

        setVisible(true);
    }

    private static class MainPanel extends JPanel {
        public MainPanel(){
            setLayout(new BorderLayout());
            StackPanel stackPanel = new StackPanel(this);
            add(stackPanel, BorderLayout.CENTER);
            stackPanel.push(new ModeSelectionPanel(stackPanel));
        }
    }

}
