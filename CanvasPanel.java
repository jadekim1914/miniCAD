package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import figuras.ListFiguras;

/**
 *
 * @author jade
 */
public class CanvasPanel extends JPanel {

	private ListFiguras figurasModel;

	public CanvasPanel() {
		this(null);
	}

	public CanvasPanel(ListFiguras figurasModel) {
		this.figurasModel = figurasModel;
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}

	public ListFiguras getModel() {
		return this.figurasModel;
	}

	public void setModel(ListFiguras model) {
		this.figurasModel = model;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (figurasModel == null) {
			return;
		}

		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

		for (var it : figurasModel.sort()) {
			it.dibujar(g2);
		}

	}
}
