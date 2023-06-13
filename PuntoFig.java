package figuras;

/**
 *
 * @author jade
 */
public class PuntoFig {

	public Double x;
	public Double y;

	public PuntoFig() {
		this(0d, 0d);
	}

	public PuntoFig(PuntoFig other) {
		this(other.x, other.y);
	}

	public PuntoFig(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public void setLocation(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Double distance(PuntoFig other) {

		double aux = Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2);

		return Math.sqrt(aux);
	}

	public Double distance(Double x, Double y) {
		return this.distance(new PuntoFig(x, y));
	}

}
