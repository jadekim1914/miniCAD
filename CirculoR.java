package figuras;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author jade
 */
public class CirculoR extends ModeloFigura {//Es una clase para dibujar un circulo en el lienzo

	public CirculoR() {//Constructor de la clase CirculoR
		super();
		nombre = "Circulo";
		puntos = new PuntosFigu(2);
	}

	@Override
	public void dibujar(Graphics2D g) {

		if (!canDraw()) {
			puntos.dibujar(g);
			return;
		}

		if (isSelected()) {
			g.setStroke(new BasicStroke(10));
			g.setColor(Color.RED);
		} else {
			g.setColor(background);
		}

		PuntoFig p1 = puntos.getValueAt(0);
		PuntoFig p2 = puntos.getValueAt(1);

		int distance = p1.distance(p2).intValue();

		g.drawOval(p1.x.intValue() - distance, p1.y.intValue() - distance, 2 * distance, 2 * distance);

		g.setStroke(new BasicStroke(2));
		g.setColor(background);
		g.fillOval(p1.x.intValue() - distance, p1.y.intValue() - distance, 2 * distance, 2 * distance);

		// Reset color
		g.setColor(Color.BLACK);

	}

	@Override
	public String toString() {

		String centro = "";
		String radio = "";
		ArrayList<String> valores = new ArrayList<>();

		if (puntos.getValueAt(0) != null) {//Si el punto 0 no es nulo
			centro = "Centro(" + puntos.getValueAt(0).x + ", " + puntos.getValueAt(0).y + ")";
			valores.add(centro);
		}

		if (canDraw()) {
			radio = "Radio=" + puntos.getValueAt(0).distance(puntos.getValueAt(1));
			valores.add(radio);
		}

		return "CirculoR{\n" + String.join(", ", valores) + "\n}";
	}

}
