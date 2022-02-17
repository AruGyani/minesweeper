package src.core;
// JPanel Imports
import java.awt.Color;
import java.awt.Dimension;
// Graphics
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import src.game.*;

/**
 * Color Palette
 *      - Black: #202027 | (32, 32, 39)
 *      - Grey: #454a5b | (69, 74, 91)
 *      - Red: #a72e32 | (167, 46, 50)
 *      - Blue: #20c7fa | (32, 199, 250)
 */

public class GamePanel extends JPanel {
    public int WIDTH = 496, HEIGHT = 496;
    public int TILE_SIZE = 31;
    
    public Board board = new Board(this);

    private MouseHandler mouse = new MouseHandler(board);
    private KeyHandler key = new KeyHandler(this);

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(32, 32, 39));
        setDoubleBuffered(true);
        addMouseListener(mouse);
        addKeyListener(key);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font font = new Font("SansSerif", Font.BOLD, 15);

        g2.setRenderingHints(rh);
        g2.setFont(font);

        board.render(g2);
    }
}
