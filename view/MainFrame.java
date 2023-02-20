package propra22.q9721070.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import propra22.q9721070.controller.*;
import propra22.q9721070.model.*;

/**
* Zweck: Die Klasse MainFrame fungiert als View im Sinne des MVC.
* Aufgabe: Hier wird die grafische Oberflaeche generiert.
* Einordnung: Gemeinsam mit dem PointPanel wird die Oberflaeche mit deren Interaktionsmoeglichkeiten generiert. 
*/
public class MainFrame extends JFrame {
	/**
	 * Default-Wert.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Neu-Item.
	 */
	public JMenuItem newDataItem = new JMenuItem("Neu");
	/**
	 * Speichern-Item.
	 */
	public JMenuItem saveItem = new JMenuItem("Speichern");
	/**
	 * Speichern unter-Item.
	 */
	public JMenuItem exportDataItem = new JMenuItem("Speichern unter");
	/**
	 * Oeffnen-Item.
	 */
	public JMenuItem importDataItem = new JMenuItem("Öffnen");
	/**
	 * Anzeige Konvexe Huelle-Item.
	 */
	public JCheckBoxMenuItem showCH = new MyMenuItem("Konvexe Hülle");
	/**
	 * Anzeige Durchmesser-Item.
	 */
	public JCheckBoxMenuItem showDia = new MyMenuItem("Durchmesser");
	/**
	 * Anzeige Groesstes Viereck-Item.
	 */
	public JCheckBoxMenuItem showQua = new MyMenuItem("Größtes Viereck");
	/**
	 * Punkte10-Item.
	 */
	public JMenuItem Points10 = new JMenuItem("10 Punkte");
	/**
	 * Punkte50-Item.
	 */
	public JMenuItem Points50 = new JMenuItem("50 Punkte");
	/**
	 * Punkte100-Item.
	 */
	public JMenuItem Points100 = new JMenuItem("100 Punkte");
	/**
	 * Punkte500-Item.
	 */
	public JMenuItem Points500 = new JMenuItem("500 Punkte");
	/**
	 * Punkte1000-Item.
	 */
	public JMenuItem Points1000 = new JMenuItem("1000 Punkte");
	/**
	 * Zurueck-Item.
	 */
	public JMenuItem goback = new JMenuItem("Zurück");
	/**
	 * Vorwaerts-Item.
	 */
	public JMenuItem goforward = new JMenuItem("Vorwärts");
	/**
	 * Neuberechnung-Item.
	 */
	public JMenuItem reload = new JMenuItem("Neuberechnung");
	/**
	 * Vergroessern-Item.
	 */
	public JMenuItem zoomin = new JMenuItem("Vergrößern");
	/**
	 * Verkleinern-Item.
	 */
	public JMenuItem zoomout = new JMenuItem("Verkleinern");
	/**
	 * Hilfe-Item.
	 */
	public JMenuItem help = new JMenuItem("Anleitung");
	/**
	 * FileChooser-Instanz.
	 */
	public JFileChooser fileChooser;
	/**
	 * Einbindung der Punktedarstellung.
	 */
	public PointPanel drawing;
	/**
	 * Speicherpfad.
	 */
	public File savepath = null;
	/**
	 * ScrollPane-Item.
	 */
	public JScrollPane scrollPane;
	/**
	* Konstruktor.
	* @param calculatorGUI Speicher- und Berechnungsmodel fuer die GUI.
	*/
	public MainFrame(CalculatorGUI calculatorGUI) {
		super("Algorithmische Geometrie");
		Controller controller = new Controller(calculatorGUI, this);
		addMouseListener(controller);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension (400, 400));
		
		drawing = new PointPanel(calculatorGUI, controller, this);
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("../ProPra-SS22-Basis/data"));

		setJMenuBar(createMenuBar(calculatorGUI));
		
        drawing.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(drawing);
        
        add(scrollPane);
        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    	showCH.setSelected(calculatorGUI.checkCH);
    	showCH.addActionListener(controller);
    	showDia.setSelected(calculatorGUI.checkDia);
    	showDia.addActionListener(controller);
    	showQua.setSelected(calculatorGUI.checkQua);
    	showQua.addActionListener(controller);
    	Points10.addActionListener(controller);
    	Points50.addActionListener(controller);
    	Points100.addActionListener(controller);
    	Points500.addActionListener(controller);
    	Points1000.addActionListener(controller);
    	goback.addActionListener(controller);
    	goforward.addActionListener(controller);
    	newDataItem.addActionListener(controller);
    	exportDataItem.addActionListener(controller);
    	saveItem.addActionListener(controller);
    	importDataItem.addActionListener(controller);
    	reload.addActionListener(controller);
    	zoomin.addActionListener(controller);
    	zoomout.addActionListener(controller);
    	help.addActionListener(controller);
	}
	/**
	* Generiert eine Menuleiste. Die Funktionalität (z.B. Speichern) kann durch die Menuleiste gesteuert werden.
	* @param calculatorGUI Speicher- und Berechnungsmodel fuer die GUI.
	* @return Menuleiste.
	*/
	private JMenuBar createMenuBar(CalculatorGUI calculatorGUI) {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("Datei");
		fileMenu.add(newDataItem);
		fileMenu.add(importDataItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.add(exportDataItem);

		JMenu showPoints = new JMenu("Bearbeiten");
		showPoints.add(showCH);
		showPoints.add(showDia);
		showPoints.add(showQua);
		showPoints.addSeparator();
		
		JMenu newPoints = new JMenu("Zusätzliche Punkte");
		newPoints.add(Points10);
		newPoints.add(Points50);
		newPoints.add(Points100);
		newPoints.add(Points500);
		newPoints.add(Points1000);
		
		JMenu view = new JMenu("Ansicht");
		view.add(goback);
		view.add(goforward);
		view.addSeparator();
		view.add(reload);
		view.addSeparator();
		view.add(zoomin);
		view.add(zoomout);
		
		JMenu information = new JMenu("Hilfe");
		information.add(help);
		
		menuBar.add(fileMenu);
		menuBar.add(showPoints);
		showPoints.add(newPoints);
		menuBar.add(view);
		menuBar.add(information);
		
		goback.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		goforward.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		zoomin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK));
		zoomout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		newDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		return menuBar;
	}
}
