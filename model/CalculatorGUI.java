package propra22.q9721070.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import propra22.q9721070.view.*;

/**
* Die Klasse CalculatorGUI fungiert als Model im Sinne des MVC (mit Kopplung zur GUI). 
* Aufgabe: Hier ist die Businesslogik und die Datenhaltung verortet. 
* Einordnung: Die Klasse ist mit dem View gekoppelt. Aufgrund der fachlichen Abhaengigkeit zur GUI (Bildschirmgrosse fuer Einfuegen relevant) ist dies akzeptabel.
*/
public class CalculatorGUI extends Calculator {
	/**
	* Alle Punkte.
	*/
	public List<Point> data;
	/**
	* Alle Punkte im sichtbaren Bereich.
	*/
	public List<Point> visibledata;
	/**
	* Alle Punkte der Konvexen Huelle.
	*/
	public List<Point> dataCH;
	/**
	* Alle Punkte der Konvexen Huelle im sichtbaren Bereich.
	*/
	public List<Point> visibledataCH;
	/**
	* Punkte des groessten Durchmessers.
	*/
	public List<Point> dataDia;
	/**
	* Punkte des groessten Durchmessers im sichtbaren Bereich.
	*/
	public List<Point> visibledataDia;
	/**
	* Punkte des groessten Rechtecks.
	*/
	public List<Point> dataQua;
	/**
	* Punkte des groessten Rechtecks im sichtbaren Bereich.
	*/
	public List<Point> visibledataQua;
	/**
	* Liste aller Zustaende des Models.
	*/
	public List<State> states = new ArrayList<State>();
	/**
	* Gegenwaertiger Zoomfaktor.
	*/
	public float zoom = 1.0f;
	
	/**
	* Instanz des MainFrame.
	*/
	public static MainFrame mainFrame;
	/**
	* Aktueller dargestellter Zustand.
	*/
	public static int statenow = 0;
	/**
	* Maximales X, das sichtbar ist.
	*/
	public int maxx;
	/**
	* Maximales Y, das sichtbar ist.
	*/
	public int maxy;
	/**
	* Minimales X, das sichtbar ist.
	*/
	public int minx;
	/**
	* Maximales Y, das sichtbar ist.
	*/
	public int miny;
	/**
	* Maximales Y in der Datenmenge.
	*/
	public int minydata;
	/**
	* Maximales X in der Datenmenge.
	*/
	public int minxdata;
	/**
	* Anzeigezustand der Konvexen Huelle.
	*/
	public boolean checkCH 	= true;
	/**
	* Anzeigezustand des groessten Durchmessers.
	*/
	public boolean checkDia = true;
	/**
	* Anzeigezustand des groessten Vierecks.
	*/
	public boolean checkQua = true;
	
	/**
	* Suche des Punktes mit der hoechsten Y-Koordinate.
	* @return hoechste Y-Koordinate.
	*/
	public int getHighestY() {
		if (data.size()>0) {
			int highestY = (int) data.get(0).getY();
			for (Point pmax: data) {
				if (pmax.getY() > highestY) {
					highestY = (int) pmax.getY();
				}
			}
			return highestY;
		}
		return 0;
	}
	/**
	* Suche des Punktes mit der hoechsten X-Koordinate.
	* @return hoechste X-Koordinate.
	*/
	public int getHighestX() {
		if (data.size()>0) {
			int highestX = (int) data.get(0).getX();
			for (Point pmax: data) {
				if (pmax.getX() > highestX) {
					highestX = (int) pmax.getX();
				}
			}
			return highestX;
		}
		return 0;
	}
	/**
	* Suche des Punktes mit der niedrigsten X-Koordinate.
	* @return niedrigste X-Koordinate.
	*/
	public int getLowestX() {
		if (data.size()>0) {
			int lowestX = 0;
			for (Point pmin: data) {
				if (pmin.getX() < lowestX) {
					lowestX = (int) pmin.getX();
				}
			}
			minxdata = lowestX - 10; // Reduktion um 10 damit der Punkt besser sichtbar ist.
			return lowestX;
		}
		return 0;
	}
	/**
	* Suche des Punktes mit der niedrigsten Y-Koordinate.
	* @return niedrigste Y-Koordinate.
	*/
	public int getLowestY() {
		if (data.size()>0) {
			int lowestY = 0;
			for (Point pmin: data) {
				if (pmin.getY() < lowestY) {
					lowestY = (int) pmin.getY();
				}
			}
			minydata = lowestY -10; // Reduktion um 10 damit der Punkt besser sichtbar ist.
			return lowestY;
		}
		return 0;
	}
	
