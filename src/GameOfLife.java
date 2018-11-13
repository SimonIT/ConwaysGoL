import java.util.Random;

public class GameOfLife implements IGameOfLife {
    private int[][] grid = new int[IGameOfLife.SIZE][IGameOfLife.SIZE];
    private Random rand = new Random();

    public static void main(String[] args) {
        GameOfLife gOL = new GameOfLife();
        gOL.init();
        VisualGameOfLife visualGameOfLife = new VisualGameOfLife(gOL.getGrid());
        while (true) {
            gOL.runGeneration();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            visualGameOfLife.refresh(gOL.grid);
        }
    }

    @Override
    public void init() {
        for (int x = 0; x < grid.length; ++x) {
            for (int y = 0; y < grid.length; ++y) {
                grid[x][y] = rand.nextBoolean() ? IGameOfLife.ALIVE : IGameOfLife.DEAD;
            }
        }
    }

    @Override
    public void showGrid() {
        for (int i = 0; i < 100; ++i) {
            System.out.println();
        }
        for (int[] row : grid) {
            for (int state : row) {
                System.out.print(state == IGameOfLife.ALIVE ? '+' : '-');
            }
            System.out.println();
        }
    }

    @Override
    public void setAlive(int x, int y) {
        this.grid[x][y] = IGameOfLife.ALIVE;
    }

    @Override
    public void setDead(int x, int y) {
        this.grid[x][y] = IGameOfLife.DEAD;
    }

    @Override
    public int getLiveNeighbors(int x, int y) {
        int neighborsTotal = 0;
        for (int yCheck = -1; yCheck < 2; ++yCheck) {
            for (int xCheck = -1; xCheck < 2; ++xCheck) {
                if (xCheck == 0 && yCheck == 0) {
                    continue;
                }
                int xNeighbor = (x + xCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                int yNeighbor = (y + yCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                if (this.grid[xNeighbor][yNeighbor] == IGameOfLife.ALIVE) {
                    ++neighborsTotal;
                }
            }
        }
        return neighborsTotal;
    }

    @Override
    public void runGeneration() {
        int[][] newGrid = new int[IGameOfLife.SIZE][IGameOfLife.SIZE];
        for (int x = 0; x < IGameOfLife.SIZE; ++x) {
            for (int y = 0; y < IGameOfLife.SIZE; ++y) {
                int neighborsAlive = getLiveNeighbors(x, y);
                if (neighborsAlive < 2 || neighborsAlive > 3) {
                    newGrid[x][y] = IGameOfLife.DEAD;
                } else if (neighborsAlive == 3) {
                    newGrid[x][y] = IGameOfLife.ALIVE;
                } else {
                    newGrid[x][y] = this.grid[x][y];
                }
            }
        }
        this.grid = newGrid;
    }

    @Override
    public void runGenerations(int howMany_) {
        for (int i = 0; i < howMany_; ++i) {
            runGeneration();
        }
    }

    @Override
    public int[][] getGrid() {
        return this.grid;
    }
}
