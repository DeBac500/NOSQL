package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
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
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private void dosomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String url[] = request.getRequestURL().toString().split("/");
		PrintWriter out = response.getWriter();
		if("Aufgaben".equalsIgnoreCase(url[url.length-1])){
			RequestDispatcher rd = request.getRequestDispatcher("/index.html");
			rd.include(request, response);
			String s = request.getParameter("reason");
			if(s != null){
				if(s.equalsIgnoreCase("login")){
					out.println("<div class=\"alert alert-danger alert-dismissable div-test\">");
//					out.println("<p align=\"center\"/>");
					out.println("<span class=\"glyphicon glyphicon-remove\"></span> Name oder Passwort falsch");
					out.println("</div>");
				}
				if(s.equalsIgnoreCase("login1")){
					out.println("<div class=\"alert alert-danger alert-dismissable div-test\">");
//					out.println("<p align=\"center\"/>");
					out.println("<span class=\"glyphicon glyphicon-remove\"></span> Keine Verbindung zur Datenbasis");
					out.println("</div>");
				}
				if(s.equalsIgnoreCase("logout")){
					out.println("<div class=\"alert alert-success div-test\">");
//					out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>");
					out.println("<span class=\"glyphicon glyphicon-ok\"></span> Erfolgreich Ausgeloggt");
//					out.println("<p align=\"center\"/>");
//					out.println("Erfolgreich Ausgeloggt");
					out.println("</div>");
				}
			}
		}else{
			out.println("<h1>ERROR 404: Fehler Falscher URL!</h1>");
		}
		out.close();
	}

}
