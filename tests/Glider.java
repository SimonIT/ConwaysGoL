import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * Testfall 1 - Glider
 */
public class Glider {
    // the amount of simulated steps in this test is TIME_STEP
    static final int TIME_STEPS = 4;

    /**
     * Sets a glider
     * https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens#/media/File:Glider_(Conways_Spiel_des_Lebens).gif
     *
     * @param gameOfLife the gameOfLife object
     */
    private static void setStartPattern(IGameOfLife gameOfLife) {
        int halfSize = gameOfLife.SIZE / 2;
        gameOfLife.setAlive(halfSize + 1, halfSize);
        gameOfLife.setAlive(halfSize + 2, halfSize + 1);
        gameOfLife.setAlive(halfSize, halfSize + 2);
        gameOfLife.setAlive(halfSize + 1, halfSize + 2);
        gameOfLife.setAlive(halfSize + 2, halfSize + 2);
    }

    /**
     * Sets the glider after 4 generations
     *
     * @param gameOfLife the gameOfLife object
     */
    private static void setEndPattern(IGameOfLife gameOfLife) {
        int halfSize = gameOfLife.SIZE / 2;
        gameOfLife.setAlive(halfSize + 2, halfSize + 1);
        gameOfLife.setAlive(halfSize + 3, halfSize + 2);
        gameOfLife.setAlive(halfSize + 1, halfSize + 3);
        gameOfLife.setAlive(halfSize + 2, halfSize + 3);
        gameOfLife.setAlive(halfSize + 3, halfSize + 3);
    }

    /**
     * test for int array
     */
    @Test
    void gliderInt() {
        GameOfLife gameOfEndLife = new GameOfLife();
        setEndPattern(gameOfEndLife);
        GameOfLife gameOfLife = new GameOfLife();
        setStartPattern(gameOfLife);

        gameOfLife.runGenerations(TIME_STEPS);
        assertArrayEquals(gameOfLife.getGrid(), gameOfEndLife.getGrid());
    }

    /**
     * test for Cell array
     */
    @Test
    void gliderCell() {
        GameOfWildlife gameOfEndLife = new GameOfWildlife();
        GameOfWildlife gameOfLife = new GameOfWildlife();

        Cell[][] golEndCells = gameOfEndLife.getCellGrid();
        Cell[][] golCells = gameOfLife.getCellGrid();
        for (int x = 0; x < gameOfLife.SIZE; ++x) {
            for (int y = 0; y < gameOfLife.SIZE; ++y) {
                golCells[x][y] = new Cell();
                golEndCells[x][y] = new Cell();
                golEndCells[x][y].setAge(TIME_STEPS);
            }
        }

        setEndPattern(gameOfEndLife);
        setStartPattern(gameOfLife);
        gameOfLife.runGenerations(TIME_STEPS);

        Cell[][] golCellsAfterGeneration = gameOfLife.getCellGrid();
        for (int x = 0; x < gameOfLife.SIZE; ++x) {
            for (int y = 0; y < gameOfLife.SIZE; ++y) {
                golCellsAfterGeneration[x][y].setColor(Color.BLACK);
            }
        }

        assertArrayEquals(gameOfEndLife.getCellGrid(), gameOfLife.getCellGrid());
    }
}