	/**
	* Aendert den Zustand der Anzeige des groessten Quadrats. Fuer das Ein- und Ausblenden notwendig.
	*/
	public void checkQuaCheckedChanger() {
		addState();
		checkQua = !checkQua;
		addState();
	}
	/**
	* Aendert den Zustand der Anzeige des Durchmessers. Fuer das Ein- und Ausblenden notwendig.
	*/
	public void checkDiaCheckedChanger() {
		addState();
		checkDia = !checkDia;
		addState();
	}
	/**
	* Aendert den Zustand der Anzeige der Konvexen Huelle. Fuer das Ein- und Ausblenden notwendig.
	*/
	public void checkCHCheckedChanger() {
		addState();
		checkCH = !checkCH;
		addState();
	}
	
	/**
	* Konstruktor.
	*/
	public CalculatorGUI() {
		CalculatorGUI.mainFrame = null; 
		data = new ArrayList<Point>();
		Collections.sort(data, new Point());
		addState();
	}
	/**
	* Kopplung der grafischen Oberflaeche mit dem Model.
	* @param mainFrame Instanz der GUI.
	*/
	public void addMainFrame(MainFrame mainFrame) {
		CalculatorGUI.mainFrame = mainFrame;
	}
	/**
	* Entfernt einen Punkt aus der Datenmenge.
	* @param p zu entfernender Punkt.
	*/
	public void remove(Point p) {
		data.remove(p);
		addState();
		reload();
	}
	/**
	* Loescht alle Punkte und setzt die Anzeigen auf sichtbar ("Neu-Funktion").
	*/
	public void clearPoints() {
		data.removeAll(data);
		checkCH = true;
		checkDia = true;
		checkQua = true;
		zoom = 1.0f;
		addState();
		reload();
	}
	/**
	* Neuberechnung der geometrischen Formen bei Verschieben eines Punktes.
	*/
	public void PointChange() {
		Collections.sort(data, new Point());
		reload();
	}
	/**
	* Fuegt Punkt hinzu und fuehrt Neuberechnung der geometrischen Formen durch.
	*/
	public void add(Point point) {
		data.add(point);
		Collections.sort(data, new Point());
		addState();
		reload();
	}
	/**
	* Berechnet zufaelligen Punkt, der im sichtbaren Bereich liegt.
	* @param minx minimales X (festgelegt durch GUI)
	* @param miny minimales Y (festgelegt durch GUI)
	* @param maxx maximales X (festgelegt durch GUI)
	* @param maxy maximales Y (festgelegt durch GUI)
	* @return neuer Punkt
	*/
	public Point getRandomPoint(int minx, int miny, int maxx, int maxy) {
		return new Point((int) (Math.random() * (maxx-minx)+minx),(int)(Math.random() * (maxy-miny)+miny));
	}
	/**
	* Generiert eine bestimmte Zahl von Zufallspunkten und fuehrt eine Neuberechnung der geometrischen Formen durch. Punkte werden nur im sichtbaren Bereich eingefuegt.
	* @param number Anzahl der Zufallspunkte.
	*/
	public void generateRandomPoints(int number) {
		List<Point> temp = new ArrayList<Point>();

		maxx = (int) ((int) (mainFrame.getContentPane().getSize().getWidth())/zoom + (int) ((int) ((int) mainFrame.scrollPane.getViewport().getViewPosition().getX() + (minxdata+1)*zoom)/zoom));
		maxy = (int) ((int) (mainFrame.getContentPane().getSize().getHeight())/zoom + (int) ((int) ((int) mainFrame.scrollPane.getViewport().getViewPosition().getY()+ (minydata+1)*zoom)/zoom));
		minx = (int) ((int) ((int) mainFrame.scrollPane.getViewport().getViewPosition().getX() + (minxdata)*zoom)/zoom);
		miny = (int) ((int) ((int) mainFrame.scrollPane.getViewport().getViewPosition().getY() + (minydata)*zoom)/zoom);
		
		for (int i = 0; i< number; i++) {
			temp.add(getRandomPoint(minx,miny,maxx,maxy));
		}
		data.addAll(temp);
		Collections.sort(data, new Point());
		addState();
		reload();
	}
	/**
	* Fuegt den Inhalt der angegebenen Datei als Punkte der Punktmenge hinzu und fuehrt Neuberechnung durch. Es findet hier zudem eine Pruefung der Datenformate statt.
	* @param file der volle Dateiname der zu lesenden Datei.
	* @throws IOException falls beim Lesen der Datei ein Fehler aufgetreten ist.
	* @throws NumberFormatException wenn der String nicht in eine Zahl uebersetzt werden kann.
	*/
	@Override
	public void importData(File file) throws NumberFormatException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			data.removeAll(data);
			List<Point> temp = new ArrayList<Point>();
			String regex = "[\s]*[-]?[0-9]+[\s]+[-]?[0-9]+[\s]*.*";
			String regex2nd = "[-]?[0-9]+[\s]+[-]?[0-9]+";
			while ((line = br.readLine()) != null) {
				String lineString = line.toString();
				lineString = lineString.replace("+", "");
				if (Pattern.matches(regex, lineString)) {
					Matcher m = Pattern.compile(regex2nd).matcher(lineString);
					m.find();
					String coordinateString = m.group(0);
					while (coordinateString.length() > coordinateString.replace("  ", " ").length()) {
						coordinateString = coordinateString.replace("  ", " ");
					}
					String[] res = coordinateString.split(" ");
					temp.add(new Point(Integer.parseInt(res[0]),Integer.parseInt(res[1])));
				}
			}
			data.addAll(temp);
		}

        Collections.sort(data, new Point());
		getLowestY();
		getLowestX();
		addState();
		reload();
	}
	/**
	* Speichert die Datenpunkte ab.
	* @param file der volle Dateiname der zu lesenden Datei.
	* @throws IOException falls beim Lesen der Datei ein Fehler aufgetreten ist.
	* @throws NumberFormatException wenn der String nicht in eine Zahl uebersetzt werden kann.
	*/
	public void exportData(File file) throws NumberFormatException, IOException {
		FileWriter stringWriter = new FileWriter(file);
        BufferedWriter buffWriter = new BufferedWriter(stringWriter);
        for (Point p: data) {
        	buffWriter.write(Integer.toString((int) p.getX()));
        	buffWriter.write(" ");
        	buffWriter.write(Integer.toString((int) p.getY()));
        	buffWriter.newLine();
        } 
        buffWriter.close();
        reload();
	}
	/**
	* Liefert Punkt zurueck, um bestimmte Aktionen durchzufuehren.
	* @param i Index des Punkts.
	* @return liefert den Punkt mit Index i zurueck.
	*/
	public Point get(int i) {
		return data.get(i);
	}
	/**
	* Liefert Punktemenge zurueck.
	* @return Punktemenge.
	*/
	public List<Point> data() {
		return data;
	}
	/**
	* Loescht alle Punkte, welche doppelt in der Datenmenge vorkommen. Dies ist notwendig, da sonst die Algorithmen nicht funktionieren.
	*/
	@Override
	public void delDoublePoints() {
		for (int i = 0; i<data.size()-1; i++) {
			if (data.get(i).getX() == data.get(i+1).getX() && data.get(i).getY() == data.get(i+1).getY()) {
				data.remove(i+1);
				i--;
			}
		}
	}
	/**
	* Nimmt eine Neuberechnung der geometrischen Formen vor (z.B. bei Hinzufuegen von Punkten).
	*/
	@Override
	public void reload() {
		mainFrame.repaint();
		delDoublePoints();
		setConvexHull();
		setDiameter();
		setQuadrangle();
		setVisibledata();
		setVisibleConvexHull();
		setvisibleDiameter();
		setVisibleQuadrat();
	}
	/**
	* Berechnet die Punkte, die im sichtbaren GUI-Bereich liegen.
	*/
	public void setVisibledata() {
		visibledata = new ArrayList<Point>();
		if (data.size()>0) {
			
			maxx = (int) ((int) (mainFrame.getContentPane().getSize().getWidth())/zoom + (int) ((int) ((int) mainFrame.scrollPane.getViewport().getViewPosition().getX() + (minxdata+1)*zoom)/zoom));
			maxy = (int) ((int) (mainFrame.getContentPane().getSize().getHeight())/zoom + (int) ((int) ((int) mainFrame.scrollPane.getViewport().getViewPosition().getY()+ (minydata+1)*zoom)/zoom));
			minx = (int) ((int) ((int) mainFrame.scrollPane.getViewport().getViewPosition().getX() + (minxdata)*zoom)/zoom);
			miny = (int) ((int) ((int) mainFrame.scrollPane.getViewport().getViewPosition().getY()+ (minydata)*zoom)/zoom);
			
			for (Point p: data) {
				if (p.getX() <= maxx && p.getX() >= minx && p.getY() <= maxy && p.getY() >= miny) {
					visibledata.add(p);
				}
			}
		}
	}
	/**
	* Berechnet die Konvexe Huelle der Punkte, die im sichtbaren Bereich liegen.
	*/
	public void setVisibleConvexHull() {
		if (visibledata.size() <= 2 ) {
			visibledataCH = new ArrayList<Point>();
		}
		if (visibledata.size() > 2) {
			visibledataCH = UtilsModel.generateConvexHull(visibledata);
		}
	}
	/**
	* Berechnet die Punkte des groessten Durchmessers, der im sichtbaren Bereich liegt. 
	*/
	public void setvisibleDiameter() {
		if (visibledata.size() < 2) {
			visibledataDia = new ArrayList<Point>();
		}
		if (visibledata.size() == 2) {
			visibledataDia = visibledata;
		}
		if (visibledata.size() > 2) {
			visibledataDia = UtilsModel.generateDiameter(visibledataCH);
		}
	}
	/**
	* Berechnet die Punkte des groessten Rechtecks, das im sichtbaren Bereich liegt. 
	*/
	public void setVisibleQuadrat() {
		if (visibledata.size() < 4) {
			visibledataQua = new ArrayList<Point>();
		}
		if (visibledata.size() >= 4) {
			visibledataQua = UtilsModel.generatedataQuadrangle(visibledataCH);
		}
	}
	/**
	* Der gegenwaertige Zustand wird abgespeichert. Grundlage fuer den Undo/Redo-Mechanismus. 
	*/
	public void addState() {
		State s = new State(data, checkCH, checkDia, checkQua);
		if ((states.size()) == 0 || (s.checksum != states.get(states.size()-1).checksum)) {
			if (statenow != (states.size())) {
				states.removeAll(states.subList(statenow, states.size()));
				for (int i = 0; i < states.size()-1; i++) {
					if (states.get(i).checksum == states.get(i+1).checksum) {
						states.remove(i);
						statenow--;
					}
				}
			}
			states.add(s);
			statenow ++;
		}
	}
	/**
	* Ersetzt die gegenwaertige Punktemenge durch den letzten Zustand. (Undo-Mechanimus).
	*/
	public void setlastState() {
		if (statenow == 1) {
			data.removeAll(data);
			checkCH = true;
			checkDia = true;
			checkQua = true;
			reload();
		}
		if (statenow>1) {
			data.removeAll(data);
			int param = statenow-2;
			for (int i = 0; i < states.get(param).pointsStateX.size(); i++) {
				data.add(new Point(states.get(param).pointsStateX.get(i),states.get(param).pointsStateY.get(i)));
			}
			checkCH = states.get(param).checkCH;
			checkDia = states.get(param).checkDia;
			checkQua = states.get(param).checkQua;
			statenow--;
			reload();
		}
	}
	/**
	* Ersetzt die gegenwaertige Punktemenge durch den letzten bereits dargestellten Zustand (Redo-Mechanimus).
	*/
	public void setnextState() {
		if (statenow != states.size()) {
			if (statenow>0) {
				data.removeAll(data);
			}
			for (int i = 0; i < states.get(statenow).pointsStateX.size(); i++) {
				data.add(new Point(states.get(statenow).pointsStateX.get(i),states.get(statenow).pointsStateY.get(i)));
			}
			checkCH = states.get(statenow).checkCH;
			checkDia = states.get(statenow).checkDia;
			checkQua = states.get(statenow).checkQua;
			statenow ++;
			reload();
		}
	}
	/**
	* Modifiziert die X-Koordinate, damit auch negative Punkte dargestellt werden koennen.
	* @param x X-Koordinate
	* @return Angepasste X-Koordinate.
	*/
	public int modX(int x) {
		return x - minxdata;
	}
	/**
	* Modifiziert die Y-Koordinate, damit auch negative Punkte dargestellt werden koennen.
	* @param y Y-Koordinate
	* @return Angepasste Y-Koordinate.
	*/
	public int modY(int y) {
		return y - minydata;
	}
}	