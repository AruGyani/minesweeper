package src.game;
public class Cell {
    public boolean covered;
    public boolean flagged;

    public int x, y;

    public int value;
    
    public Cell(int value) {
        this.value = value;

        this.covered = true;
        this.flagged = false;
    }
}
