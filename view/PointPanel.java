package propra22.q9721070.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JPanel;
import propra22.q9721070.model.*;
import propra22.q9721070.controller.*;

/**
 * Die Klasse uebernimmt saemtliche Darstellungen im Zusammenhang mit der Punktemenge und der geometrischen Formen. Sie ist aus Uebersichtsgruenden aus dem MainFrame ausgegliedert.
*/
public class PointPanel extends JPanel{
	/**
	 * Default-Wert.
	 */
	private static final long serialVersionUID = 1L;
	/**
	* Instanz des Berechnungsmodels.
	*/
	private CalculatorGUI calculatorGUI;
	/**
	* Instanz des MainFrame.
	*/
	private MainFrame mainFrame;
	/**
	 * Konstruktor. 
	 * @param calculatorGUI Speicher- und Berechnungsmodel fuer die GUI.
	 * @param mainFrame GUI.
	 * @param controller Controller.
	*/
	public PointPanel(CalculatorGUI calculatorGUI, Controller controller, MainFrame mainFrame) {
    	this.calculatorGUI = calculatorGUI;
        this.mainFrame = mainFrame;
    	addMouseListener(controller);
        addMouseMotionListener(controller);
    }
	/**
	 * Zeichnet die Konvexe Huelle, den Durchmesser und das groesste Viereck. Dies erfolgt durch den Aufruf der unten dargestellten Methoden.
	 * @param g Einbindung der Graphics-Komponente. 
	*/
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
		mainFrame.showCH.setSelected(calculatorGUI.checkCH);
		mainFrame.showDia.setSelected(calculatorGUI.checkDia);
		
