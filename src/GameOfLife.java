import java.util.Random;

/**
 * standard version of "Conway's Game of Life"; implements IGameOfLife
 */
public class GameOfLife implements IGameOfLife {
    // width and height of grid are defined as X_SIZE and Y_SIZE
    private int X_SIZE = IGameOfLife.SIZE;
    private int Y_SIZE = IGameOfLife.SIZE;
    // this 2D grid holds all cells in the form of integers
    private int[][] grid = new int[IGameOfLife.SIZE][IGameOfLife.SIZE];
    // random integers are needed for initialization of the grid
    private Random rand = new Random();

    /**
     * start of program
     *
     * @param args console args
     */
    public static void main(String[] args) {
        // create a new object this class
        GameOfLife gOL = new GameOfLife();
        // fill grid with random initial population
        gOL.init();
        // create a new object of class "VisualGameOfLife" for gui representation of grid and provide reference of grid
        VisualGameOfLife visualGameOfLife = new VisualGameOfLife(gOL.getGrid());
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

    /**
     * randomly fill grid with alive and dead cells
     */
    @Override
    public void init() {
        // iterate through every cell in grid
        for (int x = 0; x < grid.length; ++x) {
            for (int y = 0; y < grid.length; ++y) {
                // randomly (50% / 50%) set grid cells to be alive or dead
                grid[x][y] = rand.nextBoolean() ? IGameOfLife.ALIVE : IGameOfLife.DEAD;
            }
        }
    }

    /**
     * CURRENTLY NOT USED; this method shows the current generation in the console
     */
    @Override
    public void showGrid() {
        // add 100 blank lines for better readability
        for (int i = 0; i < 100; ++i) {
            System.out.println();
        }
        // iterate through every cell of grid
        for (int[] row : grid) {
            for (int state : row) {
                // alive cells are represented by 'X' while dead ones are not shown(' ')
                System.out.print(state == IGameOfLife.ALIVE ? 'X' : ' ');
            }
            // after every row of grid, add a line break for proper formatting
            System.out.println();
        }
    }

    /**
     * set a specific cell in grid to be alive
     *
     * @param x: x position of cell
     * @param y: y position of cell
     */
    @Override
    public void setAlive(int x, int y) {
        this.grid[x][y] = IGameOfLife.ALIVE;
    }

    /**
     * set a specific cell in grid to be dead
     *
     * @param x  x position of cell
     * @param y: y position of cell
     */
    @Override
    public void setDead(int x, int y) {
        this.grid[x][y] = IGameOfLife.DEAD;
    }

    /**
     * determine how many of the 8 neighbors of a specific cell are alive
     *
     * @param x: x position of cell
     * @param y: y position of cell
     * @return amount of alive neighboring cells
     */
    @Override
    public int getLiveNeighbors(int x, int y) {
        // "neighborsTotal" is 0 at first and will be returned later
        int neighborsTotal = 0;
        // iterate through all neighbors within a 3x3 grid around the cell
        for (int yCheck = -1; yCheck < 2; ++yCheck) {
            for (int xCheck = -1; xCheck < 2; ++xCheck) {
                // cell that wants to know the amount of its neighbors is not included in the check
                if (xCheck == 0 && yCheck == 0) {
                    continue;
                }
                // x and y positions of this neighbor are determined; % is used to wrap around the edges (donut world)
                int xNeighbor = (x + xCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                int yNeighbor = (y + yCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                // if this neighbor is alive; increment "neighborsTotal" by one
                if (this.grid[xNeighbor][yNeighbor] == IGameOfLife.ALIVE) {
                    ++neighborsTotal;
                }
            }
        }
        // return the total amount of neighbors of cell at (x, y)
        return neighborsTotal;
    }

    /**
     * this method provides the logic for creating the next generation of the simulation
     */
    @Override
    public void runGeneration() {
        // "newGrid" will contain the next generation
        int[][] newGrid = new int[IGameOfLife.SIZE][IGameOfLife.SIZE];
        // iterate through all cells of the grid
        for (int x = 0; x < IGameOfLife.SIZE; ++x) {
            for (int y = 0; y < IGameOfLife.SIZE; ++y) {
                // determine the amount of alive neighbors
                int neighborsAlive = getLiveNeighbors(x, y);
                // set cells to be dead or alive depending on their current state and the amount of their neighbors
                if (neighborsAlive < 2 || neighborsAlive > 3) {
                    newGrid[x][y] = IGameOfLife.DEAD;
                } else if (neighborsAlive == 3) {
                    newGrid[x][y] = IGameOfLife.ALIVE;
                } else {
                    newGrid[x][y] = this.grid[x][y];
                }
            }
        }
        // set grid to be the updated grid
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

    /**
     * get private grid from
     *
     * @return grid
     */
    @Override
    public int[][] getGrid() {
        return this.grid;
    }
}
