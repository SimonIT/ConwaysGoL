import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
class CanvasInt extends Canvas {
	private int grid[][];

	public CanvasInt(int[][] grid) {
		this.grid = grid;
	}

	/**
	 * @param grid the grid to set
	 */
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	/**
	 * update : wird bei repaint() aufgerufen
	 */
	public void update(Graphics g) {
		// Berechne die Breite und Höhe der Felder
		Rectangle frameBounds = getBounds();
		int width = frameBounds.width - 1;
		int height = frameBounds.height - 1;
		double xSize = (double) width / grid.length;
		double ySize = (double) height / grid[0].length;
		for (int xPos = 0; xPos < grid.length; xPos++) {
			for (int yPos = 0; yPos < grid[0].length; yPos++) {
				if (grid[xPos][yPos] == 1) {
					g.setColor(Color.YELLOW);
				} else {
					g.setColor(Color.BLACK);
				}
				g.fillRect((int) (xSize * xPos), (int) (ySize * (grid[0].length - 1 - yPos)), (int) xSize + 1,
						(int) ySize + 1);
			}
		}
	}

	/**
	 * paint wird beim ersten Mal aufgerufen => initialisierung
	 */
	public void paint(Graphics g) {
		update(g);
	}
}

// --- end of GoLCanvas ----------

@SuppressWarnings("serial")
public class VisualGameOfLife extends Frame {
	private CanvasInt canvas;

	public VisualGameOfLife(int[][] grid) {
		super("Game of Life");
		// Groesse des Feldes anpassen aber mit min 300x300
		int xMax = grid.length;
		int yMax = grid[0].length;
		int xFrame = xMax * 15;
		int yFrame = yMax * 15;
		xFrame = Math.max(300, xFrame);
		yFrame = Math.max(300, yFrame);
		setSize(xFrame, yFrame);
		canvas = new CanvasInt(grid);
		add(canvas, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		setVisible(true);
	}

	/**
	 * Methode zum Neuzeichen des Gitters
	 */
	public void refresh(int[][] grid) {
	    canvas.setGrid(grid);
		canvas.repaint();
	}
}