package propra22.q9721070.main;
import propra22.q9721070.model.*;
import propra22.q9721070.view.*;

/**
* Zweck: Ausgangspunkt des Programms.
* Aufgabe und Einordnung: Es wird entweder das Tester-Interface aufgerufen oder grafische Benutzeroberflaeche generiert.
*/
public class Main {
	public static void main(String[] args) {
			CalculatorGUI pointsAll = new CalculatorGUI();
			MainFrame mainFrame = new MainFrame(pointsAll);
			pointsAll.addMainFrame(mainFrame);
		}
	}

