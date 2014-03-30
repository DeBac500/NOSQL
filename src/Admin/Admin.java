package Admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Data.DBConection;
import Main_View.Main_View;

/**
 * Servlet implementation class Admin
 */
public class Admin extends HttpServlet {
	private static String adm = "aufgabenadmin";
	private static final long serialVersionUID = 1L;
	private boolean bool = false;
	private DBConection db;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.does(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.does(request, response);
	}

	public void does(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String url[] = request.getRequestURL().toString().split("/");
		PrintWriter out = response.getWriter();
		
		if("admin".equalsIgnoreCase(url[url.length-1])){
			String name = request.getParameter("adname");
			String pwd = request.getParameter("adpwd");
			String neu = request.getParameter("neu");
			String dname = request.getParameter("dname");
			String nname = request.getParameter("nanme");
			String npwd = request.getParameter("npwd");
			String suche = request.getParameter("suche");
			if(nname != null && npwd != null){
				db.register(nname, npwd);
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/NOSQL/Aufgaben/admin");
			}
			if(dname != null){
				db.deluser(dname);
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/NOSQL/Aufgaben/admin");
			}
			if(neu != null){
				Cookie m = getCookie(request);
				if(m != null){
				m.setValue("Nein");
				m.setMaxAge(0);
				response.addCookie(m);
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/NOSQL/Aufgaben");
				}
			}
			if(name == null && pwd == null){
				Cookie m = getCookie(request);
				if(m != null){
					if(m.getValue().equalsIgnoreCase("JA")){
						bool = true;
					}
				}
				if(bool){
					this.setupmain(suche,out,request, response);
				}else{
					RequestDispatcher rd = request.getRequestDispatcher("/admin.html");
					rd.include(request, response);
				}
			}else{
				List<String> nn = new ArrayList<String>();
				List<String> pw = new ArrayList<String>();
				nn.add("admin");
				pw.add("admin");
				nn.add("dominik");
				pw.add("dominik");
				if(nn.contains(name) && pw.contains(pwd)){
					Cookie c = new Cookie(Admin.adm,"JA");
					c.setMaxAge(3600/4);
					response.addCookie(c);
					this.setupmain(suche,out,request, response);
				}else{
					response.setStatus(response.SC_MOVED_TEMPORARILY);
					response.setHeader("Location", "/NOSQL/Aufgaben/admin");
				}
			}
		}
		out.close();
	}
	public void setupmain(String suche,PrintWriter out, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher("/adminmain.html");
		rd.include(request, response);
		if(suche != null){
			out.println("<p align=\"center\"/>");
			out.println("<a href=\"/NOSQL/Aufgaben/admin\">Zur&uuml;ck</a></br>");
		}
		out.println("<table class=\"table\">");
		out.println("<thead><tr><th>Name</th><th></th></tr></thead><tbody>");
		Properties prop = new Properties();
		InputStream fis = Main_View.class.getResourceAsStream("config.properties");
		prop.load(fis);
//		out.println(prop.getProperty("ip"));
//		out.println(prop.getProperty("port"));
//		out.println(prop.getProperty("dbname"));
		db = new DBConection(prop.getProperty("ip"),
				Integer.parseInt(prop.getProperty("port")),
				prop.getProperty("dbname"));
		List<String> name = db.getDB(suche);
		for(String n : name){
			out.println("<tr><td>"+n+"</td><td><a href=\"/NOSQL/Aufgaben/admin?dname="+n+"\"><span class=\"glyphicon glyphicon-remove\"></span></a></td></tr>");
		}
		out.println("</tbody></table>");
	}
	public Cookie getCookie(HttpServletRequest request){
		Cookie[] co = request.getCookies();
		if(co != null){
		for(Cookie temp : co){
			if(temp.getName().equals(Admin.adm)){
				return temp;
			}
		}
		}
		return null;
	}
}
