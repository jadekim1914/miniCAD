package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import figuras.*;

/**
 *
 * @author jade
 */
public class Pizarra extends JFrame implements Runnable {

	@Override
	public void run() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);
	}

	public Pizarra() {
		initComponents();
	}

	private void initComponents() {
		leftPanel = new JPanel();
		centerPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanelTitle = new JLabel();

		buttonGroup = new ButtonGroup();
		btnLinea = new JToggleButton();
		btnTriangulo = new JToggleButton();
		btnCirculo = new JToggleButton();
		btnCuadrado = new JToggleButton();


		canvasPanel = new CanvasPanel();
		scrollLogger = new JScrollPane();
		logger = new JTextArea();
		texto = new JTextArea();
		

		rightPanelTitle = new JLabel();
		scrollFiguras = new JScrollPane();
		listFiguras = new JList<>(new ListFiguras());

		setTitle("MiniCAD_Jade");
		setResizable(false);
		setLayout(new BorderLayout());

		// Si la ventana pierde el foco del teclado, recuperarlo
		KeyboardFocusManager.
						getCurrentKeyboardFocusManager().
						addPropertyChangeListener("focusOwner", (PropertyChangeEvent e) -> {
							//System.out.println(e.toString());
							requestFocusInWindow();
						});

		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {

				ModeloFigura selectedModel = listFiguras.getSelectedValue();

				// Quitar la seleccion de la figura
				if (e.getExtendedKeyCode() == VK_ESCAPE && selectedModel != null) {
					logger.append(selectedModel.getNombre() + " deseleccionado.");
					listFiguras.clearSelection();
					return;
				}

				if (selectedModel == null || !selectedModel.canDraw()) {
					return;
				}

				switch (e.getExtendedKeyCode()) {
					//boton de direccion arriba
					case VK_UP -> {// Up
						
						selectedModel.getPuntos().up(5d);
						logger.append("Arriba " + selectedModel.getNombre() + "\n");
					}
					case VK_DOWN -> {// Down
						selectedModel.getPuntos().down(5d);
						logger.append("Abajo " + selectedModel.getNombre() + "\n");
					}
					case VK_LEFT -> {// Left
						selectedModel.getPuntos().left(5d);
						logger.append("Izquierda " + selectedModel.getNombre() + "\n");
					}
					case VK_RIGHT -> {// Right
						selectedModel.getPuntos().right(5d);
						logger.append("Derecha " + selectedModel.getNombre() + "\n");
					}
					case VK_Q -> {// Rotate left
						selectedModel.getPuntos().rotateLeft(90);
						logger.append("gira izq " + selectedModel.getNombre() + "\n");
					}
					case VK_E -> {// Rotate right
						selectedModel.getPuntos().rotateRight(90);
						logger.append("gira der " + selectedModel.getNombre() + "\n");
					}
					case VK_SHIFT -> { // Zoom in
						selectedModel.getPuntos().zoomIn(2d);
						logger.append("Zoom Aumenta " + selectedModel.getNombre() + "\n");
					}
					case VK_CONTROL -> {// Zoom out
						selectedModel.getPuntos().zoomOut(2d);
						logger.append("Zoom Aleja " + selectedModel.getNombre() + "\n");
					}

					case VK_DELETE -> {// el boton borrar elimina la figura seleccionada de la lista junto con sus puntos
						logger.append(selectedModel.getNombre() + " eliminado.\n");
						listModel.removeElement(selectedModel);
						listFiguras.clearSelection();
						//si la figura es la ultima de la lista, se borra el log
						if (listModel.isEmpty()) {
							logger.setText("");
						}
					}

					case VK_SPACE -> {// el boton espacio cambia el color de la figura seleccionada
						selectedModel.setBackground(new Color((int) (Math.random() * 0x1000000)));
						logger.append(selectedModel.getNombre() + " color cambiado.\n");
					}

					case VK_ENTER -> {// borra el log
						logger.setText("");
					}
				}

				repaint();
			}

		});

		/* Panel izquierdo */
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		leftPanelTitle.setText("Figuras");
		leftPanelTitle.setFont(new Font("Arial", Font.BOLD, 24));
		leftPanelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		//color de panel izquierdo en verde
		leftPanel.setBackground(Color.PINK);

		btnLinea.setText("Línea");
		btnLinea.setFont(new Font("Arial", Font.BOLD, 16));
		btnLinea.setAlignmentX(Component.CENTER_ALIGNMENT);
		//tamaño de boton adaptable al panel izquierdo
		btnLinea.setMaximumSize(new Dimension(200, 50));
		btnLinea.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Se va a crear una línea.\n");

			btnLinea.setEnabled(false);
			btnCirculo.setEnabled(true);
			btnCuadrado.setEnabled(true);
			btnTriangulo.setEnabled(true);

			currentFigura = new Linea();
		});


		btnTriangulo.setText("Triángulo");
		btnTriangulo.setFont(new Font("Arial", Font.BOLD, 16));
		btnTriangulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		//tamaño de boton adaptable al panel izquierdo
		btnTriangulo.setMaximumSize(new Dimension(200, 50));
		btnTriangulo.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Se va a crear un triángulo.\n");

			btnTriangulo.setEnabled(false);
			btnLinea.setEnabled(true);
			btnCirculo.setEnabled(true);
			btnCuadrado.setEnabled(true);
			

			currentFigura = new TrianguloR();
		});

		btnCirculo.setText("Circulo");
		btnCirculo.setFont(new Font("Arial", Font.BOLD, 16));
		btnCirculo.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCirculo.setMaximumSize(new Dimension(200, 50));
		btnCirculo.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Se va a crear un circulo.\n");

			btnCirculo.setEnabled(false);
			btnTriangulo.setEnabled(true);
			btnLinea.setEnabled(true);

			currentFigura = new CirculoR();
		});

		btnCuadrado.setText("Cuadrado");
		btnCuadrado.setFont(new Font("Arial", Font.BOLD, 16));
		btnCuadrado.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCuadrado.setMaximumSize(new Dimension(200, 50));
		btnCuadrado.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Se va a crear un cuadrado.\n");

			btnCuadrado.setEnabled(false);
			btnTriangulo.setEnabled(true);
			btnCirculo.setEnabled(true);

			currentFigura = new CuadradoR();
		});

		buttonGroup.add(btnTriangulo);
		buttonGroup.add(btnCirculo);
		buttonGroup.add(btnCuadrado);
		buttonGroup.add(btnLinea);


		leftPanel.add(leftPanelTitle);
		leftPanel.add(btnLinea);
		leftPanel.add(btnTriangulo);
		leftPanel.add(btnCirculo);
		leftPanel.add(btnCuadrado);


		/* Diseño del panel central */
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.lightGray);

		canvasPanel.setBackground(centerPanel.getBackground());
		canvasPanel.setBorder(new LineBorder(Color.BLACK, 2));
		canvasPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (currentFigura == null) {
					return;
				}

				for (int i = 0; i < currentFigura.getPuntos().size(); i++) {

					if (currentFigura.getPuntos().getValueAt(i) == null) {

						currentFigura.getPuntos().setValueAt(i, e.getPoint());
						logger.append("P" + (i + 1) + " (" + e.getX() + ", " + e.getY() + ")\n");

						listModel.addElement(currentFigura);
						canvasPanel.setModel(listModel);
						repaint();
						break;
					}
				}

				if (currentFigura.canDraw()) {//si ya se puede dibujar
					try {
						logger.append(currentFigura.getNombre() + " creado.\n");
						currentFigura = (ModeloFigura) currentFigura.getClass().getConstructors()[0].newInstance(new Object[0]);
					} catch (Exception ex) {

					}
				}

			}

		});

		logger.setRows(10);//filas
		logger.setLineWrap(true);
		logger.setEditable(false);

		scrollLogger.setViewportView(logger);

		centerPanel.add(canvasPanel, BorderLayout.CENTER);
		leftPanel.add(scrollLogger, BorderLayout.PAGE_END);

		/*panel derecho colores y diseño */
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		rightPanelTitle.setText("Panel de selección");
		rightPanelTitle.setFont(new Font("Arial", Font.BOLD, 24));
		rightPanelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		//panel derecho en color verde
		rightPanel.setBackground(Color.PINK);

		listModel = (ListFiguras) listFiguras.getModel();
		listFiguras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		listFiguras.addListSelectionListener((ListSelectionEvent e) -> {

			listModel.unselectAll();

			var selectedModel = listFiguras.getSelectedValue();

			if (selectedModel == null) {
				repaint();
				return;
			}

			selectedModel.setSelected(true);
			logger.append(selectedModel.getNombre() + " seleccionado.\n");
			repaint();
		});

		//Pondremos un texto en el panel derecho para que se vea
		texto.setText("Manipulación de figuras:\n"
		+ "← = izquierda | → =  derecha\n"
		+ "-------------------------------\n"
		+ "↑ =  arriba | ↓ =  abajo\n"
		+ "-------------------------------\n"
		+ "Supr = borrar figura\n"
		+ "-------------------------------\n"
		+ "Shift = agrandar figura\n"
		+ "-------------------------------\n"
		+ "Ctrl = reducir figura\n"
		+ "-------------------------------\n"
		+ "Espacio = cambiar color\n"
		+ "-------------------------------\n"
		+ "Enter = borrar registro\n"
		+ "-------------------------------\n"
		+ "Esc = Deselecciona figura\n"
		+ "-------------------------------\n"
		+ "q = rotar izquierda\n"
		+ "-------------------------------\n"
		+ "e = rotar derecha\n");
		texto.setFont(new Font("Arial", Font.BOLD, 20));
		texto.setAlignmentX(Component.CENTER_ALIGNMENT);
		//panel adaptable al tamaño del texto
		texto.setMaximumSize(new Dimension(500, 500));

		//color de texto y fondo
		texto.setForeground(Color.BLACK);
		texto.setBackground(Color.PINK);



		//canvasPanel.setModel(listModel);
		scrollFiguras.setViewportView(listFiguras);

		rightPanel.add(rightPanelTitle);
		rightPanel.add(scrollFiguras);
		rightPanel.add(texto);

		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

	}

	/* Variables declaration */
	private ModeloFigura currentFigura;

	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JLabel leftPanelTitle;

	/* leftPanel components */
	private ButtonGroup buttonGroup;
	private JToggleButton btnLinea;
	private JToggleButton btnTriangulo;
	private JToggleButton btnCirculo;
	private JToggleButton btnCuadrado;


	/* centerPanel components */
	private CanvasPanel canvasPanel;
	private JScrollPane scrollLogger;
	private JTextArea logger;
	private JTextArea texto;

	/* rightPanel components */
	private JLabel rightPanelTitle;
	private JScrollPane scrollFiguras;
	private JList<ModeloFigura> listFiguras;
	private ListFiguras listModel;
}
