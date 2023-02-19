package propra22.q9721070.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
* Die Klasse fungiert als Hilfsklasse. Hier sind verschiedene Berechnungen fuer u.a. die geometrischen Formen ausgelagert. 
*/
class UtilsModel {
	/**
	* Berechnet die Determinante von drei Punkten. Wird u.a. fuer die Berechnung des Durchmessers benoetigt.
	* @param a Punkt.
	* @param b Punkt.
	* @param c Punkt.
	* @return gibt die Determinante zurueck.
	*/
	static long Determinante(Point a, Point b, Point c) {
		return a.getX()* (b.getY()-c.getY()) + b.getX()*(c.getY()-a.getY()) + c.getX()*(a.getY()-b.getY());
	}
	/**
	* Berechnet die Konvexe Huelle aus einer Menge von Punkten.
	* @param data aktuelle Punktemenge.
	* @return gibt die Konvexe Huelle zurueck.
	*/
	static List<Point> generateConvexHull(List<Point> data) {
		List<Point> KonturpolygonUpperLeft = new ArrayList<Point>();
		List<Point> KonturpolygonUpperRight= new ArrayList<Point>();
		List<Point> KonturpolygonLowerLeft = new ArrayList<Point>();
		List<Point> KonturpolygonLowerRight= new ArrayList<Point>();
		
		List<Point> KonvexHullUpperLeft = new ArrayList<Point>();
		List<Point> KonvexHullLowerLeft = new ArrayList<Point>();
		List<Point> KonvexHullUpperRight = new ArrayList<Point>();
		List<Point> KonvexHullLowerRight = new ArrayList<Point>();
		
		List<Point> KonvexHull = new ArrayList<Point>();
	
		boolean maxYreachedfromLeft = false;
		boolean minYreachedfromLeft = false;
		boolean maxYreachedfromRight = false;
		boolean minYreachedfromRight = false;
		
		Point minY = data.get(0);
		int lowestY = (int) data.get(0).getY();
		for (Point pmin: data) {
			if (pmin.getY() < lowestY) {
				lowestY = (int) pmin.getY();
				minY = pmin;
			}
		}
		
		Point maxY = data.get(0);
		int highestY = (int) data.get(0).getY();
		for (Point pmax: data) {
			if (pmax.getY() > highestY) {
				highestY = (int) pmax.getY();
				maxY = pmax;
			}
		}
	
		Point minYsofarFromLeft = data.get(0);
		Point maxYsofarFromLeft = data.get(0); 
		Point minYsofarFromRight = data.get(data.size()-1);
		Point maxYsofarFromRight = data.get(data.size()-1);
		
		KonturpolygonUpperLeft.add(data.get(0));
		KonturpolygonLowerLeft.add(data.get(0));
		KonturpolygonUpperRight.add(data.get(data.size()-1));
		KonturpolygonLowerRight.add(data.get(data.size()-1));

		for (int i = 0; i <= data.size()-1; i++) {
			Point p = data.get(i);
			
			if (p.getId() == maxY.getId()) {
				maxYreachedfromLeft = true;
				KonturpolygonUpperLeft.add(p);
				maxYsofarFromLeft = p;
			}
			if (p.getId() == minY.getId()) {
				minYreachedfromLeft = true;
				KonturpolygonLowerLeft.add(p);
				minYsofarFromLeft = p;
			}
			if (p.getY() > maxYsofarFromLeft.getY()) {
				KonturpolygonUpperLeft.add(p);
				maxYsofarFromLeft = p;
			}
			if (p.getY() < minYsofarFromLeft.getY()) {
				KonturpolygonLowerLeft.add(p);
				minYsofarFromLeft = p;
			}
			if (maxYreachedfromLeft && minYreachedfromLeft) {
				break;
			}
		}

		for (int i = data.size()-1; i>=0; i--) {
			Point p = data.get(i);
			
			if ((p.getId() == maxY.getId())) {
				maxYreachedfromRight = true;
				KonturpolygonUpperRight.add(p);
				maxYsofarFromRight = p;
			}
			if ((p.getId() == minY.getId())) {
				minYreachedfromRight = true;
				KonturpolygonLowerRight.add(p);
				minYsofarFromRight = p;
			}
			if (p.getY() > maxYsofarFromRight.getY()) {
				KonturpolygonUpperRight.add(p);
				maxYsofarFromRight = p;
			}
			if (p.getY() < minYsofarFromRight.getY()) {
				KonturpolygonLowerRight.add(p);
				minYsofarFromRight = p;
			}	
			if (maxYreachedfromRight && minYreachedfromRight) {
				break;
			}
		}
		
		
		List<Point> l1 = KonvexHullUpperLeft;
		
		if (KonturpolygonUpperLeft.size()<3) {
			KonvexHullUpperLeft = KonturpolygonUpperLeft;
			
		} else {
			KonvexHullUpperLeft.add(KonturpolygonUpperLeft.get(0));
			KonvexHullUpperLeft.add(KonturpolygonUpperLeft.get(1));
			for (int i = 2; i < KonturpolygonUpperLeft.size(); i++) {
				if (UtilsModel.Determinante(KonvexHullUpperLeft.get(l1.size()-2), KonvexHullUpperLeft.get(l1.size()-1),KonturpolygonUpperLeft.get(i)) < 0) {
					KonvexHullUpperLeft.add(KonturpolygonUpperLeft.get(i));
				} else {
					KonvexHullUpperLeft.remove(l1.size()-1);
					KonvexHullUpperLeft.add(KonturpolygonUpperLeft.get(i));
					while((KonvexHullUpperLeft.size() > 2) && (UtilsModel.Determinante(KonvexHullUpperLeft.get(l1.size()-3),KonvexHullUpperLeft.get(l1.size()-2),KonvexHullUpperLeft.get(l1.size()-1))>=0)) { //
						KonvexHullUpperLeft.remove(l1.size()-2);
					}
				}
			}
		}
		
		List<Point> l3 = KonvexHullUpperRight;
		
		if (KonturpolygonUpperRight.size()<3) {
			KonvexHullUpperRight = KonturpolygonUpperRight;
			
		} else {
			KonvexHullUpperRight.add(KonturpolygonUpperRight.get(0));
			KonvexHullUpperRight.add(KonturpolygonUpperRight.get(1));
			for (int i = 2; i < KonturpolygonUpperRight.size(); i++) {
				if (UtilsModel.Determinante(KonvexHullUpperRight.get(l3.size()-2), KonvexHullUpperRight.get(l3.size()-1),KonturpolygonUpperRight.get(i)) > 0) { //
					KonvexHullUpperRight.add(KonturpolygonUpperRight.get(i));
				} else {
					KonvexHullUpperRight.remove(l3.size()-1);
					KonvexHullUpperRight.add(KonturpolygonUpperRight.get(i));
					while((KonvexHullUpperRight.size() > 2) && (UtilsModel.Determinante(KonvexHullUpperRight.get(l3.size()-3),KonvexHullUpperRight.get(l3.size()-2),KonvexHullUpperRight.get(l3.size()-1))<=0)) { //
						KonvexHullUpperRight.remove(l3.size()-2);
					}
				}
			}
		}
		
		List<Point> l2 = KonvexHullLowerLeft;
		
		if (KonturpolygonLowerLeft.size()<3) {
			KonvexHullLowerLeft = KonturpolygonLowerLeft;
			
		} else {
			KonvexHullLowerLeft.add(KonturpolygonLowerLeft.get(0));
			KonvexHullLowerLeft.add(KonturpolygonLowerLeft.get(1));
			for (int i = 2; i < KonturpolygonLowerLeft.size(); i++) {
				if (UtilsModel.Determinante(KonvexHullLowerLeft.get(l2.size()-2), KonvexHullLowerLeft.get(l2.size()-1),KonturpolygonLowerLeft.get(i)) > 0) { //
					KonvexHullLowerLeft.add(KonturpolygonLowerLeft.get(i));
				} else {
					KonvexHullLowerLeft.remove(l2.size()-1);
					KonvexHullLowerLeft.add(KonturpolygonLowerLeft.get(i));
					while((KonvexHullLowerLeft.size() > 2) && (UtilsModel.Determinante(KonvexHullLowerLeft.get(l2.size()-3),KonvexHullLowerLeft.get(l2.size()-2),KonvexHullLowerLeft.get(l2.size()-1))<=0)) { //
						KonvexHullLowerLeft.remove(l2.size()-2);
					}
				}
			}
		}
		
		List<Point> l4 = KonvexHullLowerRight;
		
		if (KonturpolygonLowerRight.size()<3) {
			KonvexHullLowerRight = KonturpolygonLowerRight;
			
		} else {
			KonvexHullLowerRight.add(KonturpolygonLowerRight.get(0));
			KonvexHullLowerRight.add(KonturpolygonLowerRight.get(1));
			for (int i = 2; i < KonturpolygonLowerRight.size(); i++) {
				if (UtilsModel.Determinante(KonvexHullLowerRight.get(l4.size()-2), KonvexHullLowerRight.get(l4.size()-1),KonturpolygonLowerRight.get(i)) < 0) {
					KonvexHullLowerRight.add(KonturpolygonLowerRight.get(i));
					
				} else {
					KonvexHullLowerRight.remove(l4.size()-1);
					KonvexHullLowerRight.add(KonturpolygonLowerRight.get(i));
					while((KonvexHullLowerRight.size() > 2) && (UtilsModel.Determinante(KonvexHullLowerRight.get(l4.size()-3),KonvexHullLowerRight.get(l4.size()-2),KonvexHullLowerRight.get(l4.size()-1))>=0)) { //
						KonvexHullLowerRight.remove(l4.size()-2);
					}
				}
			}
		}
		
		Collections.reverse(KonvexHullUpperLeft);
		Collections.reverse(KonvexHullLowerRight);
		
		KonvexHull.addAll(KonvexHullLowerLeft);
		KonvexHull.addAll(KonvexHullLowerRight);
		KonvexHull.addAll(KonvexHullUpperRight);
		KonvexHull.addAll(KonvexHullUpperLeft);
		
		for (int i = 0; i < KonvexHull.size()-1; i++) {
			if (KonvexHull.get(i+1).getId() == KonvexHull.get(i).getId()) {
				KonvexHull.remove(i+1);
				i--;
			}
		}
		
		Collections.reverse(KonvexHull);
		
		for (int i = 0; i < KonvexHull.size()-1; i++) {
			KonvexHull.get(i).setnextCHPoint(KonvexHull.get(i+1));
		}
	
		return KonvexHull;
	}
	/**
	* Berechnet den groessten Durchmesser aus einer Konvexen Huelle.
	* @param dataCH aktuelle Konvexe Huelle.
	* @return gibt die Punkte des groesssten Durchmessers zurueck.
	*/
	static List<Point> generateDiameter(List<Point> dataCH) {	
		List<Point> dataDiameter = new ArrayList<Point>();
		
		if (dataCH.size() >= 2) {
			double diameter = 0;
			dataDiameter = new ArrayList<Point>();
			
			Point A = dataCH.get(0);
			Point B = null;
			
			int highestX = 0;
			for (Point p: dataCH) {
				if (p.getX()>highestX) {
					highestX = (int) p.getX();
					B = p;
				}
			}
			Point Afinal = B;
			Point Bfinal = A;			
			
			while (A != Afinal || B != Bfinal) {
				double diameterNew = Math.pow(A.getX()- B.getX(), 2)+ Math.pow(A.getY()- B.getY(), 2);
				if (diameterNew > diameter) {
					diameter = diameterNew;
					dataDiameter.removeAll(dataDiameter);
					dataDiameter.add(A);
					dataDiameter.add(B);
				}
				
				long DetCH = UtilsModel.Determinante(A, A.nextCHPoint, new Point(B.getX() + A.getX() - B.nextCHPoint.getX(), B.getY() + A.getY() - B.nextCHPoint.getY()));
				
				if (DetCH < 0) {
					A = A.nextCHPoint;
				}
				if (DetCH > 0) {
					B = B.nextCHPoint;
				}
				if (DetCH == 0) {
					if (Math.pow(A.getX()- A.nextCHPoint.getX(), 2)+ Math.pow(A.getY()- A.nextCHPoint.getY(), 2) > Math.pow(B.getX()- B.nextCHPoint.getX(), 2)+ Math.pow(B.getY()- B.nextCHPoint.getY(), 2)) {
						A = A.nextCHPoint;
					}
					else {
						B = B.nextCHPoint;
					}
				}
			}	
		}
		return dataDiameter;
	}
	/**
	* Berechnet das groesste Viereck aus einer Konvexen Huelle.
	* @param dataCH aktuelle Konvexe Huelle.
	* @return gibt die Punkte des groessten Vierecks zurueck.
	*/
	static List<Point> generatedataQuadrangle(List<Point> dataCH) {
		if (dataCH.size() > 3) {
			List<Point> dataQuadrat = new ArrayList<Point>();
			
			Point A = dataCH.get(0);
			Point C = null;
			
			int highestX = 0;
			for (Point p: dataCH) {
				if (p.getX() > highestX) {
					highestX = (int) p.getX();
					C = p;
				}
			}
			
			Point Afinal = C;
			Point Cfinal = A;
			Point B = A;
			Point D = C;
			
			List<Point> biggestQuadrat = null;
			
			while (A != Afinal || C != Cfinal) {
				
				while (UtilsModel.Determinante(new Point(C.getX()+B.getX()-A.getX(),C.getY()+B.getY()-A.getY()),B,B.nextCHPoint)>0) {
					B = B.nextCHPoint;
				}
				while (UtilsModel.Determinante(new Point(A.getX()+D.getX()-C.getX(),A.getY()+D.getY()-C.getY()),D,D.nextCHPoint)>0) {
					D = D.nextCHPoint;
				}
				if (biggestQuadrat == null) {
					biggestQuadrat = new ArrayList<Point>();
					biggestQuadrat.add(A);
					biggestQuadrat.add(B);
					biggestQuadrat.add(C);
					biggestQuadrat.add(D);

				} else {
					long ComparatorDetNewQ = (UtilsModel.Determinante(A,B,C) + UtilsModel.Determinante(A,C,D));
					long ComparatorDetOldQ = (UtilsModel.Determinante(biggestQuadrat.get(0),biggestQuadrat.get(1),biggestQuadrat.get(2)) + UtilsModel.Determinante(biggestQuadrat.get(0),biggestQuadrat.get(2),biggestQuadrat.get(3)));
					
					if (ComparatorDetNewQ > ComparatorDetOldQ) {
						biggestQuadrat.removeAll(biggestQuadrat);
						biggestQuadrat.add(A);
						biggestQuadrat.add(B);
						biggestQuadrat.add(C);
						biggestQuadrat.add(D);
					}
				}
				
				long DetCH = UtilsModel.Determinante(A, A.nextCHPoint, new Point(C.getX() + A.getX()-C.nextCHPoint.getX(), C.getY() + A.getY()-C.nextCHPoint.getY()));
				if (DetCH<0) {
					A = A.nextCHPoint;
				}
				if (DetCH>0) {
					C = C.nextCHPoint;
				}
				if (DetCH==0) {
					if (Math.pow(A.getX()- A.nextCHPoint.getX(), 2)+ Math.pow(A.getY()- A.nextCHPoint.getY(), 2) > Math.pow(C.getX()- C.nextCHPoint.getX(), 2)+ Math.pow(C.getY()- C.nextCHPoint.getY(), 2)) {
						A = A.nextCHPoint;
					}
					else {
						C = C.nextCHPoint;
					}
				}
			}
			Collections.reverse(biggestQuadrat);
			dataQuadrat = biggestQuadrat;
			return dataQuadrat;
		}
		return null;
	}
	/**
	* Berechnet die Flaeche eines Dreiecks. Wird u.a. fuer die Berechnung der Groesse des groessten Vierecks benoetigt. 
	* @param a Punkt.
	* @param b Punkt.
	* @param c Punkt.
	* @return Flaeche des Dreiecks aus den drei Punkten.
	*/
	static double areaTri(Point a, Point b, Point c) {
		Point vecBtoA = new Point(a.getX() - b.getX(),a.getY() - b.getY());
		Point vecBtoC = new Point(c.getX() - b.getX(),c.getY() - b.getY());
		float area = Math.abs((vecBtoA.getX() * vecBtoC.getY() - vecBtoA.getY() * vecBtoC.getX())/2);
		return area;
	}
}