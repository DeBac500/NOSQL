package Data;

import java.util.Date;
import java.util.List;

public interface DataHandler {
	public boolean save(Aufgabe save);
	public List<Aufgabe> getAll();
	public List<Aufgabe> find(String suche);
	public List<Aufgabe> find(Date from, Date to);
	public boolean edit(Aufgabe save);
	public boolean login(String usr, String pwd);
	public void register(String usr, String pwd);
	public List<Aufgabe> find(Object id);
}
