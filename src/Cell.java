import java.awt.Color;
import java.util.Random;

public class Cell {
    private int age;
    private Color color;
    private boolean alive;

    Cell() {
        this.age = 0;
        this.color = Color.BLACK;
        this.alive = false;
    }

    Cell(Color color, boolean alive) {
        this.age = 0;
        this.color = color;
        this.alive = alive;
    }

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
    }

    public static Cell createWithRandomColor() {
        Random random = new Random();
        return new Cell(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)), true);
    }
}
