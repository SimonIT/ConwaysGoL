import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

/**
 * advanced version of "Conway's Game of Life"; cells with dynamic color are used
 */
public class GameOfWildlife implements IGameOfLife {
    // width and height of grid are defined as X_SIZE and Y_SIZE
    private int X_SIZE = IGameOfLife.SIZE;
    private int Y_SIZE = IGameOfLife.SIZE;
    // new object of class CellListener is created for being able to generate new alive cells on grid
    private CellListener cellListener;
    // this 2D grid holds all cells in the form of Cell objects;
    private Cell[][] grid = new Cell[X_SIZE][Y_SIZE];
    // random integers are needed for initialization of the grid
    private Random rand = new Random();

    // subclass CellListener is used for being able to create new alive cells by clicking on GUI
    // TODO: Simon's comment
    class CellListener implements MouseListener, MouseMotionListener {
        private VisualGameOfLife visualGameOfLife;

        /**
         * needed to access the window by the listener
         *
         * @param visualGameOfLife
         */
        void setVisualGameOfLife(VisualGameOfLife visualGameOfLife) {
            this.visualGameOfLife = visualGameOfLife;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            changeGridOnMousePosition(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            changeGridOnMousePosition(e);
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

        /**
         * creates new cells or kills cells
         *
         * @param e a mouse event
         */
        void changeGridOnMousePosition(MouseEvent e) {
            // store bounds for the calculation
            Rectangle bounds = this.visualGameOfLife.getBounds();
            // calculate the x array position from the mouse x position
            int x = Math.round((float) e.getX() / (bounds.width - 1) * X_SIZE);
            // calculate the y array position from the mouse y position
            int y = Math.round(Y_SIZE - (float) e.getY() / (bounds.height - 1) * Y_SIZE);
            // if the mouse is on the grid
            if (Y_SIZE > x && x > -1 && X_SIZE > y && y > -1) {
                if (e.isShiftDown()) {
                    // if shift is pressed  kill the cell
                    grid[x][y].setAlive(false);
                } else {
                    // create a new random colored cell
                    grid[x][y] = Cell.createWithRandomColor();
                }
                // refresh the screen
                visualGameOfLife.refresh(grid);
            }
        }
    }

    /**
     * start of program
     *
     * @param args console args
     */
    public static void main(String[] args) {
        // create a new object this class
        GameOfWildlife gOL = new GameOfWildlife();
        // fill grid with random initial population
        gOL.init();
        // create a new object of class "VisualGameOfLife" for gui representation of grid and provide reference of grid
        VisualGameOfLife visualGameOfLife = new VisualGameOfLife(gOL.grid);
        // to refresh the picture needs the cell listener the visualGameOfLife
        gOL.getCellListener().setVisualGameOfLife(visualGameOfLife);
        // adds the cell listener to the windows to draw if a click appears
        visualGameOfLife.addCellListener(gOL.getCellListener());
        // start infinite loop for simulating the game
        while (true) {
            // wait for 500ms (0.5s) so simulation doesn't happen too fast; a try-catch block is needed for this
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // retrieve new generation from current one
            gOL.runGeneration();
            // show current generation in GUI with the help of "VisualGameOfLife"
            visualGameOfLife.refresh(gOL.grid);
        }
    }

    GameOfWildlife() {
        // create a new cell listener
        this.cellListener = new CellListener();
    }

    /**
     * randomly fill grid with alive and dead cells
     */
    @Override
    public void init() {
        // iterate through every cell in grid
        for (int x = 0; x < X_SIZE; ++x) {
            for (int y = 0; y < Y_SIZE; ++y) {
                // // randomly (50% / 50%) set grid cells to be alive (with random color) or dead
                grid[x][y] = rand.nextBoolean() ? Cell.createWithRandomColor() : new Cell();
            }
        }
    }

    /**
     * Not implemented because we can't display mixed colors using console output
     */
    @Deprecated
    @Override
    public void showGrid() {
    }

    /**
     * set a specific cell in grid to be alive
     *
     * @param x: x position of cell
     * @param y: y position of cell
     */
    @Override
    public void setAlive(int x, int y) {
        this.grid[x][y].setAlive(true);
    }

    /**
     * set a specific cell in grid to be dead
     *
     * @param x  x position of cell
     * @param y: y position of cell
     */
    @Override
    public void setDead(int x, int y) {
        this.grid[x][y].setAlive(false);
    }

    // "getLiveNeighbors" is not needed by this class; instead "getLiveNeighborsAndMixColor" is used
    @Deprecated
    @Override
    public int getLiveNeighbors(int x, int y) {
        return 0;
    }

    /**
     * determine how many of the 8 neighbors of a specific cell are alive and determine the average color of neighbors
     *
     * @param x: x position of cell
     * @param y: y position of cell
     * @param c: Cell object passed by reference to obtain mixed color
     * @return amount of alive neighboring cells
     */
    private int getLiveNeighborsAndMixColor(int x, int y, Cell c) {
        // "neighborsTotal" is 0 at first and will be returned later
        int neighbors = 0;
        // r, g and b will be the color values (red, green, blue) for the mixed color of the cell in question
        int r = c.getColor().getRed(), g = c.getColor().getGreen(), b = c.getColor().getBlue();
        // iterate through all neighbors within a 3x3 grid around the cell
        for (int xCheck = -1; xCheck < 2; ++xCheck) {
            for (int yCheck = -1; yCheck < 2; ++yCheck) {
                // cell that wants to know the amount of its neighbors is not included in the check
                if (xCheck == 0 && yCheck == 0) {
                    continue;
                }
                // x and y positions of this neighbor are determined; % is used to wrap around the edges (donut world)
                int xNeighbor = (x + xCheck + X_SIZE) % X_SIZE;
                int yNeighbor = (y + yCheck + Y_SIZE) % Y_SIZE;
                // if this neighbor is alive; increment "neighborsTotal" by one and update values to corresponding variables
                if (this.grid[xNeighbor][yNeighbor].getAlive()) {
                    ++neighbors;
                    r += this.grid[xNeighbor][yNeighbor].getColor().getRed();
                    g += this.grid[xNeighbor][yNeighbor].getColor().getGreen();
                    b += this.grid[xNeighbor][yNeighbor].getColor().getBlue();
                }
            }
        }

        // average color is applied to Cell object c; "neighbors + 1" is used to avoid dividing by zero
        c.setColor(new Color(Math.round((float) r / (neighbors + 1)), Math.round((float) g / (neighbors + 1)), Math.round((float) b / (neighbors + 1))));
        // return the total amount of neighbors of cell at (x, y)
        return neighbors;
    }

    /**
     * this method provides the logic for creating the next generation of the simulation
     */
    @Override
    public void runGeneration() {
        // "newGrid" will contain the next generation
        Cell[][] newGrid = new Cell[X_SIZE][Y_SIZE];
        // iterate through all cells of the grid
        for (int x = 0; x < X_SIZE; ++x) {
            for (int y = 0; y < Y_SIZE; ++y) {
                // determine the amount of alive neighbors and apply mixed color to "mixexdCell" in case it is needed
                Cell mixedCell = new Cell(grid[x][y]);
                int neighborsAlive = getLiveNeighborsAndMixColor(x, y, mixedCell);

                // set cells to be dead or alive depending on their current state and the amount of their neighbors
                newGrid[x][y] = new Cell();
                if (neighborsAlive < 2 || neighborsAlive > 3) {
                    newGrid[x][y].setAlive(false);
                } else if (neighborsAlive == 3) {
                    newGrid[x][y].setAlive(true);
                    if (grid[x][y].getAlive()) {
                        // adjust color of this cell to the colors of its neighbors
                        newGrid[x][y] = mixedCell;
                    } else {
                        // if cell is born just now, set its color to be random
                        newGrid[x][y] = Cell.createWithRandomColor();
                    }
                } else {
                    if (grid[x][y].getAlive()) {
                        newGrid[x][y] = mixedCell;
                    } else {
                        newGrid[x][y].setAlive(false);
                    }
                }
                // update age attribute
                newGrid[x][y].setAge(grid[x][y].getAge() + 1);
            }
        }
        //set grid to be the updated grid
        this.grid = newGrid;
    }

    /**
     * runs multiple simulation steps
     *
     * @param howMany: amount of steps to be simulated
     */
    @Override
    public void runGenerations(int howMany) {
        for (int i = 0; i < howMany; ++i) {
            runGeneration();
        }
    }

    @Deprecated
    @Override
    public int[][] getGrid() {
        // return null because we have here no int array and we have to match the interface
        return null;
    }

    /**
     * @return the cell grid
     */
    public Cell[][] getCellGrid() {
        return this.grid;
    }

    /**
     * @return the cell listener
     */
    CellListener getCellListener() {
        return this.cellListener;
    }
}
