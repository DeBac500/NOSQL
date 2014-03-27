package Main_View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import Data.Aufgabe;
import Data.DBConection;
import Data.DataHandler;

/**
 * Servlet implementation class Main_View
 */
public class Main_View extends HttpServlet {
	private DataHandler db;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main_View() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.dosomething(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.dosomething(request, response);
	}
	private void dosomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try{
			String url[] = request.getRequestURL().toString().split("/");
			PrintWriter out = response.getWriter();
			if("main".equalsIgnoreCase(url[url.length-1])){
				String s = request.getParameter("neu");
				if(s != null){
					response.setStatus(response.SC_MOVED_TEMPORARILY);
					response.setHeader("Location", "/NOSQL/Aufgaben?reason=logout");
				}else{
					String save = request.getParameter("save");
					String name = request.getParameter("name");
					String pwd = request.getParameter("pwd");
					if(name != null && pwd != null){
	//					Properties prop = new Properties();
	//					File f = new File("WebContent/config.properties");
	//					out.println("Test");
	//					out.println(f.getAbsolutePath());
	//					if(f.exists()){
	//					FileInputStream fis = new FileInputStream(f);
	//					prop.load(fis);
	//					out.println(prop.getProperty("ip"));
	//					out.println(prop.getProperty("port"));
	//					out.println(prop.getProperty("dbname"));
	//					db=new DBConection(prop.getProperty("ip"),Integer.parseInt(prop.getProperty("port")), prop.getProperty("dbname"));
						db = new DBConection("10.0.105.68", "TESTDB");
						db.register("dominik", "dominik");
						if(db.login(name, pwd)){
							this.setupMain(out, request, response);
						}else{
							response.setStatus(response.SC_MOVED_TEMPORARILY);
							response.setHeader("Location", "/NOSQL/Aufgaben?reason=login");
						}
					}else if(save!=null){
						this.setupMain(out, request, response);
					}else{
						response.setStatus(response.SC_MOVED_TEMPORARILY);
						response.setHeader("Location", "/NOSQL/Aufgaben?reason=login1");
					}
				}
			}else if("edit".equalsIgnoreCase(url[url.length-1])){
				try{
				int inc = Integer.parseInt(request.getParameter("inc"));
				int time = Integer.parseInt(request.getParameter("time"));
				int machine = Integer.parseInt(request.getParameter("machine"));
				ObjectId id = new ObjectId(time, machine, inc);
				List<Aufgabe> list = db.find(id);
				System.out.println(list);
				RequestDispatcher rd = request.getRequestDispatcher("/edit.jsp");
				rd.include(request, response);
				setupEdit(out, list.get(0));
				}catch(NumberFormatException e){
					RequestDispatcher rd = request.getRequestDispatcher("/edit.jsp");
					rd.include(request, response);
					setupEdit(out, null);
				}
			}else if("save".equalsIgnoreCase(url[url.length-1])){
				SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String Art = request.getParameter("Art");
				String Kathegorie = request.getParameter("Kathegorie");
				String Tags = request.getParameter("Tags");
				String Author = request.getParameter("Author");
				String Zugeteilt = request.getParameter("Author");
				String inc = request.getParameter("inc");
				String machine = request.getParameter("machine");
				String time = request.getParameter("time");
				String aufgaben = request.getParameter("aufgabe");
				
				String zuamyyyy = request.getParameter("zuamyyyy");
				String zuamMM = request.getParameter("zuamMM");
				String zuamdd = request.getParameter("zuamdd");
				String zuamHH = request.getParameter("zuamHH");
				String zuammm = request.getParameter("zuammm");
				
				Aufgabe auf = new Aufgabe();
				auf.setArt(Art);
				auf.setAutor(Author);
				auf.setLastedit(new Date());
				auf.setKathegorie(Kathegorie);
				auf.setTags(Tags);
				auf.setZugeteilt(Zugeteilt);
				auf.setAngabe(aufgaben);
				auf.setZugeteiltam(simp.parse(zuamyyyy+"-"+zuamMM+"-"+zuamdd+" " +zuamHH + ":" + zuammm));
				
				if(inc != null && machine != null && time != null){
					ObjectId id = new ObjectId(Integer.parseInt(time),Integer.parseInt(machine),Integer.parseInt(inc));
					auf.setID(id);
					db.edit(auf);
				}else{
					auf.setCreated(new Date());
					db.save(auf);
				}
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/NOSQL/Aufgaben/main?save=fin");
			}else{
				out.println("<h1>ERROR 404: Fehler Falscher URL!</h1>");
			}
			out.close();
		}catch(NumberFormatException e){
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "/NOSQL/Aufgaben?reason=faild");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setupEdit(PrintWriter out, Aufgabe auf){
		if(auf != null){
			SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat simpyy = new SimpleDateFormat("yyyy");
			SimpleDateFormat simpmm = new SimpleDateFormat("MM");
			SimpleDateFormat simpdd = new SimpleDateFormat("dd");
			SimpleDateFormat simphh = new SimpleDateFormat("HH");
			SimpleDateFormat simpm = new SimpleDateFormat("mm");
			out.println("<form action=\"edit/save\" method=\"post\">");
			out.println("<table border=\"0\" align=\"center\">");
			out.println("<tr><td>Art:</td><td align=\"center\"><input name=\"Art\" type=\"text\" value=\""+auf.getArt()+"\"></td></tr>");
			out.println("<tr><td>Kathegorie:</td><td align=\"center\"><input name=\"Kathegorie\" type=\"text\" value=\""+auf.getKathegorie()+"\"></td></tr>");
			out.println("<tr><td>Tags:</td><td align=\"center\"><input name=\"Tags\" type=\"text\" value=\""+auf.getTags()+"\"></td></tr>");
			out.println("<tr><td>Author:</td><td align=\"center\"><input name=\"Author\" type=\"text\" value=\""+auf.getAutor()+"\"></td></tr>");
			out.println("<tr><td>Zugeteilt:</td><td align=\"center\"><input name=\"Zugeteilt\" type=\"text\" value=\""+auf.getZugeteilt()+"\"></td></tr>");
			out.println("<tr><td>Zugeteilt am:</td><td align=\"center\"><input name=\"zuamyyyy\" type=\"text\" maxlength=4 size=3 value=\""+simpyy.format(auf.getZugeteiltam())+"\">-<input name=\"zuamMM\" type=\"text\" size=2 maxlength=2 value=\""+simpmm.format(auf.getZugeteiltam())+"\">-<input name=\"zuamdd\" type=\"text\" size=2 maxlength=2 value=\""+simpdd.format(auf.getZugeteiltam())+"\"> <input name=\"zuamHH\" type=\"text\" size=2 maxlength=2 value=\""+simphh.format(auf.getZugeteiltam())+"\">:<input name=\"zuammm\" type=\"text\" size=2 maxlength=2 value=\""+simpm.format(auf.getZugeteiltam())+"\"></td></tr>");
			out.println("<tr><td colspan=\"2\" align=\"center\">Aufgabe:</td></tr>");
			out.println("<tr><td colspan=\"2\" align=\"center\"><textarea name=\"aufgabe\">"+auf.getAngabe()+"</textarea></td></tr>");
			out.println("<tr><td>Erstellt:</td><td>"+simp.format(auf.getCreated())+"</td></tr>");
			out.println("<tr><td>Zuletzt Bearbeited:</td><td>"+simp.format(auf.getLastedit())+"</td></tr>");
			out.println("<tr><td align=\"center\"><a href=\"/NOSQL/Aufgaben/main?save=fin\">Zur&uuml;ck</a></td><td align=\"center\"><input type=\"submit\" value=\"Speichern\"></td></tr>");
			out.println("</table>");
			out.println("<input type=\"hidden\" name=\"inc\" value=\""+auf.getID()._inc()+"\">");
			out.println("<input type=\"hidden\" name=\"machine\" value=\""+auf.getID()._machine()+"\">");
			out.println("<input type=\"hidden\" name=\"time\" value=\""+auf.getID()._time()+"\">");
			out.println("</form>");
		}else{
			out.println("<form action=\"edit/save\" method=\"post\">");
			out.println("<table border=\"0\" align=\"center\">");
			out.println("<tr><td>Art:</td><td align=\"center\"><input name=\"Art\" type=\"text\" ></td></tr>");
			out.println("<tr><td>Kathegorie:</td><td align=\"center\"><input name=\"Kathegorie\" type=\"text\" ></td></tr>");
			out.println("<tr><td>Tags:</td><td align=\"center\"><input name=\"Tags\" type=\"text\" ></td></tr>");
			out.println("<tr><td>Author:</td><td align=\"center\"><input name=\"Author\" type=\"text\" ></td></tr>");
			out.println("<tr><td>Zugeteilt:</td><td align=\"center\"><input name=\"Zugeteilt\" type=\"text\" ></td></tr>");
			out.println("<tr><td>Zugeteilt am:</td><td align=\"center\"><input name=\"zuamyyyy\" type=\"text\" maxlength=4 size=3 >-<input name=\"zuamMM\" type=\"text\" size=2 maxlength=2 >-<input name=\"zuamdd\" type=\"text\" size=2 maxlength=2 > <input name=\"zuamHH\" type=\"text\" size=2 maxlength=2 >:<input name=\"zuammm\" type=\"text\" size=2 maxlength=2 ></td></tr>");
			out.println("<tr><td colspan=\"2\" align=\"center\">Aufgabe:</td></tr>");
			out.println("<tr><td colspan=\"2\" align=\"center\"><textarea name=\"aufgabe\"></textarea></td></tr>");
			out.println("<tr><td align=\"center\"><a href=\"/NOSQL/Aufgaben/main?save=fin\">Zur&uuml;ck</a></td><td align=\"center\"><input type=\"submit\" value=\"Speichern\"></td></tr>");
			out.println("</table>");
			out.println("</form>");
		}
	}
	public void setupMain(PrintWriter out, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher("/main.jsp");
		rd.include(request, response);
		out.println("<table border=\"1\" align=\"center\">");
		out.println("<tr><th>Art</th><th>Kathegorie</th><th>Tags</th><th>Autor</th><th>Zugeteilt</th><th>Zugeteilt am</th><th>Erstellt</th><th>Zuletzt ge&auml;ndert</th><th><a href=\"/NOSQL/Aufgaben/main/edit\">Neue Aufgabe</a></th></tr>");
		List<Aufgabe> list = db.getAll();
		SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(Aufgabe temp: list){
			out.println("<tr>");
			out.println("<td>"+temp.getArt()+"</td>");
			out.println("<td>"+temp.getKathegorie()+"</td>");
			out.println("<td>"+temp.getTags()+"</td>");
			out.println("<td>"+temp.getAutor()+"</td>");
			out.println("<td>"+temp.getZugeteilt()+"</td>");
			out.println("<td>"+simp.format(temp.getZugeteiltam())+"</td>");
			out.println("<td>"+simp.format(temp.getCreated())+"</td>");
			out.println("<td>"+simp.format(temp.getLastedit())+"</td>");
			out.println("<td><a href=\"/NOSQL/Aufgaben/main/edit?inc="+temp.getID()._inc()+"&machine="+temp.getID()._machine()+"&time="+temp.getID()._time()+"\">bearbeiten</a></td>");
			out.println("</tr>");
		}
		out.println("</table>");
	}
}
