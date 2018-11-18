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

    /**
     * constructor
     *
     * @param grid: provides reference to grid
     */
    CanvasInt(int[][] grid) {
        this.grid = grid;
    }

    /**
     * @param grid: the grid to set
     */
    void setGrid(int[][] grid) {
        this.grid = grid;
    }

    /**
     * called in repaint()
     *
     * @param g: java.awt.Graphics object
     */
    @Override
    public void update(Graphics g) {
        // calculate width and height of fields
        Rectangle frameBounds = getBounds();

        double xSize = (double) (frameBounds.width - 1) / grid.length;
        double ySize = (double) (frameBounds.height - 1) / grid[0].length;
        // iterate through all fields on grid
        for (int xPos = 0; xPos < grid.length; ++xPos) {
            for (int yPos = 0; yPos < grid[0].length; ++yPos) {
                // set cell to be yellow if alive or black if dead
                if (grid[xPos][yPos] == IGameOfLife.ALIVE) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.BLACK);
                }
                // actually color this cell
                g.fillRect((int) (xSize * xPos), (int) (ySize * (grid[0].length - 1 - yPos)), (int) xSize + 1,
                        (int) ySize + 1);
            }
        }
    }

    /**
     * paint will be called automatically
     *
     * @param g: java.awt.Graphics object
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

    /**
     * constructor
     *
     * @param grid: provides reference to grid
     */
    CanvasCell(Cell[][] grid) {
        this.grid = grid;
    }

    /**
     * sets the required listeners for being able to draw with mouse
     *
     * @param cellListener: a cell listener
     */
    void addCellListener(GameOfWildlife.CellListener cellListener) {
        addMouseListener(cellListener);
        addMouseMotionListener(cellListener);
    }

    /**
     * sets grid
     *
     * @param grid: the grid to set
     */
    void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    /**
     * called in repaint()
     *
     * @param g: java.awt.Graphics object
     */
    @Override
    public void update(Graphics g) {
        // calculate width and height of fields
        Rectangle frameBounds = getBounds();

        double xSize = (double) (frameBounds.width - 1) / grid.length;
        double ySize = (double) (frameBounds.height - 1) / grid[0].length;
        // iterate through all fields on grid
        for (int xPos = 0; xPos < grid.length; ++xPos) {
            for (int yPos = 0; yPos < grid[0].length; ++yPos) {
                // set wanted color
                g.setColor(grid[xPos][yPos].getColor());
                g.fillRect((int) (xSize * xPos), (int) (ySize * (grid[0].length - 1 - yPos)), (int) xSize + 1,
                        (int) ySize + 1);
            }
        }
    }

    /**
     * paint will be called automatically
     *
     * @param g: java.awt.Graphics object
     */
    @Override
    public void paint(Graphics g) {
        update(g);
    }
}

/**
 * GUI window
 */
class VisualGameOfLife extends Frame {
    private CanvasInt canvasInt;
    private CanvasCell canvasCell;

    /**
     * creates a window using the int array ("GameOfLife")
     *
     * @param grid: a int grid
     */
    VisualGameOfLife(int[][] grid) {
        super("Game of Life");
        // adjust size of window to size of grid; min-size is 300x300
        setSize(Math.max(300, grid.length * 15), Math.max(300, grid[0].length * 15));
        // create a new canvas
        canvasInt = new CanvasInt(grid);
        //add the canvas to the window
        add(canvasInt, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                // close the window on x click
                dispose();
                System.exit(0);
            }
        });
        //set the window to be visible
        setVisible(true);
    }

    /**
     * creates a window using the cell objects ("GameOfWildLife")
     *
     * @param grid: a Cell grid
     */
    VisualGameOfLife(Cell[][] grid) {
        super("Game of Wildlife");
        // adjust size of window to size of grid; min-size is 300x300
        setSize(Math.max(300, grid.length * 15), Math.max(300, grid[0].length * 15));
        // create a new canvas
        canvasCell = new CanvasCell(grid);
        //add the canvas to the window
        add(canvasCell, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                // close the window on x click
                dispose();
                System.exit(0);
            }
        });
        //set the window to be visible
        setVisible(true);
    }

    /**
     * necessary for cell listener mouse calculation
     *
     * @return bounds of canvas cell
     */
    Rectangle getCanvasCellBounds() {
        return this.canvasCell.getBounds();
    }

    /**
     * method to paint the int grid again
     *
     * @param grid: the new grid
     */
    void refresh(int[][] grid) {
        canvasInt.setGrid(grid);
        canvasInt.repaint();
    }

    /**
     * method to paint the Cell grid again
     *
     * @param grid the new grid
     */
    void refresh(Cell[][] grid) {
        canvasCell.setGrid(grid);
        canvasCell.repaint();
    }

    /**
     * adds a listener to canvas
     *
     * @param cellListener: a Cell listener
     */
    void addCellListener(GameOfWildlife.CellListener cellListener) {
        canvasCell.addCellListener(cellListener);
    }
}