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
	
	private void dosomething(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String url[] = request.getRequestURL().toString().split("/");
		PrintWriter out = response.getWriter();
		if("Aufgaben".equalsIgnoreCase(url[url.length-1])){
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.include(request, response);
			String s = request.getParameter("reason");
			if(s != null){
				if(s.equalsIgnoreCase("login")){
					out.println("<p align=\"center\" />");
					out.println("<font color=\"#FF0000\"> Name oder Passwort Falsch!</font>");
				}
				if(s.equalsIgnoreCase("login1")){
					out.println("<p align=\"center\" color=\"#FF0000\"/>");
					out.println("<font color=\"#FF0000\">Keine Verbindung zur Datenbasis</font>");
				}
				if(s.equalsIgnoreCase("logout")){
					out.println("<p align=\"center\"/>");
					out.println("Erfolgreich Ausgeloggt");
				}
			}
		}else{
			out.println("<h1>ERROR 404: Fehler Falscher URL!</h1>");
		}
		out.close();
	}

}
