package propra22.q9721070.controller;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import propra22.q9721070.model.*;
import propra22.q9721070.view.*;

/**
* Zweck: Die Klasse Controller fungiert als Controller im Sinne des MVC.
* Aufgabe: Saemtliche Aenderungen des Models finden ueber den Controller statt. 
* Einordnung: Die Klasse reduziert die Kopplung zwischen View und Model.
*/
public class Controller extends MouseAdapter implements MouseMotionListener, ActionListener {
	/**
	 * Dateipfad zur Abspeicherung der Punktemenge.
	 */
	private File savepath = null;
	/**
	 * X-Koordinate.
	 */
	private int x;
	/**
	 * Y-Koordinate.
	 */
	private int y;
	/**
	 * Distanz zum naechsten Punkt. Dadurch wird ermoeglicht Punkte zu veraendern, ohne sie exakt zu treffen.
	 */
	private int closest;
	/**
	 * Punkt mit niedrigster Distanz zur aktuellen Mausposition. Dadurch wird ermoeglicht Punkte zu veraendern, ohne sie exakt zu treffen.
	 */
	private Point closestP;
	/**
	 * Instanz des Models.
	 */
	private CalculatorGUI calculatorGUI;
	/**
	 * Instanz der GUI.
	 */
	private MainFrame mainFrame;
	/**
	 * Konstruktor.
	 * @param calculatorGUI Speicher- und Berechnungsmodel fuer die GUI.
	 * @param mainFrame GUI
	 */
	public Controller(CalculatorGUI calculatorGUI, MainFrame mainFrame){
		this.calculatorGUI = calculatorGUI;
		this.mainFrame = mainFrame;
	}
	
