import java.awt.*;
import java.util.Random;

public class GameOfWildlife implements IGameOfLife {
    Cell[][] grid = new Cell[IGameOfLife.SIZE][IGameOfLife.SIZE];
    Random rand = new Random();

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
        for (int y = 0; y < grid.length; ++y) {
            for (int x = 0; x < grid.length; ++x) {
                grid[y][x] = rand.nextBoolean() ? Cell.createWithRandomColor() : new Cell();
            }
        }
    }

    @Override
    public void showGrid() {
    }

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

    public int getLiveNeighborsAndMixColor(int x, int y, Cell c) {
        int neighbors = 0;
        int r = c.getColor().getRed(), g = c.getColor().getGreen(), b = c.getColor().getBlue();
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

        c.setColor(new Color(Math.round((float) r / (neighbors + 1)), Math.round((float) g / (neighbors + 1)), Math.round((float) b / (neighbors + 1))));
        return neighbors;
    }

    @Override
    public void runGeneration() {
        Cell[][] newGrid = new Cell[IGameOfLife.SIZE][IGameOfLife.SIZE];

        for (int y = 0; y < IGameOfLife.SIZE; ++y) {
            for (int x = 0; x < IGameOfLife.SIZE; ++x) {
                Cell mixedCell = new Cell(grid[y][x]);
                int neighborsAlive = getLiveNeighborsAndMixColor(x, y, mixedCell);

                newGrid[y][x] = new Cell();
                if (neighborsAlive < 2 || neighborsAlive > 3) {
                    newGrid[y][x].setColor(Color.BLACK);
                    newGrid[y][x].setAlive(false);
                } else if (neighborsAlive == 3) {
                    newGrid[y][x].setAlive(true);
                    if (grid[y][x].getAlive()) {
                        newGrid[y][x] = mixedCell;
                    } else {
                        newGrid[y][x] = Cell.createWithRandomColor();
                    }
                } else {
                    if (grid[y][x].getAlive()) {
                        newGrid[y][x] = mixedCell;
                    } else {
                        newGrid[y][x].setColor(Color.BLACK);
                    }
                }
                newGrid[y][x].setAge(grid[y][x].getAge() + 1);
            }
        }

        // special effect: old cells die and young ones spawn around them -->  AWESOME!!!
        for (int y = 0; y < IGameOfLife.SIZE; ++y) {
            for (int x = 0; x < IGameOfLife.SIZE; ++x) {
                if (newGrid[y][x].getAge() > 50 && newGrid[y][x].getAlive()) {
                    for (int yOffset = y - 1; yOffset < y + 2; ++yOffset) {
                        if (yOffset < 0 || yOffset >= IGameOfLife.SIZE) {continue;} // out of bound
                        for (int xOffset = x - 1; xOffset < x + 2; ++xOffset) {
                            if (xOffset < 0 || xOffset >= IGameOfLife.SIZE) {continue;} // out of bound
                            newGrid[yOffset][xOffset] = rand.nextBoolean() ? Cell.createWithRandomColor() : new Cell();
                        }
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
