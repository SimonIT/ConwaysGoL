import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testfall 1 - Glider
 */
public class Glider {

    /**
     * Sets a glider
     * https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens#/media/File:Glider_(Conways_Spiel_des_Lebens).gif
     *
     * @param gameOfLife the gameOfLife object
     */
    private static void setStartPattern(IGameOfLife gameOfLife) {
        int halfSize = gameOfLife.SIZE / 2;
        gameOfLife.setAlive(halfSize, halfSize);
        gameOfLife.setAlive(halfSize + 1, halfSize);
        gameOfLife.setAlive(halfSize + 2, halfSize);
        gameOfLife.setAlive(halfSize + 2, halfSize + 1);
        gameOfLife.setAlive(halfSize + 2, halfSize + 2);
    }

    @Test
    void gliderInt() {

    }

    @Test
    void gliderCell() {

    }
}
