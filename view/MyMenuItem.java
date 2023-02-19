package propra22.q9721070.view;

import java.awt.event.MouseEvent;
import javax.swing.JCheckBoxMenuItem;
/**
* Definition der modifizierten Checkboxitems (Groesstes Viereck, Durchmesser und Konvexe Huelle).
*/
class MyMenuItem extends JCheckBoxMenuItem {
	/**
	 * Default-Wert.
	 */
	private static final long serialVersionUID = 1L;
	/**
	* Konstruktor.
	* @param string Name des Items.
	*/
	public MyMenuItem(String string) {
		super(string);
	}
	/**
	* Verarbeitung des MouseEvents
	* @param e Mausaktion findet statt.
	*/
	@Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_RELEASED && contains(e.getPoint())) {
            doClick();
            setArmed(true);
        } else {
            super.processMouseEvent(e);
        }
    }
}