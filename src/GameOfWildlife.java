import java.awt.*;
import java.util.Random;

public class GameOfWildlife implements IGameOfLife {
    Cell[][] grid = new Cell[IGameOfLife.SIZE][IGameOfLife.SIZE];

    public static void main(String[] args) {
        GameOfWildlife gOL = new GameOfWildlife();
        gOL.init();
        VisualGameOfLife visualGameOfLife = new VisualGameOfLife(gOL.grid);
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
        Random rand = new Random();
        for (int y = 0; y < grid.length; ++y) {
            for (int x = 0; x < grid.length; ++x) {
                if (rand.nextBoolean()) {
                    grid[y][x] = new Cell(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)), true);
                } else {
                    grid[y][x] = new Cell(Color.BLACK, false);
                }
            }
        }
    }

    @Override
    public void showGrid() { }

    @Override
    public void setAlive(int x, int y) {
        this.grid[y][x].setAlive(true);
    }

    @Override
    public void setDead(int x, int y) {
        this.grid[y][x].setAlive(false);
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
                if (this.grid[yNeighbor][xNeighbor].getAlive()) {
                    ++neighborsTotal;
                }
            }
        }
        return neighborsTotal;
    }

    public int getLiveNeighborsAndMixColor(int x, int y, Color c) {
        int neighbors = 0;
        int r = 0, g = 0, b = 0;
        for (int yCheck = -1; yCheck < 2; ++yCheck) {
            for (int xCheck = -1; xCheck < 2; ++xCheck) {
                if (yCheck == 0 && xCheck == 0) {
                    continue;
                }
                int xNeighbor = (x + xCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                int yNeighbor = (y + yCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                if (this.grid[yNeighbor][xNeighbor].getAlive()) {
                    ++neighbors;
                    r += this.grid[yNeighbor][xNeighbor].getColor().getRed();
                    g += this.grid[yNeighbor][xNeighbor].getColor().getGreen();
                    b += this.grid[yNeighbor][xNeighbor].getColor().getBlue();
                }
            }
        }
        c = new Color(Math.round((float) r / neighbors), Math.round((float) g / neighbors), Math.round((float) b / neighbors));
        return neighbors;
    }

    @Override
    public void runGeneration() {
        Cell[][] newGrid = new Cell[IGameOfLife.SIZE][IGameOfLife.SIZE];
        for (int y = 0; y < IGameOfLife.SIZE; ++y) {
            for (int x = 0; x < IGameOfLife.SIZE; ++x) {
                Color mixedColor = new Color(grid[y][x].getColor().getRed(), grid[y][x].getColor().getGreen(), grid[y][x].getColor().getBlue());
                int neighborsAlive = getLiveNeighborsAndMixColor(x, y, mixedColor);

                newGrid[y][x] = new Cell(Color.BLACK, false);
                if (neighborsAlive < 2 || neighborsAlive > 3) {
                    newGrid[y][x].setColor(Color.BLACK);
                    newGrid[y][x].setAlive(false);
                } else if (neighborsAlive == 3) {
                    newGrid[y][x].setColor(mixedColor);
                    newGrid[y][x].setAlive(true);
                } else {
                    if (grid[y][x].getAlive()) {
                        newGrid[y][x].setColor(mixedColor);
                    } else {
                        newGrid[y][x].setColor(Color.BLACK);
                    }
                }
            }
        }
        this.grid = newGrid;
    }

    @Override
    public void runGenerations(int howMany) {
        for (int i = 0; i < howMany; ++i) {
            runGeneration();
        }
    }

    @Override
    public int[][] getGrid() {
        return null;
    }
}
