package propra22.q9721070.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Die Klasse Calculator fungiert als Model im Sinne des MVC.
* Aufgabe: Hier ist die Businesslogik und die Datenhaltung verortet.
* Einordnung: Die Klasse hat keine Verbindung zum View und wird fuer den Testabgleich verwendet. Die Berechnung ist mit CalculatorGUI identisch, nur dass architekturell keine Kopplung mit der GUI stattfindet.
*/
public class Calculator {
	/**
	* Alle Punkte.
	*/
	List<Point> data;
	/**
	* Alle Punkte der Konvexen Huelle.
	*/
	List<Point> dataCH;
	/**
	* Punkte des groessten Durchmessers.
	*/
	List<Point> dataDia;
	/**
	* Punkte des groessten Rechtecks.
	*/
	List<Point> dataQua;
	
	
	/**
	* Gibt die E-Mail des bearbeitenden Studierenden zurueck.
	* @return E-Mail-Adresse: Matthias Blank.
	*/
	public String getEmail() {
		return "Matthias.Blank2@gmx.net";
	}
	/**
	* Gibt den kompletten Namen des bearbeitenden Studierenden zurueck.
	* @return Name: Matthias Blank.
	*/
	public String getName() {
		return "Matthias Blank";
	}
	/**
	* Gibt die Matrikelnummer des bearbeitenden Studierenden zurueck.
	* @return Matrikelnummer: 9721070.
	*/
	public String getMatrNr() {
		return "9721070";
	}
	/**
	* Konstruktur der Klasse.
	*/
	public Calculator() {
		data = new ArrayList<Point>();
	}
	/**
	* Entfernt alle Punkte.
	*/
	 public void clearPoints() {
		data.removeAll(data);
		reload();
	}
	/**
	* Fuegt einen Punkt der Punktmenge hinzu.
	* @param point Punkt, der hinzugefuegt wird.
	*/
	public void add(Point point) {
		data.add(point);
		reload();
	}
	/**
	* Fuegt einen Punkt mit den gegebenen Koordinaten der Punktmenge hinzu.
	* @param arg0 X-Koordinate des Punkts.
	* @param arg1 Y-Koordinate des Punkts.
	*/
	public void addPoint(int arg0, int arg1) {
		add(new Point(arg0,arg1));
	}
	/**
	* Fuegt den Inhalt des Arrays der Punktmenge hinzu. Die X-Koordinate des ersten Elements ist pointArray[0][0], Die Y-Koordinate des ersten Elements ist pointArray[0][1], usw.
	* @param arg0 Array, welches die Punktdaten enthaelt.
	*/
	public void addPointsFromArray(int[][] arg0) {
		List<Point> temp = new ArrayList<Point>();
		for (int i = 0; i < arg0.length; i++ ) {
			temp.add(new Point(i, arg0[i][1]));
		}
		data.addAll(temp);
		reload();
	}
	/**
	* Fuegt den Inhalt der angegebenen Datei als Punkte der Punktmenge hinzu. Es findet hier eine Pruefung der Datenformate statt.
	* @param file Dateiname der zu lesenden Datei.
	* @throws IOException falls beim Lesen der Datei ein Fehler aufgetreten ist.
	* @throws NumberFormatException wenn der String nicht in eine Zahl uebersetzt werden kann.
	*/
	public void importData(File file) throws NumberFormatException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			data.removeAll(data);
			List<Point> temp = new ArrayList<Point>();
			String regex1st = "[\s]*[-]?[0-9]+[\s]+[-]?[0-9]+[\s]*.*";
			String regex2nd = "[-]?[0-9]+[\s]+[-]?[0-9]+";
			while ((line = br.readLine()) != null) {
				if (Pattern.matches(regex1st, line.toString())) {
					Matcher m = Pattern.compile(regex2nd).matcher(line.toString());
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
		reload();
	}
	/**
	* Fuegt den Inhalt der angegebenen Datei als Punkte der Punktmenge hinzu. Der volle Dateiname wird ueber fileName uebergeben.
	* @param arg0 Dateiname der zu lesenden Datei.
	* @throws IOException falls beim Lesen der Datei ein Fehler aufgetreten ist.
	*/
	public void addPointsFromFile(String arg0) throws IOException {
		importData(new File(arg0));
	}
	/**
	* Loescht doppelte Punkte in der Datenmenge.
	*/
	public void delDoublePoints() {
		for (int i = 0; i<data.size()-1; i++) {
			if (data.get(i).getX() == data.get(i+1).getX() && data.get(i).getY() == data.get(i+1).getY()) {
				data.remove(i+1);
				i--;
			}
		}
	}
	/**
	* Berechnet die Ergebnisse neu, sofern beispielsweise neue Zahlen eingefuegt werden.
	*/
	public void reload() {
		Collections.sort(data, new Point());
		delDoublePoints();
		setConvexHull();
		setDiameter();
		setQuadrangle();
	}
	/**
	* Berechnet die Konvexe Huelle.
	*/
	public void setConvexHull() {
		if (data.size() <= 2) {
			dataCH = data;
		}
		if (data.size() > 2) {
			dataCH = UtilsModel.generateConvexHull(data);
		}	
	}
	/**
	* Gibt die Konvexe Huelle der Punktmenge zurueck.
	* @return Konvexe Huelle, ein zwei dimensionaler Array (oder null).
	*/
	public int[][] getConvexHull() {
		if (data.size() == 0) {
			return null;
		}
		if (data.size() == 1) {
			int[][] temp2 = new int[1][2];
			temp2 [0][0] = (int)data.get(0).getX();
			temp2 [0][1] = (int)data.get(0).getY();
			return temp2;
		}
		if (data.size() == 2) {
			int[][] temp3 = new int[2][2];
			temp3 [0][0] = (int)data.get(0).getX();
			temp3 [0][1] = (int)data.get(0).getY();
			temp3 [1][0] = (int)data.get(1).getX();
			temp3 [1][1] = (int)data.get(1).getY();
			return temp3;
		}
		int[][] temp = new int[dataCH.size()-1][2]; // CH enthaelt den ersten und letzten Punkt doppelt -> Verkuerzung der Schleife
		for (int i = 0; i < dataCH.size()-1; i++) {
			temp[i][0] = (int)dataCH.get(i).getX();
			temp[i][1] = (int)dataCH.get(i).getY();
		}
		return temp;
	}
	/**
	* Berechnet den groessten Durchmesser.
	*/
	public void setDiameter() {
		if (data.size() <= 2) {
			dataDia = data;
		}
		if (data.size() > 2) {
			dataDia = UtilsModel.generateDiameter(dataCH);
		}
	}
	/**
	* Gibt den Durchmesser zurueck, also zwei Punkte. Liefert null zurueck, falls die Punktemenge aus weniger als zwei Punkten besteht.
	* @return Durchmesser, ein Array mit zwei Punkten, sofern Punkte in der Datenmenge liegen (oder null).
	*/
	public int[][] getDiameter() {
		int[][] temp = new int[2][2];
		
		if (data.size() == 0) {
			return null;
		}
		if (data.size() == 1) {
			temp[0][0] = (int)data.get(0).getX();
			temp[0][1] = (int)data.get(0).getY();
			temp[1][0] = (int)data.get(0).getX();
			temp[1][1] = (int)data.get(0).getY();
		}
		if (data.size()>= 2) {
			temp[0][0] = (int)dataDia.get(0).getX();
			temp[0][1] = (int)dataDia.get(0).getY();
			temp[1][0] = (int)dataDia.get(1).getX();
			temp[1][1] = (int)dataDia.get(1).getY();
		}
		return temp;
	}
	/**
	* Gibt die Laenge des Durchmessers (groesster Abstand) in der Punktmenge zurueck.
	* @return Durchmesser-Laenge.
	*/
	public double getDiameterLength() {
		double temp = Math.pow(dataDia.get(0).getX()- dataDia.get(1).getX(),2) + Math.pow(dataDia.get(0).getY() - dataDia.get(1).getY(),2);
		return Math.sqrt(temp);
	}
	/**
	* Berechnet das groesste Viereck.
	*/
	public void setQuadrangle() {
		if (data.size() < 4) {
			dataQua = new ArrayList<Point>();;
		}
		if (data.size() >= 4) {
			dataQua = UtilsModel.generatedataQuadrangle(dataCH);
		}
	}
	/**
	* Gibt das groesste eingeschlossene Viereck zurueck. Liefert null zurueck, falls die Ueberpruefung des Viereck noch nicht durchgefuehrt werden soll.
	* @return das groesste Viereck, ein Array mit vier Punkten.
	*/
	public int[][] getQuadrangle() {
		int[][] temp = new int[4][4];
		if (data.size() == 0) {
			return null;
		}
		if (data.size() == 1) {
			for (int i = 0; i < 4; i++) {
				temp[i][0] = (int)dataCH.get(0).getX();
				temp[i][1] = (int)dataCH.get(0).getY();
			}
		}
		if (data.size() == 2) {
			for (int i = 0; i < 2; i++) {
				temp[i][0] = (int) dataCH.get(i).getX();
				temp[i][1] = (int) dataCH.get(i).getY();
				temp[i+2][0] = (int) dataCH.get(i).getX();
				temp[i+2][1] = (int) dataCH.get(i).getY();
			}
		}
		if (data.size() == 3) {
			for (int i = 0; i < 3; i++) {
				temp[i][0] = (int) dataCH.get(i).getX();
				temp[i][1] = (int) dataCH.get(i).getY();
			}
			temp[3][0] = (int) dataCH.get(3).getX();
			temp[3][1] = (int) dataCH.get(3).getY();
			return temp;
		}
		if (data.size() >= 4) {
			for (int i = 0; i < dataQua.size(); i++) {
				temp[i][0] = (int) dataQua.get(i).getX();
				temp[i][1] = (int) dataQua.get(i).getY();
			}
		}
		return temp;
	}
	/**
	* Gibt die Flaeche des groessten Vierecks zurueck. Liefert Double.NaN zurueck, falls die Ueberpruefung noch nicht durchgefuehrt werden soll.
	* @return Viereckflaeche.
	*/
	public double getQuadrangleArea() {
		if (data.size() >= 4) {
			double areaTri1 = UtilsModel.areaTri(dataQua.get(0),dataQua.get(1),dataQua.get(2));
			double areaTri2 = UtilsModel.areaTri(dataQua.get(0),dataQua.get(3),dataQua.get(2));
				return Math.abs(areaTri1) + Math.abs(areaTri2);
		} else {
			return Double.NaN;
		}
	}
	/**
	* Gibt das groesste eingeschlossene Dreieck zurueck. Liefert null zurueck, falls die Ueberpruefung des Dreiecks noch nicht durchgefuehrt werden soll.
	* @return null, da keine Berechnung vorgenommen wird.
	*/
	public int[][] getTriangle() {
		return null;
	}
	/**
	* Gibt die Flaeche des groessten Dreiecks zurueck. Liefert Double.NaN zurueck, falls die ueberpruefung noch nicht durchgefuehrt werden soll.
	* @return 0, da keine Berechnung vorgenommen wird. 
	*/
	public double getTriangleArea() {
		return 0;
	}	
}	
