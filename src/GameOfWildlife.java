import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Random;

public class GameOfWildlife implements IGameOfLife {
    Cell[][] grid = new Cell[IGameOfLife.SIZE][IGameOfLife.SIZE];

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        GameOfWildlife gOL = new GameOfWildlife();
        gOL.init();
        while (true) {
            gOL.showGrid();
            try {
                Thread.sleep(100);
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
                if (rand.nextBoolean()) {
                    grid[y][x] = rand.nextBoolean() ? new Cell(Ansi.Color.YELLOW, true) : new Cell(Ansi.Color.MAGENTA, true);
                } else {
                    grid[y][x] = new Cell(Ansi.Color.BLACK, false);
                }
            }
        }
    }

    @Override
    public void showGrid() {
        System.out.print(Ansi.ansi().eraseScreen());
        for (Cell[] row : grid) {
            for (Cell state : row) {
                System.out.print(Ansi.ansi().fg(state.getColor()).a('█'));
            }
            System.out.println();
        }
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

    public Ansi.Color getDominantColor(int x, int y) {
        int color1Count = 0, color2Count = 0;
        for (int yCheck = -1; yCheck < 2; ++yCheck) {
            for (int xCheck = -1; xCheck < 2; ++xCheck) {
                if (yCheck == 0 && xCheck == 0) {
                    continue;
                }
                int xNeighbor = (x + xCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                int yNeighbor = (y + yCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                if (this.grid[yNeighbor][xNeighbor].getAlive()) {
                    if (this.grid[yNeighbor][xNeighbor].getColor() == Ansi.Color.YELLOW) {
                        ++color1Count;
                    } else {
                        ++color2Count;
                    }
                }
            }
        }
        if (color1Count > color2Count) {
            return Ansi.Color.YELLOW;
        }
        return Ansi.Color.MAGENTA;
    }

    @Override
    public void runGeneration() {
        for (int y = 0; y < grid.length; ++y) {
            for (int x = 0; x < grid.length; ++x) {
                int neighborsAlive = getLiveNeighbors(x, y);
                if (grid[y][x].getAlive()) {
                    if (neighborsAlive < 2 || neighborsAlive > 3) {
                        this.setDead(x, y);
                        this.grid[y][x].setColor(Ansi.Color.BLACK);
                    }
                } else {
                    if (neighborsAlive == 3) {
                        this.setAlive(x, y);
                        this.grid[y][x].setColor(getDominantColor(x, y));
                    }
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
        return null;
    }
}
