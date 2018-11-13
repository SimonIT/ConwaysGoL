import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * "Testfall 2 - Muster mit begrenzter Lebensdauer"
 */
public class Lifetime54 {

    static final int AGE = 54;

    private static void setPattern(IGameOfLife gameOfLife) {
        int halfSize = gameOfLife.SIZE / 2;
        gameOfLife.setAlive(halfSize, halfSize);
        gameOfLife.setAlive(halfSize + 1, halfSize);
        gameOfLife.setAlive(halfSize + 2, halfSize);
        gameOfLife.setAlive(halfSize, halfSize + 1);
        gameOfLife.setAlive(halfSize + 2, halfSize + 1);
        gameOfLife.setAlive(halfSize, halfSize + 2);
        gameOfLife.setAlive(halfSize + 2, halfSize + 2);

        gameOfLife.setAlive(halfSize, halfSize + 4);
        gameOfLife.setAlive(halfSize + 2, halfSize + 4);
        gameOfLife.setAlive(halfSize, halfSize + 5);
        gameOfLife.setAlive(halfSize + 2, halfSize + 5);
        gameOfLife.setAlive(halfSize, halfSize + 6);
        gameOfLife.setAlive(halfSize + 1, halfSize + 6);
        gameOfLife.setAlive(halfSize + 2, halfSize + 6);
    }

    @Test
    void noLifeAfter54CyclesInt() {
        int[][] deadGrid = new int[IGameOfLife.SIZE][IGameOfLife.SIZE];
        GameOfLife gameOfLife = new GameOfLife();
        for (int x = 0; x < IGameOfLife.SIZE; x++) {
            for (int y = 0; y < IGameOfLife.SIZE; y++) {
                gameOfLife.setDead(x, y);
                deadGrid[x][x] = IGameOfLife.DEAD;
            }
        }

        setPattern(gameOfLife);

        gameOfLife.runGenerations(AGE);

        assertArrayEquals(deadGrid, gameOfLife.getGrid());
    }

    @Test
    void noLifeAfter54CyclesCell() {
        Cell[][] deadGrid = new Cell[IGameOfLife.SIZE][IGameOfLife.SIZE];
        GameOfWildlife gameOfLife = new GameOfWildlife();

        Cell[][] golCells = gameOfLife.getCellGrid();
        for (int x = 0; x < IGameOfLife.SIZE; x++) {
            for (int y = 0; y < IGameOfLife.SIZE; y++) {
                golCells[x][y] = new Cell();
                deadGrid[x][y] = new Cell();
                deadGrid[x][y].setAge(AGE);
            }
        }

        setPattern(gameOfLife);

        gameOfLife.runGenerations(AGE);

        assertArrayEquals(deadGrid, gameOfLife.getCellGrid());
    }
}