		mainFrame.showQua.setSelected(calculatorGUI.checkQua);
        if (calculatorGUI.checkCH) {
            g.setColor(Color.RED);
            drawCH(g, calculatorGUI.visibledataCH);
            mainFrame.showCH.setSelected(calculatorGUI.checkCH);
        }
        if (calculatorGUI.checkQua) {
            g.setColor(Color.darkGray);
            drawQua(g, calculatorGUI.visibledataQua);
        }
        if (calculatorGUI.checkDia) {
            g.setColor(Color.BLUE);
            drawDia(g, calculatorGUI.visibledataDia);
        }  
        g.setColor(Color.BLACK);
        drawPoints(g, calculatorGUI.data);
    }  
    /**
     * Zeichnet alle Punkte.
     * @param g Einbindung der Graphics-Komponente. 
     * @param points Liste der darzustellenden Punkte.
    */
    private void drawPoints(Graphics g, List<Point> points) {
    	for (Point p: points) {
    		g.fillOval((int)(modX(p.getX())*calculatorGUI.zoom -(int)Math.sqrt(2)*calculatorGUI.zoom*5/2) ,(int)(modY(p.getY())*calculatorGUI.zoom - (int)Math.sqrt(2)*calculatorGUI.zoom*5/2), sizePoints(5), sizePoints(5));
    	}
    }
    /**
     * Zeichnet die Konvexe Huelle.
     * @param g Einbindung der Graphics-Komponente. 
     * @param PointsCH Liste der Punkte der Konvexen Huelle.
    */
    private void drawCH(Graphics g, List<Point> PointsCH) {
    	if (PointsCH != null) {
        	if (PointsCH.size()>=3) {
        	    Graphics2D g2d = (Graphics2D)g;
        	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        	    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        	    g2d.setStroke(new BasicStroke(sizePoints(2)));
        	    for (int i = 1; i< PointsCH.size(); i++) {
        	    	g.drawLine((int)(modX(PointsCH.get(i).getX())*calculatorGUI.zoom), (int)(modY(PointsCH.get(i).getY())*calculatorGUI.zoom), (int)(modX(PointsCH.get(i-1).getX())*calculatorGUI.zoom), (int)(modY(PointsCH.get(i-1).getY())*calculatorGUI.zoom));
        	    }
        	}
    	}
    }
    /**
     * Zeichnet den groessten Durchmesser.
     * @param g Einbindung der Graphics-Komponente. 
     * @param PointsDia - Liste der Punkte des groessten Durchmessers.
    */
    private void drawDia(Graphics g, List<Point> PointsDia) {
    	if (PointsDia != null) {
        	if (PointsDia.size()>=2) {
        	    Graphics2D g2d = (Graphics2D)g;
        	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        	    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        	    g2d.setStroke(new BasicStroke((int)sizePoints(2)));
        	    g.drawLine((int)(modX(PointsDia.get(0).getX())*calculatorGUI.zoom), (int)(modY(PointsDia.get(0).getY())*calculatorGUI.zoom), (int)(modX(PointsDia.get(1).getX())*calculatorGUI.zoom), (int) (modY(PointsDia.get(1).getY())*calculatorGUI.zoom));
        	}
        }
    }
    /**
     * Zeichnet das groesste Rechteck.
     * @param g Einbindung der Graphics-Komponente. 
     * @param PointsQua Liste der Punkte des groessten Rechtecks.
    */
    private void drawQua(Graphics g, List<Point> PointsQua) {
    	if (PointsQua != null) {
        	if (PointsQua.size()>= 4 && calculatorGUI.visibledataCH.size()>4) {
        	    Graphics2D g2d = (Graphics2D)g;
        	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        	    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        	    g2d.setStroke(new BasicStroke(sizePoints(2)));
        	    g.drawLine((int)(modX(PointsQua.get(0).getX())*calculatorGUI.zoom), (int)(modY(PointsQua.get(0).getY())*calculatorGUI.zoom), (int)(modX(PointsQua.get(1).getX())*calculatorGUI.zoom), (int)(modY(PointsQua.get(1).getY())*calculatorGUI.zoom));
        	    System.out.println(PointsQua.get(0).getX());
        	    System.out.println(PointsQua.get(0).getY());
        	    System.out.println(PointsQua.get(1).getX());
        	    System.out.println(PointsQua.get(1).getY());
        	    System.out.println(PointsQua.get(2).getX());
        	    System.out.println(PointsQua.get(2).getY());
        	    System.out.println(PointsQua.get(3).getX());
        	    System.out.println(PointsQua.get(3).getY());
        	    g.drawLine((int)(modX(PointsQua.get(1).getX())*calculatorGUI.zoom), (int)(modY(PointsQua.get(1).getY())*calculatorGUI.zoom), (int)(modX(PointsQua.get(2).getX())*calculatorGUI.zoom), (int)(modY(PointsQua.get(2).getY())*calculatorGUI.zoom));
        	    g.drawLine((int)(modX(PointsQua.get(2).getX())*calculatorGUI.zoom), (int)(modY(PointsQua.get(2).getY())*calculatorGUI.zoom), (int)(modX(PointsQua.get(3).getX())*calculatorGUI.zoom), (int)(modY(PointsQua.get(3).getY())*calculatorGUI.zoom));
        	    g.drawLine((int)(modX(PointsQua.get(3).getX())*calculatorGUI.zoom), (int)(modY(PointsQua.get(3).getY())*calculatorGUI.zoom), (int)(modX(PointsQua.get(0).getX())*calculatorGUI.zoom), (int)(modY(PointsQua.get(0).getY())*calculatorGUI.zoom));
        	}
    	}
    }
    /**
     * Berechnet die Groesse der Punkte, welche durch das Zoomen groesser oder kleiner werden. Eine Mindestgroesse wird durch den Parameter Basis gewaehrleistet.
     * @param basis kleinste Groesse der Punkte.
     * @return Groesse der Punkte.
    */
	private int sizePoints(int basis) {
		if (calculatorGUI.zoom > 1) {
			return (int) (calculatorGUI.zoom * basis);
		} else {
			return basis;
		}
	}
	/**
	* Modifiziert die X-Koordinate, damit auch negative Punkte dargestellt werden koennen.
	* @param x X-Koordinate
	* @return Angepasste X-Koordinate.
	*/
	public int modX(long x) {
		return (int) (x - calculatorGUI.minxdata);
	}
	/**
	* Modifiziert die Y-Koordinate, damit auch negative Punkte dargestellt werden koennen.
	* @param y Y-Koordinate
	* @return Angepasste Y-Koordinate.
	*/
	public int modY(long y) {
		return (int) (y - calculatorGUI.minydata);
	}
}