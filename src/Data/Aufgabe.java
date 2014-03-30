package Data;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;
/**
 * Datentyp zum speichern von Aufgaben
 * @author dominik backhausen alexander rieppel
 *
 */
public class Aufgabe implements Comparable<Aufgabe>{
	public static SimpleDateFormat dateform = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat dateformdate = new SimpleDateFormat("yyy-MM-dd");
	public static SimpleDateFormat dateformtime = new SimpleDateFormat("HH:mm");
	private ObjectId id;
	private String art;
	private String kathegorie;
	private String tags;
	private String autor;
	private Date created;
	private Date lastedit;
	private String zugeteilt;
	private Date zugeteiltam;
	private String angabe;
	//private HashMap<String, String> zusatz; //4er Gruppe
	//private String attach; //3er Gruppe
	/**
	 * Default Konstruktor
	 */
	public Aufgabe(){}
	/**
	 * Konstruktor mit allen Parametern
	 * @param art
	 * @param kat
	 * @param tags
	 * @param author
	 * @param created
	 * @param laste
	 * @param zugeteilt
	 * @param zugeteiltam
	 * @param angabe
	 */
	public Aufgabe(String art, String kat, String tags, String author, 
			Date created, Date laste, String zugeteilt,Date zugeteiltam, String angabe){
		this.art = art;
		this.kathegorie = kat;
		this.tags = tags;
		this.autor = author;
		this.created = created;
		this.lastedit = laste;
		this.zugeteilt = zugeteilt;
		this.angabe = angabe;
		this.zugeteiltam = zugeteiltam;
	}
	
	
	/**
	 * @return the id
	 */
	public ObjectId getID() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setID(ObjectId id) {
		this.id = id;
	}
	/**
	 * @return the art
	 */
	public String getArt() {
		return art;
	}
	/**
	 * @param art the art to set
	 */
	public void setArt(String art) {
		this.art = art;
	}
	/**
	 * @return the kathegorie
	 */
	public String getKathegorie() {
		return kathegorie;
	}
	/**
	 * @param kathegorie the kathegorie to set
	 */
	public void setKathegorie(String kathegorie) {
		this.kathegorie = kathegorie;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * @return the autor
	 */
	public String getAutor() {
		return autor;
	}
	/**
	 * @param autor the autor to set
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @return the lastedit
	 */
	public Date getLastedit() {
		return lastedit;
	}
	/**
	 * @param lastedit the lastedit to set
	 */
	public void setLastedit(Date lastedit) {
		this.lastedit = lastedit;
	}
	/**
	 * @return the zugeteilt
	 */
	public String getZugeteilt() {
		return zugeteilt;
	}
	/**
	 * @param zugeteilt the zugeteilt to set
	 */
	public void setZugeteilt(String zugeteilt) {
		this.zugeteilt = zugeteilt;
	}
	/**
	 * @return the zugeteiltam
	 */
	public Date getZugeteiltam() {
		return zugeteiltam;
	}
	/**
	 * @param zugeteiltam the zugeteiltam to set
	 */
	public void setZugeteiltam(Date zugeteiltam) {
		this.zugeteiltam = zugeteiltam;
	}
	/**
	 * @return the angabe
	 */
	public String getAngabe() {
		return angabe;
	}
	/**
	 * @param angabe the angabe to set
	 */
	public void setAngabe(String angabe) {
		this.angabe = angabe;
	}
	
	@Override
	public String toString(){
		return this.art;
	}
	@Override
	public int compareTo(Aufgabe o) {
		return this.art.compareTo(o.art);
	}
}
