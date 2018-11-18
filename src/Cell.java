import java.awt.Color;
import java.util.Objects;
import java.util.Random;

// objects of class Cell are needed in GameOfWildlife to store additional information such as the color of a cell
public class Cell {
    private int age;
    private Color color;
    private boolean alive;

    /**
     * creates a dead Cell; color is black since it shouldn't be visible on grid
     */
    Cell() {
        this.age = 0;
        this.color = Color.BLACK;
        this.alive = false;
    }

    /**
     * creates a newly born cell with a specific color
     *
     * @param color: Color
     * @param alive: boolean
     */
    Cell(Color color, boolean alive) {
        this.age = 0;
        this.color = color;
        this.alive = alive;
    }

    /**
     * the copy constructor simply copies a Cell object
     *
     * @param cell: Cell
     */
    Cell(Cell cell) {
        this.age = cell.age;
        this.color = cell.color;
        this.alive = cell.alive;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        // if not alive set color black
        if (!this.alive) {
            this.setColor(Color.BLACK);
        }
    }

    /**
     * overwriting "equals" is necessary for performing test cases
     *
     * @param obj: object that is compared to this
     * @return if the two objects in question are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        // check if obejcts are of same class
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }
        Cell that = (Cell) obj;
        // cell objects should have the same age, the same color and should both be alive to be seen equal
        return this.age == that.getAge() && this.color.equals(that.getColor()) && this.alive == that.getAlive();
    }

    /**
     * overwriting "hashCode" is necessary for performing test cases
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.age, this.color, this.alive);
    }

    /**
     * for neat representation on screen, overwrite "toString" method
     *
     * @return string representation of Cell
     */
    @Override
    public String toString() {
        return String.format("Cell [age=%s] [%s] [alive=%s]", this.age, this.color, this.alive);
    }

    /**
     * @return an alive Cell object with random color
     */
    public static Cell createWithRandomColor() {
        Random random = new Random();
        return new Cell(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)), true);
    }
}
