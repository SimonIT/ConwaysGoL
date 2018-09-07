import java.util.Random;

public class GameOfLife implements IGameOfLife {
    int[][] grid = new int[IGameOfLife.SIZE][IGameOfLife.SIZE];

    public static void main(String[] args) {
        GameOfLife gOL = new GameOfLife();
        gOL.init();
        while (true) {
            gOL.showGrid();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gOL.runGeneration();
        }
    }

    @Override
    public void init() {
        Random rand = new Random();
        for (int y = 0; y < grid.length; ++y) {
            for (int x = 0; x < grid.length; ++x) {
                grid[y][x] = rand.nextBoolean() ? IGameOfLife.ALIVE : IGameOfLife.DEAD;
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
        this.grid[y][x] = IGameOfLife.ALIVE;
    }

    @Override
    public void setDead(int x, int y) {
        this.grid[y][x] = IGameOfLife.DEAD;
    }

    @Override
    public int getLiveNeighbors(int x, int y) {
        int neighborsTotal = 0;
        for (int yCheck = -1; yCheck < 2; ++yCheck) {
            for (int xCheck = -1; xCheck < 2; ++xCheck) {
                if (yCheck == 0 && xCheck == 0) {
                    continue;
                }
                int xNeighbor = (x + xCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                int yNeighbor = (y + yCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                if (this.grid[yNeighbor][xNeighbor] == IGameOfLife.ALIVE) {
                    ++neighborsTotal;
                }
            }
        }
        return neighborsTotal;
    }

    @Override
    public void runGeneration() {
        for (int y = 0; y < grid.length; ++y) {
            for (int x = 0; x < grid.length; ++x) {
                int neighborsAlive = getLiveNeighbors(x, y);
                if (neighborsAlive < 2 || neighborsAlive > 3) {
                    this.grid[y][x] = IGameOfLife.DEAD;
                } else if (neighborsAlive == 3) {
                    this.grid[y][x] = IGameOfLife.ALIVE;
                }
            }
        }
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
