package src.game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import src.core.GamePanel;

public class Board {
    public int rows, cols;
    public int width, height;

    public int mines = 20;

    public Cell[][] cells;

    private GamePanel gp;
    private boolean gameOver = false;

    private Font numbers = new Font("SansSerif", Font.BOLD, 15);

    public Board(GamePanel gp) {
        this.rows = this.cols = 16;
        this.cells = new Cell[rows][cols];
        this.gp = gp;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(0);

                cells[i][j].x = j;
                cells[i][j].y = i;
            }
        }

        this.width = rows * gp.TILE_SIZE;
        this.height = cols * gp.TILE_SIZE;

        populate(mines);
    } 
    
    public void reset() {
        this.rows = this.cols = 16;
        this.cells = new Cell[rows][cols];
        this.gameOver = false;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(0);

                cells[i][j].x = j;
                cells[i][j].y = i;
            }
        }

        this.width = rows * gp.TILE_SIZE;
        this.height = cols * gp.TILE_SIZE;

        populate(mines);
    }

    public void displayAll() {
        gameOver = false;

        for (Cell[] row : cells) {
            for (Cell cell : row) cell.covered = false;
        }
    }

    public void populate(int mines) {
        int minesPlaced = 0;

        ArrayList<ArrayList<Cell>> available = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();

            for (int j = 0; j < cols; j++) {
                row.add(cells[i][j]);
            }

            available.add(row);
        }

        while (minesPlaced != mines) {
            int row = (int) (Math.random() * available.size());
            int col = (int) (Math.random() * available.get(row).size());

            int x = available.get(row).get(col).x;
            int y = available.get(row).get(col).y;

            cells[y][x].value = 9;

            available.get(row).remove(col);

            for (int i = y - 1; i <= y + 1; i++) {
                for (int j = x - 1; j <= x + 1; j++) {
                    if (i >= 0 && i < rows) {
                        if (j >= 0 && j < cols) {
                            if (cells[i][j].value != 9) cells[i][j].value++;
                        }
                    }
                }
            }

            minesPlaced++;
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int squareX = j * gp.TILE_SIZE + gp.WIDTH / 2 - (gp.TILE_SIZE * rows) / 2;
                int squareY = i * gp.TILE_SIZE;

                if (!cells[i][j].covered) g.setColor(new Color(69, 74, 91));
                else if (cells[i][j].flagged) g.setColor(new Color(167, 46, 50));
                else g.setColor(new Color(52, 60, 89));

                g.fillRect(squareX, squareY, gp.TILE_SIZE, gp.TILE_SIZE);

                g.setColor(new Color(132, 142, 176).darker());
                g.drawRect(squareX, squareY, gp.TILE_SIZE, gp.TILE_SIZE);

                if (!cells[i][j].covered) {
                    int valueX = squareX + gp.TILE_SIZE / 2;
                    int valueY = squareY + gp.TILE_SIZE / 2;

                    g.setFont(numbers);
                    
                    if (cells[i][j].value == 1) g.setColor(new Color(32, 199, 250));
                    if (cells[i][j].value == 2) g.setColor(new Color(20, 255, 134));
                    if (cells[i][j].value == 3) g.setColor(new Color(235, 52, 174));
                    if (cells[i][j].value == 4) g.setColor(new Color(225, 235, 52));
                    if (cells[i][j].value == 5) g.setColor(new Color(168, 52, 235));
                    if (cells[i][j].value == 6) g.setColor(new Color(247, 127, 40));
                    if (cells[i][j].value == 7) g.setColor(new Color(0, 242, 255));
                    if (cells[i][j].value == 8) g.setColor(Color.RED);

                    int centerX = g.getFontMetrics().stringWidth(String.valueOf(cells[i][j].value)) / 2;
                    int centerY = g.getFontMetrics().getHeight() / 4;

                    if (cells[i][j].value != 0 && cells[i][j].value != 9) g.drawString(String.valueOf(cells[i][j].value), valueX - centerX, valueY + centerY);
                    
                    g.setColor(Color.WHITE);
                    g.setFont(numbers);

                    int stringX = squareX + gp.TILE_SIZE / 2;
                    int stringY = squareY + gp.TILE_SIZE / 2;

                    centerX = g.getFontMetrics().stringWidth(String.valueOf("X")) / 2;
                    centerY = g.getFontMetrics().getHeight() / 4;

                    if (cells[i][j].value == 9) g.drawString("X", stringX - centerX, stringY + centerY);

                }

                if (gameOver) {
                    for (Cell[] row : cells) {
                        for (Cell cell : row) if (cell.value != 9) cell.covered = false;
                    }

                    int stringX = squareX + gp.TILE_SIZE / 2;
                    int stringY = squareY + gp.TILE_SIZE / 2;

                    g.setColor(Color.WHITE);
                    g.setFont(numbers);

                    int centerX = g.getFontMetrics().stringWidth(String.valueOf("X")) / 2;
                    int centerY = g.getFontMetrics().getHeight() / 4;

                    if (cells[i][j].value == 9) g.drawString("X", stringX - centerX, stringY + centerY);

                    g.setColor(new Color(52, 60, 89, 5));
                    g.fillRect(0, 0, cols * gp.TILE_SIZE, rows * gp.TILE_SIZE);

                    g.setColor(new Color(167, 46, 50).brighter());
                    g.setFont(new Font("SansSerif", Font.BOLD, 40));

                    int offX = gp.getWidth() / 2 - g.getFontMetrics().stringWidth("GAME OVER") / 2;

                    g.drawString("GAME OVER", offX, gp.getHeight() / 2);
                }
            }
        }
    }

    public void revealEmptyCells(int row, int col) {
        if (row >= 0 && col >= 0 && row < rows && col < cols) {
            if (cells[row][col].value != 9 && cells[row][col].covered && !cells[row][col].flagged) {
                cells[row][col].covered = false;

                if (cells[row][col].value == 0) {
                    revealEmptyCells(row, col - 1);
                    revealEmptyCells(row, col + 1);
                    revealEmptyCells(row - 1, col);
                    revealEmptyCells(row + 1, col);
                    revealEmptyCells(row - 1, col - 1);
                    revealEmptyCells(row - 1, col + 1);
                    revealEmptyCells(row + 1, col - 1);
                    revealEmptyCells(row + 1, col + 1);
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / gp.TILE_SIZE;
        int y = e.getY() / gp.TILE_SIZE;

        if (x >= 0 && x < cols && y >= 0 && y < rows) {
            // System.out.println("(" + x + ", " + y + ")");
            if (e.getButton() == 1) {
                if (cells[y][x].value == 9 && !gameOver && !cells[y][x].flagged && cells[y][x].covered) {
                    gameOver = true;
                    gp.repaint();
                }

                if (cells[y][x].covered && !cells[y][x].flagged) {
                    if (cells[y][x].value == 0 && !gameOver) {
                        revealEmptyCells(y, x);
                        
                        gp.repaint();
                    } else if (cells[y][x].value != 9) {
                        cells[y][x].covered = false;
                        gp.repaint();
                    }   
                }
            } else if (e.getButton() == 3) {
                if (cells[y][x].covered) cells[y][x].flagged = !cells[y][x].flagged;

                gp.repaint();
            } else if (e.getButton() == 2) {
                for (Cell[] row : cells) {
                    for (Cell cell : row) {
                        cell.covered = false;
                    }
                }
                
                gp.repaint();
            }
        }
    }
}
