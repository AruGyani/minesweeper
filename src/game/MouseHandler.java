package src.game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private Board b;

    public MouseHandler(Board b) {
        this.b = b;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
                
    }

    @Override
    public void mousePressed(MouseEvent e) {
                
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        b.mouseClicked(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
                
    }

    @Override
    public void mouseExited(MouseEvent e) {
                
    }
}
