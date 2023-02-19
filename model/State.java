package propra22.q9721070.model;

import java.util.ArrayList;
import java.util.List;

/**
* Die Klasse definiert den Zustand des Models, fungiert als dessen Datenstruktur und ist die Grundlage fuer die Undo/Redo-Funktion.
*/
public class State {
	/**
	* Zaehler-Variable.
	*/
	static int counter = 0;
	/**
	* Abgespeicherte X-Koordinaten. Die Reihenfolge entspricht den Zustaenden des Models.
	*/
	List<Integer> pointsStateX = new ArrayList<Integer>();
	/**
	* Abgespeicherte Y-Koordinaten. Die Reihenfolge entspricht den Zustaenden des Models.
	*/
	List<Integer> pointsStateY = new ArrayList<Integer>();
	/**
	* Anzeigezustand der Konvexen Huelle.
	*/
	Boolean checkCH;
	/**
	* Anzeigezustand des groessten Durchmessers.
	*/
	Boolean checkDia;
	/**
	* Anzeigezustand des groessten Rechtecks.
	*/
	Boolean checkQua;
	/**
	* Zustands-Pruefsumme. Wird fuer die Bestimmung der Eindeutigkeit eines jeden Zustands benoetigt. Sofern ein Zustand mehrmals hintereinander abgespeichert wird, wird einer davon geloescht.
	*/
	long checksum;
	/**
	* Transformiert True in 1 und False in 0. Wird benoetigt, um die checksum zu berechnen.
	* @param foo - boolean Variable, welche transformiert wird. 
	* @return 1 oder 0 in Abhaengigkeit vom Boolean-Wert.
	*/
	private int booleantoint(boolean foo) {
	    return (foo) ? 1 : 0;
	}
	/**
	* Konstruktor.
	* @param data Punktemenge des aktuellen Zustands.
	* @param checkCH Darstellungszustand Konvexe Huelle.
	* @param checkDia Darstellungszustand Durchmesser.
	* @param checkQua Darstellungszustand Rechteck.
	*/
	public State (List<Point> data, Boolean checkCH, Boolean checkDia, Boolean checkQua) {
		this.checksum = 0;
		for (Point p: data) {
			this.pointsStateX.add((int) p.getX());
			this.pointsStateY.add((int) p.getY());
			this.checksum += p.getX() + p.getY();
		};
		this.checkCH = checkCH;
		this.checkDia = checkDia;
		this.checkQua = checkQua;
		this.checksum += (booleantoint(checkCH) + booleantoint(checkDia) + booleantoint(checkQua));
	}
}
