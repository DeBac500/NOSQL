package Data;

import java.util.Date;
import java.util.List;
/**
 * Interface zum Verarbeiten von Daten
 * @author dominik backhausen, alexander rieppel
 *
 */
public interface DataHandler {
	/**
	 * Speichert eine Aufgabe
	 * @param save neue Aufgabe
	 * @return
	 */
	public boolean save(Aufgabe save);
	/**
	 * Gibt alle Aufgaben zurueck
	 * @return alle Aufgaben
	 */
	public List<Aufgabe> getAll();
	/**
	 * suche aufgaben in denen das suchwort vorkommt
	 * @param suche suchwort
	 * @return
	 */
	public List<Aufgabe> find(String suche);
	/**
	 * sucht aufgaben die das entsprechende Datum besitzen
	 * @param suche datum
	 * @return
	 */
	public List<Aufgabe> find(Date suche);
	/**
	 * Bearbeitet diese Aufgabe
	 * @param save
	 * @return
	 */
	public boolean edit(Aufgabe save);
	/**
	 * loggt user ein
	 * @param usr
	 * @param pwd
	 * @return
	 */
	public boolean login(String usr, String pwd);
	/**
	 * registriert User
	 * @param usr
	 * @param pwd
	 */
	public void register(String usr, String pwd);
	/**
	 * sucht Objekte mit dieser ID
	 * @param id
	 * @return
	 */
	public List<Aufgabe> find(Object id);
	/**
	 * Loescht Objekte mit dieser ID
	 * @param id
	 */
	public void remove(Object id);
}
