package figuras;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author jade
 */
public abstract class ModeloFigura implements Iterable<PuntoFig>, IDrawable {

	private int id;//Id de la figura
	protected String nombre;
	protected PuntosFigu puntos;
	protected Color background;
	protected boolean selected;

	private static final ArrayList<Integer> ids = new ArrayList<>();

	protected ModeloFigura() {
		id = generateId();
		nombre = "Figuras";
		background = Color.WHITE;
		selected = false;
		
	}

	private static int generateId() {//Genera un id aleatorio para cada figura
		Random r = new Random();
		int id = r.nextInt(1000);

		while (ids.contains(id)) {
			id = r.nextInt(1000);
		}

		ids.add(id);

		return id;
	}

	public int getId() {//Retorna el id de la figura
		return id;
	}

	public Color getBackground() {//Retorna el color de fondo de la figura
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public PuntosFigu getPuntos() {
		return puntos;
	}
	
	public String getNombre(){
		return nombre;
	}

	public Iterator<PuntoFig> iterator() {
		return puntos.iterator();
	}

	public boolean canDraw() {
		return puntos.isFull();
	}

	@Override
	public String toString() {

		ArrayList<String> puntosStr = new ArrayList<>();

		for (int i = 0; i < this.puntos.size(); i++) {

			if (this.puntos.getValueAt(i) == null) {
				continue;
			}

			puntosStr.add("\tP" + (i + 1) + "(" + this.puntos.getValueAt(i).x.intValue() + ", " + this.puntos.getValueAt(i).y.intValue() + ")");
			
		}

		return nombre + "{\n" + String.join(", \n", puntosStr) + "\n}";
	}

}
