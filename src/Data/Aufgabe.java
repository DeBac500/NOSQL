package Data;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;

public class Aufgabe {
	public static SimpleDateFormat dateform = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateformdate = new SimpleDateFormat("YYYY-MM-dd");
	public static SimpleDateFormat dateformtime = new SimpleDateFormat("HH:mm:ss");
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
	public Aufgabe(){}
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
	public ObjectId getID(){
		return this.id;
	}
	public void setID(ObjectId id){
		this.id = id;
	}
	public String getArt() {
		return art;
	}
	public void setArt(String art) {
		this.art = art;
	}
	public String getKathegorie() {
		return kathegorie;
	}
	public void setKathegorie(String kathegorie) {
		this.kathegorie = kathegorie;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastedit() {
		return lastedit;
	}
	public void setLastedit(Date lastedit) {
		this.lastedit = lastedit;
	}
	public String getZugeteilt() {
		return zugeteilt;
	}
	public void setZugeteilt(String zugeteilt) {
		this.zugeteilt = zugeteilt;
	}
	public String getAngabe() {
		return angabe;
	}
	public void setAngabe(String angabe) {
		this.angabe = angabe;
	}
	public Date getZugeteiltam() {
		return zugeteiltam;
	}
	public void setZugeteiltam(Date zugeteiltam) {
		this.zugeteiltam = zugeteiltam;
	}
	
	@Override
	public String toString(){
		return "AUFGABE: " +this.art + ", Author: " + this.autor;
	}
}
