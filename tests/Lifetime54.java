import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * "Testfall 2 - Muster mit begrenzter Lebensdauer"
 */
public class Lifetime54 {
    // the amount of simulated steps in this test is TIME_STEP
    static final int TIME_STEPS = 54;

    /**
     * sets the following pattern which should be dead after 54 cycles:
     * <pre>
     *      ###
     *      # #
     *      # #
     *
     *      # #
     *      # #
     *      ###
     *</pre>
     * @param gameOfLife the gameOfLife object
     */
    private static void setStartPattern(IGameOfLife gameOfLife) {
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

    /**
     * test for int array
     */
    @Test
    void noLifeAfter54CyclesInt() {
        int[][] deadGrid = new int[IGameOfLife.SIZE][IGameOfLife.SIZE];
        GameOfLife gameOfLife = new GameOfLife();

        setStartPattern(gameOfLife);
        gameOfLife.runGenerations(TIME_STEPS);
        assertArrayEquals(deadGrid, gameOfLife.getGrid());
    }

    /**
     * test for Cell array
     */
    @Test
    void noLifeAfter54CyclesCell() {
        Cell[][] deadGrid = new Cell[IGameOfLife.SIZE][IGameOfLife.SIZE];
        GameOfWildlife gameOfLife = new GameOfWildlife();

        Cell[][] golCells = gameOfLife.getCellGrid();
        for (int x = 0; x < gameOfLife.SIZE; ++x) {
            for (int y = 0; y < gameOfLife.SIZE; ++y) {
                golCells[x][y] = new Cell();
                deadGrid[x][y] = new Cell();
                deadGrid[x][y].setAge(TIME_STEPS);
            }
        }

        setStartPattern(gameOfLife);
        gameOfLife.runGenerations(TIME_STEPS);
        assertArrayEquals(deadGrid, gameOfLife.getCellGrid());
    }
}
