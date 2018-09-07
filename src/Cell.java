import org.fusesource.jansi.Ansi;

public class Cell {
    private int age;
    private Ansi.Color color;
    private boolean alive;

    Cell(Ansi.Color color, boolean alive) {
        this.age = 0;
        this.color = color;
        this.alive = alive;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Ansi.Color getColor() {
        return color;
    }

    public void setColor(Ansi.Color color) {
        this.color = color;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
