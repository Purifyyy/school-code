package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.buttons.*;
import javax.swing.*;
import java.awt.*;

public class Game {
    JFrame frame = new JFrame("Rook in a Maze");
    JPanel mainPanel = new JPanel();
    JPanel guiPanel = new JPanel();
    JPanel gamePanel = new JPanel();

    Game() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        guiPanel.setLayout(new GridLayout(2,3));
        gamePanel.setLayout(new BorderLayout());
        LabelCounter cnt = new LabelCounter();

        Maze area = new Maze(31,cnt);
        Player player = new Player(area);

        mainPanel.add(guiPanel);

        guiPanel.add(cnt);
        guiPanel.add(new GoUpButton("↑",player));
        guiPanel.add(new ResetButton("RESET",player));
        guiPanel.add(new GoLeftButton("←",player));
        guiPanel.add(new GoDownButton("↓",player));
        guiPanel.add(new GoRightButton("→",player));

        gamePanel.addKeyListener(new KeyboardManager(player));
        MouseManager mouse = new MouseManager(player);
        gamePanel.add(mouse);
        gamePanel.addMouseMotionListener(mouse);
        gamePanel.addMouseListener(mouse);

        gamePanel.add(area, BorderLayout.CENTER);
        gamePanel.setFocusable(true);
        guiPanel.setPreferredSize(new Dimension(620,150));
        gamePanel.setPreferredSize(new Dimension(620,620));
        mainPanel.add(guiPanel);
        mainPanel.add(gamePanel);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
