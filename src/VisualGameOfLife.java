import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;

/**
 * canvas for drawing the cell objects
 */
class CanvasInt extends Canvas {
    private int grid[][];

    CanvasInt(int[][] grid) {
        this.grid = grid;
    }

    /**
     * @param grid the grid to set
     */
    void setGrid(int[][] grid) {
        this.grid = grid;
    }

    /**
     * update : wird bei repaint() aufgerufen
     */
    @Override
    public void update(Graphics g) {
        // Berechne die Breite und Höhe der Felder
        Rectangle frameBounds = getBounds();
        int width = frameBounds.width - 1;
        int height = frameBounds.height - 1;
        double xSize = (double) width / grid.length;
        double ySize = (double) height / grid[0].length;
        for (int xPos = 0; xPos < grid.length; xPos++) {
            for (int yPos = 0; yPos < grid[0].length; yPos++) {
                if (grid[xPos][yPos] == IGameOfLife.ALIVE) {
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
    @Override
    public void paint(Graphics g) {
        update(g);
    }
}

/**
 * canvas for drawing the cell objects
 */
class CanvasCell extends Canvas {
    private Cell grid[][];

    CanvasCell(Cell[][] grid) {
        this.grid = grid;
    }

    /**
     * sets the required listeners for the mouse drawing
     *
     * @param cellListener a cell listener
     */
    void addCellListener(GameOfWildlife.CellListener cellListener) {
        addMouseListener(cellListener);
        addMouseMotionListener(cellListener);
    }

    /**
     * @param grid the grid to set
     */
    void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    /**
     * update : wird bei repaint() aufgerufen
     */
    @Override
    public void update(Graphics g) {
        // Berechne die Breite und Höhe der Felder
        Rectangle frameBounds = getBounds();

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
    @Override
    public void paint(Graphics g) {
        update(g);
    }
}

/**
 * the window
 */
class VisualGameOfLife extends Frame {
    private CanvasInt canvasInt;
    private CanvasCell canvasCell;

    /**
     * creates aa windows from int array
     *
     * @param grid: a int grid
     */
    VisualGameOfLife(int[][] grid) {
        super("Game of Life");
        // Groesse des Feldes anpassen aber mit min 300x300
        setSize(Math.max(300, grid.length * 15), Math.max(300, grid[0].length * 15));
        // create a new canvas
        canvasInt = new CanvasInt(grid);
        //add the canvas to the window
        add(canvasInt, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                // close the window on x click
                dispose();
                System.exit(0);
            }
        });
        //set the window visible
        setVisible(true);
    }

    /**
     * creates a window from cell objects
     *
     * @param grid: a cell grid
     */
    VisualGameOfLife(Cell[][] grid) {
        super("Game of Wildlife");
        // Groesse des Feldes anpassen aber mit min 300x300
        setSize(Math.max(300, grid.length * 15), Math.max(300, grid[0].length * 15));
        // create a new canvas
        canvasCell = new CanvasCell(grid);
        //add the canvas to the window
        add(canvasCell, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                // close the window on x click
                dispose();
                System.exit(0);
            }
        });
        //set the window visible
        setVisible(true);
    }

    /**
     * Methode zum Neuzeichen des Gitters
     *
     * @param grid the new grid
     */
    void refresh(int[][] grid) {
        canvasInt.setGrid(grid);
        canvasInt.repaint();
    }

    /**
     * Methode zum Neuzeichen des Gitters
     *
     * @param grid the new grid
     */
    void refresh(Cell[][] grid) {
        canvasCell.setGrid(grid);
        canvasCell.repaint();
    }

    /**
     * adds the a listener to canvas
     *
     * @param cellListener: a cell listener
     */
    void addCellListener(GameOfWildlife.CellListener cellListener) {
        canvasCell.addCellListener(cellListener);
    }
}