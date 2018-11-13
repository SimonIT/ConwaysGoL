import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

public class GameOfWildlife implements IGameOfLife {
    private CellListener cellListener;
    private Cell[][] grid = new Cell[IGameOfLife.SIZE][IGameOfLife.SIZE];
    private Random rand = new Random();

    public static void main(String[] args) {
        GameOfWildlife gOL = new GameOfWildlife();
        gOL.init();
        VisualGameOfLife visualGameOfLife = new VisualGameOfLife(gOL.grid);
        gOL.getCellListener().setVisualGameOfLife(visualGameOfLife);
        visualGameOfLife.addCellListener(gOL.getCellListener());
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

    GameOfWildlife() {
        this.cellListener = new CellListener();
    }

    @Override
    public void init() {
        for (int x = 0; x < grid.length; ++x) {
            for (int y = 0; y < grid.length; ++y) {
                grid[x][y] = rand.nextBoolean() ? Cell.createWithRandomColor() : new Cell();
            }
        }
    }

    @Deprecated
    @Override
    public void showGrid() {
    }

    @Override
    public void setAlive(int x, int y) {
        this.grid[x][y].setAlive(true);
    }

    @Override
    public void setDead(int x, int y) {
        this.grid[x][y].setAlive(false);
    }

    @Override
    public int getLiveNeighbors(int x, int y) {
        int neighborsTotal = 0;
        for (int xCheck = -1; xCheck < 2; ++xCheck) {
            for (int yCheck = -1; yCheck < 2; ++yCheck) {
                if (xCheck == 0 && yCheck == 0) {
                    continue;
                }
                int xNeighbor = (x + xCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                int yNeighbor = (y + yCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                if (this.grid[xNeighbor][yNeighbor].getAlive()) {
                    ++neighborsTotal;
                }
            }
        }
        return neighborsTotal;
    }

    int getLiveNeighborsAndMixColor(int x, int y, Cell c) {
        int neighbors = 0;
        int r = c.getColor().getRed(), g = c.getColor().getGreen(), b = c.getColor().getBlue();
        for (int xCheck = -1; xCheck < 2; ++xCheck) {
            for (int yCheck = -1; yCheck < 2; ++yCheck) {
                if (xCheck == 0 && yCheck == 0) {
                    continue;
                }
                int xNeighbor = (x + xCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                int yNeighbor = (y + yCheck + IGameOfLife.SIZE) % IGameOfLife.SIZE;
                if (this.grid[xNeighbor][yNeighbor].getAlive()) {
                    ++neighbors;
                    r += this.grid[xNeighbor][yNeighbor].getColor().getRed();
                    g += this.grid[xNeighbor][yNeighbor].getColor().getGreen();
                    b += this.grid[xNeighbor][yNeighbor].getColor().getBlue();
                }
            }
        }

        c.setColor(new Color(Math.round((float) r / (neighbors + 1)), Math.round((float) g / (neighbors + 1)), Math.round((float) b / (neighbors + 1))));
        return neighbors;
    }

    @Override
    public void runGeneration() {
        Cell[][] newGrid = new Cell[IGameOfLife.SIZE][IGameOfLife.SIZE];

        for (int x = 0; x < IGameOfLife.SIZE; ++x) {
            for (int y = 0; y < IGameOfLife.SIZE; ++y) {
                Cell mixedCell = new Cell(grid[x][y]);
                int neighborsAlive = getLiveNeighborsAndMixColor(x, y, mixedCell);

                newGrid[x][y] = new Cell();
                if (neighborsAlive < 2 || neighborsAlive > 3) {
                    newGrid[x][y].setColor(Color.BLACK);
                    newGrid[x][y].setAlive(false);
                } else if (neighborsAlive == 3) {
                    newGrid[x][y].setAlive(true);
                    if (grid[x][y].getAlive()) {
                        newGrid[x][y] = mixedCell;
                    } else {
                        newGrid[x][y] = Cell.createWithRandomColor();
                    }
                } else {
                    if (grid[x][y].getAlive()) {
                        newGrid[x][y] = mixedCell;
                    } else {
                        newGrid[x][y].setColor(Color.BLACK);
                        newGrid[x][y].setAlive(false);
                    }
                }
                newGrid[x][y].setAge(grid[x][y].getAge() + 1);
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

    @Deprecated
    @Override
    public int[][] getGrid() {
        return null;
    }

    public Cell[][] getCellGrid() {
        return this.grid;
    }

    CellListener getCellListener() {
        return this.cellListener;
    }

    class CellListener implements MouseListener, MouseMotionListener {
        private Rectangle bounds;
        private VisualGameOfLife visualGameOfLife;

        void setBounds(Rectangle bounds) {
            this.bounds = bounds;
        }

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

        void changeGridOnMousePosition(MouseEvent e) {
            int x = (int) ((float) e.getX() / (this.bounds.width - 1) * grid.length);
            int y = (int) (grid[0].length - (float) e.getY() / (this.bounds.height - 1) * grid[0].length);
            if (grid[0].length > x && x > -1 && grid.length > y && y > -1) {
                grid[x][y] = Cell.createWithRandomColor();
                visualGameOfLife.refresh(grid);
            }
        }
    }
}
