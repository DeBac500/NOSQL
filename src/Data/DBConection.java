package Data;

import java.awt.Cursor;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

public class DBConection implements DataHandler{
	public static String Art = "Art", Author = "Author", Kathegorie = "Kathegorie", Tags = "Tags", 
			Zugeteilt = "Zugeteilt",Created = "Created", LastEdit = "LastEdit" , ZueteiltAm = "ZugeteiltAm",
			Date = "Date", Time = "Time", ID = "_id", Angabe="Angabe";
	private MongoClient client;
	private DB db;
	public DBConection (String ip, String dbname) throws UnknownHostException{
		this(ip,27017 ,dbname);
	}
	@Override
	public List<Aufgabe> getAll() {
		if(db.isAuthenticated()){
			List<Aufgabe> list = new ArrayList<Aufgabe>();
			DBCollection aufgaben = db.getCollection("Aufgaben");
			DBCursor cursor = aufgaben.find();
			while(cursor.hasNext()) {
				try{
				DBObject ob = cursor.next();
				list.add(export(ob));
	//			System.out.println(ob);
				}catch(ParseException e){}
			}
			cursor.close();
			return list;
		}else{
			return null;
		}
	}
	public DBConection (String ip, int port, String dbname) throws UnknownHostException{
		client = new MongoClient(ip,port);
		db = client.getDB(dbname);
	}
	@Override
	public boolean save(Aufgabe save) {
		if(db.isAuthenticated()){
			DBCollection aufgaben = db.getCollection("Aufgaben");
			BasicDBObject ob = new BasicDBObject();
			ob.append(DBConection.Art, save.getArt());
			ob.append(DBConection.Author, save.getAutor());
			ob.append(DBConection.Kathegorie, save.getKathegorie());
			ob.append(DBConection.Tags, save.getTags());
			ob.append(DBConection.Zugeteilt, save.getZugeteilt());
			ob.append(DBConection.Angabe, save.getAngabe());
			
			ob.append(DBConection.Created, saveDate(save.getCreated()));
			ob.append(DBConection.LastEdit, saveDate(save.getLastedit()));
			ob.append(DBConection.ZueteiltAm, saveDate(save.getZugeteiltam()));
			
			aufgaben.insert(ob);
			return true;
		}else{
			return false;
		}
	}
	private Date getDate(DBObject ob, String key) throws ParseException{
		DBObject ob1 = (DBObject)ob.get(key);
		String d = (String)ob1.get(DBConection.Date);
		String t = (String)ob1.get(DBConection.Time);
		return Aufgabe.dateform.parse(d + " " + t);
	}
	private BasicDBObject saveDate(Date date) {
		BasicDBObject ob = new BasicDBObject();
		ob.append(DBConection.Date, Aufgabe.dateformdate.format(date));
		ob.append(DBConection.Time, Aufgabe.dateformtime.format(date));
		return ob;
	}
	@Override
	public List<Aufgabe> find(String suche) {
		if(db.isAuthenticated()){
			List<Aufgabe> list = new ArrayList<Aufgabe>();
			DBCollection aufgaben = db.getCollection("Aufgaben");
			
			DBObject findObj = QueryBuilder.start().or(
					new BasicDBObject(DBConection.Art, java.util.regex.Pattern.compile(suche))).or(
					new BasicDBObject(DBConection.Kathegorie, java.util.regex.Pattern.compile(suche))).or(
					new BasicDBObject(DBConection.Tags, java.util.regex.Pattern.compile(suche))).get();;
			DBCursor cursor = aufgaben.find(findObj);
			while(cursor.hasNext()) {
				try{
					DBObject ob = cursor.next();
					list.add(export(ob));
		//			System.out.println(ob);
				}catch(ParseException e){}
			}
			cursor.close();
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<Aufgabe> find(Date suche) {
		if(db.isAuthenticated()){
			List<Aufgabe> list = new ArrayList<Aufgabe>();
			DBCollection aufgaben = db.getCollection("Aufgaben");
			DBObject ob1 = QueryBuilder.start().or(
					new BasicDBObject(DBConection.Created+"."+DBConection.Date,Aufgabe.dateformdate.format(suche))).or(
					new BasicDBObject(DBConection.ZueteiltAm+"."+DBConection.Date,Aufgabe.dateformdate.format(suche))).or(
					new BasicDBObject(DBConection.LastEdit+"."+DBConection.Date,Aufgabe.dateformdate.format(suche))).get();
			DBCursor cursor = aufgaben.find(ob1);
			while(cursor.hasNext()) {
				try{
				DBObject ob = cursor.next();
				list.add(export(ob));
	//			System.out.println(ob);
				}catch(ParseException e){}
			}
			cursor.close();
			return list;
		}else{
			//TODO Exception
			return null;
		}
	}
	@Override
	public boolean edit(Aufgabe save) {
		try {
			if(db.isAuthenticated()){
				DBCollection aufgaben = db.getCollection("Aufgaben");
				DBCursor cursor = aufgaben.find(new BasicDBObject().append("_id", save.getID()));
				if(cursor.hasNext()){
					DBObject o = cursor.next();
					
					save.setCreated(getDate(o, DBConection.Created));
					
					BasicDBObject ob = new BasicDBObject();
					ob.append(DBConection.Art, save.getArt());
					ob.append(DBConection.Author, save.getAutor());
					ob.append(DBConection.Kathegorie, save.getKathegorie());
					ob.append(DBConection.Tags, save.getTags());
					ob.append(DBConection.Zugeteilt, save.getZugeteilt());
					ob.append(DBConection.Angabe, save.getAngabe());
					
					ob.append(DBConection.Created, saveDate(save.getCreated()));
					ob.append(DBConection.LastEdit, saveDate(save.getLastedit()));
					ob.append(DBConection.ZueteiltAm, saveDate(save.getZugeteiltam()));
					
					aufgaben.update(o, ob);
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean login(String usr, String pwd) {
		return db.authenticate(usr, pwd.toCharArray());
	}
	@Override
	public void register(String usr, String pwd) {
		db.addUser(usr, pwd.toCharArray());
	}
	
	@Override
	public List<Aufgabe> find(Object id) {
		if(db.isAuthenticated()){
			List<Aufgabe> list = new ArrayList<Aufgabe>();
			if(id instanceof ObjectId){
				DBCollection aufgaben = db.getCollection("Aufgaben");
				DBCursor cursor = aufgaben.find(new BasicDBObject().append("_id", id));
				while(cursor.hasNext()) {
					try{
					DBObject ob = cursor.next();
					list.add(export(ob));
//					System.out.println(ob);
					}catch(ParseException e){}
				}
				cursor.close();
			}
			return list;
		}else{
			//TODO Exception
			return null;
		}
	}
	@Override
	public void remove(Object id) {
		if(db.isAuthenticated()){
			if(id instanceof ObjectId){
				DBCollection aufgaben = db.getCollection("Aufgaben");
				DBCursor cursor = aufgaben.find(new BasicDBObject().append("_id", id));
				while(cursor.hasNext()) {
					
					DBObject ob = cursor.next();
					aufgaben.remove(ob);
//					System.out.println(ob);
				}
				cursor.close();
			}
		}
	}
	private Aufgabe export(DBObject ob) throws ParseException{
		Aufgabe temp = new Aufgabe();
		
		temp.setArt((String)ob.get(DBConection.Art));
		temp.setAutor((String)ob.get(DBConection.Author));
		temp.setKathegorie((String)ob.get(DBConection.Kathegorie));
		temp.setTags((String)ob.get(DBConection.Tags));
		temp.setZugeteilt((String)ob.get(DBConection.Zugeteilt));
		temp.setAngabe((String)ob.get(DBConection.Angabe));
		
		temp.setCreated(getDate(ob, DBConection.Created));
		temp.setLastedit(getDate(ob, DBConection.LastEdit));
		temp.setZugeteiltam(getDate(ob, DBConection.ZueteiltAm));
		temp.setID((ObjectId)ob.get(DBConection.ID));
		
		return temp;
	}
	public List<String> getDB(String suche){
		ArrayList<String> name = new ArrayList<String>();
		DBCollection users = db.getCollection("system.users");
		DBCursor c;
		if(suche == null)
			c = users.find();
		else
			c = users.find(new BasicDBObject("user", java.util.regex.Pattern.compile(suche)));
		for(DBObject o : c){
			name.add((String)o.get("user"));
		}
		return name;
	}
	public void deluser(String name){
		db.removeUser(name);
	}
}
