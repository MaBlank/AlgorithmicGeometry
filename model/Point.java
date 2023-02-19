package propra22.q9721070.model;

import java.util.Comparator;

/**
* Die Klasse definiert die Punkte der Huelle.
*/
public class Point implements Comparator<Point> {
	/**
	 * X-Koordinate.
	 */
	private long x;
	/**
	 * Y-Koordinate.
	 */
	private long y;
	/**
	 * Counter-Variable.
	 */
	private static int count = 0;
	/**
	 * ID des Punktes.
	 */
	private int id;
	/**
	 * Gegen den Uhrzeigersinn folgender Punkt, sofern der Punkt in der Konvexen Huelle liegt.
	 */
	public Point nextCHPoint;
	/**
	* Konstruktor.
	*/
	public Point() {}
	/**
	* Konstruktur mit Koordinaten.
	* @param x X-Koordinate.
	* @param y Y-Koordinate.
	*/
	public Point(long x, long y) {
		this.id = count;
		count++;
		this.x = x;
		this.y = y;
	}
	/**
	* Setzt den (gegen den Uhrzeigersinn) naechsten Punkt in der Konvexen Huelle. Fungiert als eine Art LinkedList-Struktur.
	* @param p Punkt.
	*/
	public void setnextCHPoint(Point p) {
		this.nextCHPoint = p;
	}
	/**
	* Getter-Methode.
	* @return X-Koordinate.
	*/
	public long getX() {
		return x;
	}
	/**
	* Setzt die Koordinaten neu fest, wenn ein Punkt verschoben wird.
	* @param x - X-Koordinate.
	* @param y - Y-Koordinate.
	*/
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	* Setter-Methode.
	* @param x - X-Koordinate.
	*/
	public void setX(int x) {
		this.x = x;
	}
	/**
	* Getter-Methode.
	* @return y Y-Koordinate.
	*/
	public long getY() {
		return y;
	}
	/**
	* Setter-Methode.
	* @param y Y-Koordinate.
	*/
	public void setY(int y) {
		this.y = y;
	}
	/**
	* Getter-Methode.
	* @return ID des Punkts.
	*/
	public int getId() {
		return id;
	}
	/**
	* Compare-Methode (Vergleich der Punkte).
	* @param o1 Punkt1.
	* @param o2 Punkt2.
	* @return 1 oder 0 abhaengig von der lexikografischen Sortierung.
	*/
	public int compare(Point o1, Point o2) {
		long compareX = o1.getX() - o2.getX();
		if (compareX != 0) {
			return (int) compareX;
		} else {
			return (int) (o1.getY() - o2.getY());
		}
	}
}