	// Listener-Methoden
	/**
	 * Reaktion auf die Bewegung der Maus.
	 * @param e Mausaktion findet statt.
	 */
   	public void mouseMoved(MouseEvent e){
   		showCoordinates(e);
   	}
	/**
	 * Reaktion auf Drag and Drop.
	 * @param e Mausaktion findet statt.
	 */
   	public void mouseDragged(MouseEvent e){
   		movePoint(e);
    }
	/**
	 * Reaktion auf einen Klick.
	 * @param e Mausaktion findet statt.
	 */
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
        	addPoint(e);
        }  
        if (SwingUtilities.isRightMouseButton(e)) {
        	deletePoint(e);
        }
    }
	/**
	 * Reaktion auf das Loslassen der Maus. Wird fuer die Erstellung eines neuen Zustands fuer Undo/Redo benoetigt.
	 * @param e Mausaktion findet statt.
	 */
    public void mouseReleased(MouseEvent e) {
    	if (SwingUtilities.isLeftMouseButton(e)) {
    		calculatorGUI.addState();
    	}
    }
	/**
	 * Reaktion auf das Klicken auf bestimmte Komponenten, z.B. "Neu" oder "Zusaetzliche Punkte".
	 * @param e Aktion in Bezug auf eine Komponente findet statt.
	 */    
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainFrame.newDataItem) {
			clearPoints();
		}
		if (e.getSource() == mainFrame.Points10) {
			generateRandomPoints10();
		}
		if (e.getSource() == mainFrame.Points50) {
			generateRandomPoints50();
		}
		if (e.getSource() == mainFrame.Points100) {
			generateRandomPoints100();
		}
		if (e.getSource() == mainFrame.Points500) {
			generateRandomPoints500();
		}
		if (e.getSource() == mainFrame.Points1000) {
			generateRandomPoints1000();
		}
		if (e.getSource() == mainFrame.showCH) {
			showhideCH();
		}
		if (e.getSource() == mainFrame.showDia) {
			showhideDia();
		}
		if (e.getSource() == mainFrame.showQua) {
			showhideQua();
		}
		if (e.getSource() == mainFrame.goback) {
			setlastState();
		}
		if (e.getSource() == mainFrame.goforward) {
			setnextState();
		}
		if (e.getSource() == mainFrame.reload) {
			reload();
		}
		if (e.getSource() == mainFrame.zoomin) {
			zoomin();
		}
		if (e.getSource() == mainFrame.zoomout) {
			zoomout();
		}
		if (e.getSource() == mainFrame.importDataItem) {
			importdata();
		}
		if (e.getSource() == mainFrame.exportDataItem) {
			saveunderpath();
		}
		if (e.getSource() == mainFrame.saveItem) {
			save();
		}
		if (e.getSource() == mainFrame.help) {
			help(); 
		}
		mainFrame.repaint();
	}

	// UseCases
	/**
	 * Fuegt einen Punkt durch Links-Klick hinzu. Ermoeglichung der Vergrosserung der Punktemenge.
	 * @param e Mausaktion findet statt und die entsprechenden Koordinaten werden verwendet.
	 */
	public void addPoint(MouseEvent e) {
    	calculatorGUI.add(new Point(((int)(remodX(e.getX())/calculatorGUI.zoom)), ((int)(remodY(e.getY())/calculatorGUI.zoom))));
    	sizeAdaption();
		calculatorGUI.reload();
		mainFrame.repaint();
    }
	/**
	 * Entfernt einen Punkt durch Rechts-Klick. Reduktion der Punktmenge. Es wird der naechste Punkt ermittelt, damit er nicht genau getroffen werden muss. 
	 * @param e Mausaktion findet statt und die entsprechenden Koordinaten werden verwendet, um den naechsten Punkt zu entfernen.
	 */
    public void deletePoint(MouseEvent e) {
    	setX(remodX(e.getX()));
    	setY(remodY(e.getY()));
    	closest = (int) (300 * calculatorGUI.zoom); // Mindestabstand
    	closestP = null;
    	for (Point p: calculatorGUI.data()) {
    		int distance = (int) ((p.getX()*calculatorGUI.zoom-x)*(p.getX()*calculatorGUI.zoom-x) + (p.getY()*calculatorGUI.zoom-y)*(p.getY()*calculatorGUI.zoom-y));
    		if (distance < closest) {
    			closestP = p;
    			closest = distance;
    		}
    	}
    	calculatorGUI.remove(closestP);
    	sizeAdaption();
		calculatorGUI.reload();
		mainFrame.repaint();
    }
	/**
	 * Vergroessert den aktuellen Bildschirmausschnitt (grundsaetzlich Fokus Mitte des Bildes) und vergroessert die Punkte (Zoomin-Funktionalitaet).
	 */
    public void zoomin() {
		if (calculatorGUI.zoom < 100) { // Maximaler Zoom 
			sizeAdaption();
			
			int middlex = (calculatorGUI.maxx + calculatorGUI.minx)/2;
			int middley = (calculatorGUI.maxy + calculatorGUI.miny)/2;
			int dismiddlex = middlex - calculatorGUI.minx;
			int dismiddley = middley - calculatorGUI.miny;
			
			calculatorGUI.zoom = calculatorGUI.zoom * 1.5f;
			sizeAdaption();
			
			dismiddlex /= 1.5f;
			dismiddley /= 1.5f;
			
			int x = (int) ((middlex - dismiddlex)*calculatorGUI.zoom);
			int y = (int) ((middley - dismiddley)*calculatorGUI.zoom);
			
			mainFrame.scrollPane.getViewport().setViewPosition(new java.awt.Point(x,y));
			
		}
    }
	/**
	 * Verkleinert den Bildschirmausschnitt und macht mehr Punkte sichtbar (Zoomout-Funktionalitaet).
	 */
    public void zoomout() {
		if (calculatorGUI.zoom > 0.001) { // Minimaler Zoom
			sizeAdaption();
			
			int middlex = (calculatorGUI.maxx + calculatorGUI.minx)/2;
			int middley = (calculatorGUI.maxy + calculatorGUI.miny)/2;
			int dismiddlex = middlex - calculatorGUI.minx;
			int dismiddley = middley - calculatorGUI.miny;
			
			calculatorGUI.zoom = calculatorGUI.zoom / 1.5f;
			sizeAdaption();
			
			dismiddlex *= 1.5f;
			dismiddley *= 1.5f;
			
			int x = (int) ((middlex - dismiddlex)*calculatorGUI.zoom);
			int y = (int) ((middley - dismiddley)*calculatorGUI.zoom);
			
			mainFrame.scrollPane.getViewport().setViewPosition(new java.awt.Point(x,y));
		}	
    }
	/**
	 * Zeigt die aktuellen Punkt-Koordinaten, wenn mit der Maus auf einem Punkt verblieben wird.
	 * @param e MouseEvent findet statt und die entsprechenden Koordinaten werden verwendet.
	 */
	public void showCoordinates(MouseEvent e) {
		setX(remodX(e.getX()));
   		setY(remodY(e.getY()));
   		mainFrame.drawing.setToolTipText("X-Koordinate " + Integer.toString((int)(getX()/calculatorGUI.zoom)) + " Y-Koordinate " + Integer.toString((int)(getY()/calculatorGUI.zoom)));
	}
	/**
	 * Verschiebt die Punkt-Koordinaten bei Drag and Drop. Es wird der naechste Punkt ermittelt, damit er nicht genau getroffen werden muss.
	 * @param e Mausaktion findet statt und die entsprechenden Koordinaten werden verwendet.
	 */
    public void movePoint(MouseEvent e) {
		setX(remodX(e.getX()));
		setY(remodY(e.getY()));
		closest = (int)(300* calculatorGUI.zoom); // Maximaler Abstand
    	closestP = null;
    	for (Point p: calculatorGUI.data()){
    		int distance = (int) ((p.getX()*calculatorGUI.zoom-getX())*(p.getX()*calculatorGUI.zoom-getX()) + (p.getY()*calculatorGUI.zoom-getY())*(p.getY()*calculatorGUI.zoom -getY()));
    		if (distance < closest) {
    			closestP = p;
    			closest = distance;
    		}
    	}
    	try {
        	closestP.setXY((int)(getX()/calculatorGUI.zoom), (int)(getY()/calculatorGUI.zoom));
        	mainFrame.repaint();
        	calculatorGUI.PointChange();
    	} catch (Exception ZuWeitEntfernt) {
    	}
    	sizeAdaption();
    }
	/**
	 * Generierung von 10 zufaelligen Punkten im sichtbaren Bereich.
	 */
    public void generateRandomPoints10() {
		calculatorGUI.generateRandomPoints(10);
		mainFrame.repaint();
    }
	/**
	 * Generierung von 50 zufaelligen Punkten im sichtbaren Bereich.
	 */
    public void generateRandomPoints50() {
		calculatorGUI.generateRandomPoints(50);
		mainFrame.repaint();
    }
	/**
	 * Generierung von 100 zufaelligen Punkten im sichtbaren Bereich.
	 */
    public void generateRandomPoints100() {
		calculatorGUI.generateRandomPoints(100);
		mainFrame.repaint();
    }
	/**
	 * Generierung von 500 zufaelligen Punkten im sichtbaren Bereich.
	 */
    public void generateRandomPoints500() {
		calculatorGUI.generateRandomPoints(500);
		mainFrame.repaint();
    }
	/**
	 * Generierung von 1000 zufaelligen Punkten im sichtbaren Bereich.
	 */
    public void generateRandomPoints1000() {
		calculatorGUI.generateRandomPoints(1000);
		mainFrame.repaint();
    }
	/**
	 * Macht die Konvexe Huelle sichtbar bzw. unsichtbar.
	 */
   	public void showhideCH() {
		calculatorGUI.checkCHCheckedChanger();
		mainFrame.repaint();
   	}
	/**
	 * Macht den Durchmesser sichtbar bzw. unsichtbar.
	 */
   	public void showhideDia() {
		calculatorGUI.checkDiaCheckedChanger();
		mainFrame.repaint();
   	}
	/**
	 * Macht das groesste Quadrat sichtbar bzw. unsichtbar.
	 */
   	public void showhideQua() {
		calculatorGUI.checkQuaCheckedChanger();
		mainFrame.repaint();
   	}
	/**
	 * Macht den letzten Zustand sichtbar (Undo-Funktionalitaet).
	 */
   	public void setlastState() {
		calculatorGUI.setlastState();
		sizeAdaption();
		mainFrame.repaint();
   	}
	/**
	 * Macht den naechsten Zustand sichtbar (Redo-Funktionalitaet).
	 */
   	public void setnextState() {
		calculatorGUI.setnextState();
		sizeAdaption();
		mainFrame.repaint();
   	}
	/**
	 * Fuehrt eine Neuberechnung der geometrischen Formen durch, wenn der Punktezustand veraendert wird.
	 */
   	public void reload() {
		calculatorGUI.reload();
		mainFrame.repaint();
   	}
	/**
	 * Loescht alle Punkte.
	 */
   	public void clearPoints() {
   		calculatorGUI.clearPoints();
   		sizeAdaption();
   		mainFrame.repaint();
   	}
	/**
	 * Import der Punkte einer Datei.
	 */
   	public void importdata() {
		if (mainFrame.fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
			try {
				calculatorGUI.importData(mainFrame.fileChooser.getSelectedFile());
				sizeAdaption();
				mainFrame.repaint();
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
   	}
	/**
	 * Speichert die Punkte unter einem zu bestimmenden Pfad. Pfad muss noch ausgewaehlt werden.
	 */
   	public void saveunderpath() {
		if (mainFrame.fileChooser.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
			savepath = mainFrame.fileChooser.getSelectedFile();
			try {
				calculatorGUI.exportData(savepath);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
   	}
	/**
	 * Speichert die Punkte unter einem bestimmten Pfad. Der Pfad ist bereits festgelegt (z.B. durch bereits vorgenommenen "Speichern unter").
	 */
   	public void save() {
		if (savepath != null) {
			try {
				calculatorGUI.exportData(savepath);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else  {
			saveunderpath();
		}
   	}
	/**
	 * Oeffnet die Bedienungsanleitung. 
	 */
   	public void help() {
		if (Desktop.isDesktopSupported()) {
			File myFile = new File("..\\CHGO_9721070_Blank_Matthias\\src\\propra22\\q9721070\\model\\Bedienungsanleitung.pdf");
	    	try {
				Desktop.getDesktop().open(myFile);
			} catch (IOException e1) {
				System.out.println("Oeffnen des Dokuments Bedienungsanleitung nicht moeglich");
				e1.printStackTrace();
			}
	    }
   	}

   	// Hilfsfunktionen
	/**
 	 * Veraenderungen der Bildschirmgroesse werden an das Model weitergegegeben. Dies ist fuer die Berechnung der geometrischen Formen im sichtbaren Bereich erforderlich. Damit werden auch die zufaelligen Punkte nur im sichtbaren Bereich eingefuegt.   
	 */
	public void sizeAdaption() {
		calculatorGUI.maxx = (int) ((int) (mainFrame.getContentPane().getSize().getWidth())/calculatorGUI.zoom + mainFrame.scrollPane.getViewport().getViewPosition().getX()/calculatorGUI.zoom - calculatorGUI.minxdata);
		calculatorGUI.maxy = (int) ((int) (mainFrame.getContentPane().getSize().getHeight())/calculatorGUI.zoom + mainFrame.scrollPane.getViewport().getViewPosition().getY()/calculatorGUI.zoom - calculatorGUI.minydata);
		calculatorGUI.minx = (int) ((int) (modX((int) mainFrame.scrollPane.getViewport().getViewPosition().getX()) + calculatorGUI.minxdata)/calculatorGUI.zoom);
		calculatorGUI.miny = (int) ((int) (modY((int) mainFrame.scrollPane.getViewport().getViewPosition().getY()) + calculatorGUI.minydata)/calculatorGUI.zoom);

    	int height=(int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()-200); // 
    	int width =(int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()-200); // 

		if (calculatorGUI.getHighestY() > height) {
			height = (int) (calculatorGUI.getHighestY());
		}
		if (calculatorGUI.getHighestX() > width) {
			width = (int) (calculatorGUI.getHighestX());
		}
		mainFrame.drawing.setPreferredSize(new Dimension((int)(width*calculatorGUI.zoom), (int)(height*calculatorGUI.zoom)));
		SwingUtilities.updateComponentTreeUI(mainFrame);
	}
	/**
 	 * Setter fuer X-Koordinate.
 	 * @param x X-Koordinate.    
	 */
   	public void setX(int x) {
		this.x = x;
	}
	/**
 	 * Getter fuer X-Koordinate.  
 	 * @return X-Koordinate. 
	 */
	public int getX() {
		return x;
	}
	/**
 	 * Getter fuer Y-Koordinate.
 	 * @return Y-Koordinate.  
	 */
	public int getY() {
		return y;
	}
	/**
 	 * Setter fuer Y-Koordinate.
 	 * @param y Y-Koordinate.    
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	* Modifiziert die X-Koordinate, damit auch negative Punkte dargestellt werden koennen.
	* @param x X-Koordinate
	* @return Angepasste X-Koordinate.
	*/
	public int modX(int x) {
		return (int) (x - calculatorGUI.minxdata * calculatorGUI.zoom);
	}
	/**
	* Modifiziert die Y-Koordinate, damit auch negative Punkte dargestellt werden koennen.
	* @param y Y-Koordinate
	* @return Angepasste Y-Koordinate.
	*/
	public int modY(int y) {
		return (int) (y - calculatorGUI.minydata * calculatorGUI.zoom);
	}
	/**
	* Modifiziert die X-Koordinate, damit auch negative Punkte dargestellt werden koennen.
	* @param x X-Koordinate
	* @return Angepasste X-Koordinate.
	*/
	public int remodX(int x) {
		return (int) (x + calculatorGUI.minxdata * calculatorGUI.zoom);
	}
	/**
	* Modifiziert die Y-Koordinate, damit auch negative Punkte dargestellt werden koennen.
	* @param y Y-Koordinate
	* @return Angepasste Y-Koordinate.
	*/
	public int remodY(int y) {
		return (int) (y + calculatorGUI.minydata * calculatorGUI.zoom);
	}
};
