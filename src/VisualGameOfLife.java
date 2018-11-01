import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;

@SuppressWarnings("serial")
class CanvasInt extends Canvas {
    private int grid[][];

    public CanvasInt(int[][] grid) {
        this.grid = grid;
    }

    /**
     * @param grid the grid to set
     */
    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    /**
     * update : wird bei repaint() aufgerufen
     */
    public void update(Graphics g) {
        // Berechne die Breite und Höhe der Felder
        Rectangle frameBounds = getBounds();
        int width = frameBounds.width - 1;
        int height = frameBounds.height - 1;
        double xSize = (double) width / grid.length;
        double ySize = (double) height / grid[0].length;
        for (int xPos = 0; xPos < grid.length; xPos++) {
            for (int yPos = 0; yPos < grid[0].length; yPos++) {
                if (grid[xPos][yPos] == 1) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect((int) (xSize * xPos), (int) (ySize * (grid[0].length - 1 - yPos)), (int) xSize + 1,
                        (int) ySize + 1);
            }
        }
    }

    /**
     * paint wird beim ersten Mal aufgerufen => initialisierung
     */
    public void paint(Graphics g) {
        update(g);
    }
}

@SuppressWarnings("serial")
class CanvasCell extends Canvas {
    private Cell grid[][];
    private CellListener cellListener = new CellListener();

    public CanvasCell(Cell[][] grid) {
        this.grid = grid;
        this.cellListener.setGrid(this.grid);
        addMouseListener(this.cellListener);
        addMouseMotionListener(this.cellListener);
    }

    /**
     * @param grid the grid to set
     */
    public void setGrid(Cell[][] grid) {
        this.grid = grid;
        this.cellListener.setGrid(this.grid);
    }

    /**
     * update : wird bei repaint() aufgerufen
     */
    public void update(Graphics g) {
        // Berechne die Breite und Höhe der Felder
        Rectangle frameBounds = getBounds();
        this.cellListener.setBounds(frameBounds);
        double xSize = (double) (frameBounds.width - 1) / grid.length;
        double ySize = (double) (frameBounds.height - 1) / grid[0].length;
        for (int xPos = 0; xPos < grid.length; xPos++) {
            for (int yPos = 0; yPos < grid[0].length; yPos++) {
                g.setColor(grid[xPos][yPos].getColor());
                g.fillRect((int) (xSize * xPos), (int) (ySize * (grid[0].length - 1 - yPos)), (int) xSize + 1,
                        (int) ySize + 1);
            }
        }
    }

    /**
     * paint wird beim ersten Mal aufgerufen => initialisierung
     */
    public void paint(Graphics g) {
        update(g);
    }
}

class CellListener implements MouseListener, MouseMotionListener {

    private Cell grid[][];
    private Rectangle bounds;

    void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO change state of dragged cell
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO change state of clicked cell
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

// --- end of GoLCanvas ----------

@SuppressWarnings("serial")
public class VisualGameOfLife extends Frame {
    private CanvasInt canvasInt;
    private CanvasCell canvasCell;

    public VisualGameOfLife(int[][] grid) {
        super("Game of Life");
        // Groesse des Feldes anpassen aber mit min 300x300
        int xMax = grid.length;
        int yMax = grid[0].length;
        int xFrame = xMax * 15;
        int yFrame = yMax * 15;
        xFrame = Math.max(300, xFrame);
        yFrame = Math.max(300, yFrame);
        setSize(xFrame, yFrame);
        canvasInt = new CanvasInt(grid);
        add(canvasInt, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public VisualGameOfLife(Cell[][] grid) {
        super("Game of Life");
        // Groesse des Feldes anpassen aber mit min 300x300
        int xMax = grid.length;
        int yMax = grid[0].length;
        int xFrame = xMax * 15;
        int yFrame = yMax * 15;
        xFrame = Math.max(300, xFrame);
        yFrame = Math.max(300, yFrame);
        setSize(xFrame, yFrame);
        canvasCell = new CanvasCell(grid);
        add(canvasCell, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    /**
     * Methode zum Neuzeichen des Gitters
     */
    public void refresh(int[][] grid) {
        canvasInt.setGrid(grid);
        canvasInt.repaint();
    }

    /**
     * Methode zum Neuzeichen des Gitters
     */
    public void refresh(Cell[][] grid) {
        canvasCell.setGrid(grid);
        canvasCell.repaint();
    }
}