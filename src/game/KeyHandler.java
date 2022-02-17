package src.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import src.core.GamePanel;

public class KeyHandler implements KeyListener {
    private GamePanel gp;
    private boolean ctrl = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_CONTROL) ctrl = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_CONTROL) ctrl = false;

        if (key == KeyEvent.VK_R && ctrl) {
            gp.board.reset();

            gp.repaint();
        }

        if (key == KeyEvent.VK_S && ctrl) {
            gp.board.displayAll();

            gp.repaint();
        }

        if (key == KeyEvent.VK_Q && ctrl) {
            gp.board.reset();
            gp.board.displayAll();

            gp.repaint();
        }

        if (key == KeyEvent.VK_UP) {
            if (gp.board.mines < 50) {
                gp.board.mines += 5;                
            }
        }

        if (key == KeyEvent.VK_DOWN) {
            if (gp.board.mines > 0) gp.board.mines -= 5;
        }
    }
}
