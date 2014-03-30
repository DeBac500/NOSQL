package Main_View;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author dominik backhausen, alexander rieppel
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.dosomething(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.dosomething(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private void dosomething(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String url[] = request.getRequestURL().toString().split("/");
			PrintWriter out = response.getWriter();
			if ("main".equalsIgnoreCase(url[url.length - 1])) {
				String s = request.getParameter("neu");
				if (s != null) {
					response.setStatus(response.SC_MOVED_TEMPORARILY);
					response.setHeader("Location",
							"/NOSQL/Aufgaben?reason=logout");
				} else {
					String save = request.getParameter("save");
					String name = request.getParameter("name");
					String pwd = request.getParameter("pwd");
					if (name != null && pwd != null) {
						Properties prop = new Properties();
						InputStream fis = getClass().getResourceAsStream(
								"config.properties");
						prop.load(fis);
//						out.println(prop.getProperty("ip"));
//						out.println(prop.getProperty("port"));
//						out.println(prop.getProperty("dbname"));
						db = new DBConection(prop.getProperty("ip"),
								Integer.parseInt(prop.getProperty("port")),
								prop.getProperty("dbname"));
						// db = new DBConection("80.108.227.111", "TESTDB");
						// db.register("dominik", "dominik");
						if (db.login(name, pwd)) {
							this.setupMain(out, request, response);
						} else {
							response.setStatus(response.SC_MOVED_TEMPORARILY);
							response.setHeader("Location",
									"/NOSQL/Aufgaben?reason=login");
						}
					} else {
						if (db == null) {
							response.setStatus(response.SC_MOVED_TEMPORARILY);
							response.setHeader("Location",
									"/NOSQL/Aufgaben?reason=login1");
						}
						if (save != null) {
							if (db != null) {
								this.setupMain(out, request, response);
							} else {
								response.setStatus(response.SC_MOVED_TEMPORARILY);
								response.setHeader("Location",
										"/NOSQL/Aufgaben?reason=login1");
							}
						} else {
							response.setStatus(response.SC_MOVED_TEMPORARILY);
							response.setHeader("Location",
									"/NOSQL/Aufgaben?reason=login1");
						}
					}
				}
			} else {
				if (db == null) {
					response.setStatus(response.SC_MOVED_TEMPORARILY);
					response.setHeader("Location",
							"/NOSQL/Aufgaben?reason=login1");
				}
				if ("edit".equalsIgnoreCase(url[url.length - 1])) {
					try {
						int inc = Integer.parseInt(request.getParameter("inc"));
						int time = Integer.parseInt(request
								.getParameter("time"));
						int machine = Integer.parseInt(request
								.getParameter("machine"));
						ObjectId id = new ObjectId(time, machine, inc);
						if (db == null) {
							response.setStatus(response.SC_MOVED_TEMPORARILY);
							response.setHeader("Location",
									"/NOSQL/Aufgaben?reason=login1");
						}
						List<Aufgabe> list = db.find(id);
						System.out.println(list);
						setupEdit(out, list.get(0));
					} catch (NumberFormatException e) {
						setupEdit(out, null);
					}
				} else if ("save".equalsIgnoreCase(url[url.length - 1])) {
					SimpleDateFormat simp = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
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
					auf.setZugeteiltam(simp.parse(zuamyyyy + "-" + zuamMM + "-"
							+ zuamdd + " " + zuamHH + ":" + zuammm));
					if (inc != null && machine != null && time != null) {
						ObjectId id = new ObjectId(Integer.parseInt(time),
								Integer.parseInt(machine),
								Integer.parseInt(inc));
						auf.setID(id);
						if (db == null) {
							response.setStatus(response.SC_MOVED_TEMPORARILY);
							response.setHeader("Location",
									"/NOSQL/Aufgaben?reason=login1");
						}
						db.edit(auf);
					} else {
						auf.setCreated(new Date());
						if (db == null) {
							response.setStatus(response.SC_MOVED_TEMPORARILY);
							response.setHeader("Location",
									"/NOSQL/Aufgaben?reason=login1");
						}
						db.save(auf);
					}
					response.setStatus(response.SC_MOVED_TEMPORARILY);
					response.setHeader("Location",
							"/NOSQL/Aufgaben/main?save=fin");
				} else if ("suche".equalsIgnoreCase(url[url.length - 1])) {
					String suche = request.getParameter("suche");
					if (suche == null) {
						suche = request.getParameter("suche1");
						if (suche != null) {
							try {
								SimpleDateFormat simp = new SimpleDateFormat(
										"yyyy-MM-dd");
								if (db == null) {
									response.setStatus(response.SC_MOVED_TEMPORARILY);
									response.setHeader("Location",
											"/NOSQL/Aufgaben?reason=login1");
								}
								List<Aufgabe> list = db.find(simp.parse(suche));
								this.setUPSuche(list, out, request, response);
							} catch (ParseException e) {
								List<Aufgabe> list = new ArrayList<Aufgabe>();
								this.setUPSuche(list, out, request, response);
							}
						}
					} else {
						if (db == null) {
							response.setStatus(response.SC_MOVED_TEMPORARILY);
							response.setHeader("Location",
									"/NOSQL/Aufgaben?reason=login1");
						}
						List<Aufgabe> list = db.find(suche);
						this.setUPSuche(list, out, request, response);
					}
				} else if ("remove".equalsIgnoreCase(url[url.length - 1])) {
					try {
						int inc = Integer.parseInt(request.getParameter("inc"));
						int time = Integer.parseInt(request
								.getParameter("time"));
						int machine = Integer.parseInt(request
								.getParameter("machine"));
						ObjectId id = new ObjectId(time, machine, inc);
						if (db == null) {
							response.setStatus(response.SC_MOVED_TEMPORARILY);
							response.setHeader("Location",
									"/NOSQL/Aufgaben?reason=login1");
						}
						db.remove(id);
					} catch (NumberFormatException e) {
					}
					response.setStatus(response.SC_MOVED_TEMPORARILY);
					response.setHeader("Location",
							"/NOSQL/Aufgaben/main?save=fin");
				} else {
					out.println("<h1>ERROR 404: Fehler Falscher URL!</h1>");
				}
				out.close();
			}
		} catch (NumberFormatException e) {
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "/NOSQL/Aufgaben?reason=faild");
		} catch (ParseException e) {
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "/NOSQL/Aufgaben?reason=login1");
		} catch (NullPointerException e) {
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "/NOSQL/Aufgaben?reason=login1");
		}
	}
	/**
	 * Erstellt die Bearbeiten/Erstellen Seite
	 * @param out
	 * @param auf
	 */
	private void setupEdit(PrintWriter out, Aufgabe auf) {
		out.println("<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				+ "<head>"
				+ "<meta charset=\"utf-8\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "<meta name=\"description\" content=\"\">"
				+ "<meta name=\"author\" content=\"\">"
				+ "<link rel=\"shortcut icon\" href=\"/NOSQL/assets/ico/favicon.png\">"
				+ "<title>Bearbeiten/Erstellen</title>");
		out.println("<link href=\"/NOSQL/dist/css/bootstrap.css\" rel=\"stylesheet\">");
		out.println("<link href=\"/NOSQL/new.css\" rel=\"stylesheet\">");
		out.println("</head><body>");
		if (auf != null) {
			SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat simpyy = new SimpleDateFormat("yyyy");
			SimpleDateFormat simpmm = new SimpleDateFormat("MM");
			SimpleDateFormat simpdd = new SimpleDateFormat("dd");
			SimpleDateFormat simphh = new SimpleDateFormat("HH");
			SimpleDateFormat simpm = new SimpleDateFormat("mm");
			out.println("<h3><p align=\"center\"/>Bearbeiten</h3>");
			out.println("<form action=\"edit/save\" method=\"post\">");
			out.println("<table class=\"tab\" border=\"0\">");
			out.println("<tr><td>Art:</td><td colspan=\"9\"><input name=\"Art\" type=\"text\" class=\"form-control\" placeholder=\"Art\" value=\""
					+ auf.getArt() + "\"></td></tr>");
			out.println("<tr><td>Kathegorie:</td><td colspan=\"9\"><input name=\"Kathegorie\" type=\"text\" class=\"form-control\" placeholder=\"Kathegorie\" value=\""
					+ auf.getKathegorie() + "\"></td></tr>");
			out.println("<tr><td>Tags:</td><td colspan=\"9\"><input name=\"Tags\" type=\"text\" class=\"form-control\" placeholder=\"Tags\" value=\""
					+ auf.getTags() + "\"></td></tr>");
			out.println("<tr><td>Autor:</td><td colspan=\"9\"><input name=\"Author\" type=\"text\" class=\"form-control\" placeholder=\"Autor\" value=\""
					+ auf.getAutor() + "\"></td></tr>");
			out.println("<tr><td>Zugeteilt:</td><td colspan=\"9\"><input name=\"Zugeteilt\" type=\"text\" class=\"form-control\" placeholder=\"Zugeteilt\" value=\""
					+ auf.getZugeteilt() + "\"></td></tr>");
			out.println("<tr><td>Zugeteilt am:</td><td><input name=\"zuamyyyy\" type=\"text\" maxlength=4 size=3 class=\"form-control\" placeholder=\"yyyy\" value=\""
					+ simpyy.format(auf.getZugeteiltam())
					+ "\"></td><td>-</td><td><input name=\"zuamMM\" type=\"text\" size=2 maxlength=2 class=\"form-control\" placeholder=\"mm\" value=\""
					+ simpmm.format(auf.getZugeteiltam())
					+ "\"></td><td>-</td><td><input name=\"zuamdd\" type=\"text\" size=2 maxlength=2 class=\"form-control\" placeholder=\"dd\" value=\""
					+ simpdd.format(auf.getZugeteiltam())
					+ "\"></td><td> </td><td><input name=\"zuamHH\" type=\"text\" size=2 maxlength=2 class=\"form-control\" placeholder=\"hh\" value=\""
					+ simphh.format(auf.getZugeteiltam())
					+ "\"></td><td>:</td><td><input name=\"zuammm\" type=\"text\" size=2 maxlength=2 class=\"form-control\" placeholder=\"mm\" value=\""
					+ simpm.format(auf.getZugeteiltam()) + "\"></td></tr>");
			out.println("</br><tr><td colspan=\"10\" align=\"center\">Aufgabe:</td></tr>");
			out.println("<tr><td colspan=\"10\" align=\"center\"><textarea name=\"aufgabe\" class=\"form-control\">"
					+ auf.getAngabe() + "</textarea></td></tr>");
			out.println("<tr><td colspan=\"4\" align=\"right\">Erstellt:</td><td></td><td colspan=\"5\" align=\"left\">"
					+ simp.format(auf.getCreated()) + "</td></tr>");
			out.println("<tr><td colspan=\"4\" align=\"right\">Zuletzt Bearbeitet:</td><td></td><td colspan=\"5\" align=\"left\">"
					+ simp.format(auf.getLastedit()) + "</td></tr>");
			out.println("<tr><td colspan=\"4\" align=\"right\"><button type=button class=\"btn\" onclick=\"window.location.href='/NOSQL/Aufgaben/main?save=fin'\">Zur&uuml;ck</button></td><td></td><td align=\"left\" colspan=\"5\"><button class=\"btn\" type=\"submit\">Speichern</button></td></tr>");
			out.println("</table>");
			out.println("<input type=\"hidden\" name=\"inc\" value=\""
					+ auf.getID()._inc() + "\">");
			out.println("<input type=\"hidden\" name=\"machine\" value=\""
					+ auf.getID()._machine() + "\">");
			out.println("<input type=\"hidden\" name=\"time\" value=\""
					+ auf.getID()._time() + "\">");
			out.println("</form>");
		} else {
			out.println("<h3><p align=\"center\"/>Bearbeiten</h3>");
			out.println("<form action=\"edit/save\" method=\"post\">");
			out.println("<table class=\"tab\" border=\"0\">");
			out.println("<tr><td>Art:</td><td colspan=\"9\"><input name=\"Art\" type=\"text\" class=\"form-control\" placeholder=\"Art\"></td></tr>");
			out.println("<tr><td>Kathegorie:</td><td colspan=\"9\"><input name=\"Kathegorie\" type=\"text\" class=\"form-control\" placeholder=\"Kathegorie\" ></td></tr>");
			out.println("<tr><td>Tags:</td><td colspan=\"9\"><input name=\"Tags\" type=\"text\" class=\"form-control\" placeholder=\"Tags\"></td></tr>");
			out.println("<tr><td>Autor:</td><td colspan=\"9\"><input name=\"Author\" type=\"text\" class=\"form-control\" placeholder=\"Autor\" ></td></tr>");
			out.println("<tr><td>Zugeteilt:</td><td colspan=\"9\"><input name=\"Zugeteilt\" type=\"text\" class=\"form-control\" placeholder=\"Zugeteilt\"></td></tr>");
			out.println("<tr><td>Zugeteilt am:</td><td><input name=\"zuamyyyy\" type=\"text\" maxlength=4 size=3 class=\"form-control\" placeholder=\"yyyy\"></td><td>-</td><td><input name=\"zuamMM\" type=\"text\" size=2 maxlength=2 class=\"form-control\" placeholder=\"mm\" ></td><td>-</td><td><input name=\"zuamdd\" type=\"text\" size=2 maxlength=2 class=\"form-control\" placeholder=\"dd\"></td><td> </td><td><input name=\"zuamHH\" type=\"text\" size=2 maxlength=2 class=\"form-control\" placeholder=\"hh\" ></td><td>:</td><td><input name=\"zuammm\" type=\"text\" size=2 maxlength=2 class=\"form-control\" placeholder=\"mm\" ></td></tr>");
			out.println("</br><tr><td colspan=\"10\" align=\"center\">Aufgabe:</td></tr>");
			out.println("<tr><td colspan=\"10\" align=\"center\"><textarea name=\"aufgabe\" class=\"form-control\"></textarea></td></tr>");
			out.println("<tr><td colspan=\"4\" align=\"right\"><button type=button class=\"btn\" onclick=\"window.location.href='/NOSQL/Aufgaben/main?save=fin'\">Zur&uuml;ck</button></td><td></td><td align=\"left\" colspan=\"5\"><button class=\"btn\" type=\"submit\">Speichern</button></td></tr>");
			out.println("</table>");
			out.println("</form>");
		}
	}
	/**
	 * Erstellt die Main Seite
	 * @param out
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void setupMain(PrintWriter out, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/nav.html");
		rd.include(request, response);
		out.println("<table class=\"table\">");
		out.println("<thead><tr><th>Art</th><th>Kathegorie</th><th>Tags</th><th>Autor</th><th>Zugeteilt</th><th>Zugeteilt am</th><th>Erstellt</th><th>Zuletzt ge&auml;ndert</th><th><a href=\"/NOSQL/Aufgaben/main/edit\"><span class=\"glyphicon glyphicon-plus\"></span></a></th><th></th></tr></thead>");
		out.println("<tbody>");
		List<Aufgabe> list = db.getAll();
		Collections.sort(list);
		SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (Aufgabe temp : list) {
			out.println("<tr>");
			out.println("<td>" + temp.getArt() + "</td>");
			out.println("<td>" + temp.getKathegorie() + "</td>");
			out.println("<td>" + temp.getTags() + "</td>");
			out.println("<td>" + temp.getAutor() + "</td>");
			out.println("<td>" + temp.getZugeteilt() + "</td>");
			out.println("<td>" + simp.format(temp.getZugeteiltam()) + "</td>");
			out.println("<td>" + simp.format(temp.getCreated()) + "</td>");
			out.println("<td>" + simp.format(temp.getLastedit()) + "</td>");
			out.println("<td><a href=\"/NOSQL/Aufgaben/main/edit?inc="
					+ temp.getID()._inc()
					+ "&machine="
					+ temp.getID()._machine()
					+ "&time="
					+ temp.getID()._time()
					+ "\"><span class=\"glyphicon glyphicon-pencil\"></span></a></td><td><a href=/NOSQL/Aufgaben/main/remove?inc="
					+ temp.getID()._inc()
					+ "&machine="
					+ temp.getID()._machine()
					+ "&time="
					+ temp.getID()._time()
					+ "><span class=\"glyphicon glyphicon-remove\"></span></a></td>");
			out.println("</tr>");
		}
		out.println("</tbody></table>");
	}
	/**
	 * Erstellt die SuchenSeite
	 * @param list
	 * @param out
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void setUPSuche(List<Aufgabe> list, PrintWriter out,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/nav.html");
		rd.include(request, response);
		out.println("<p align=\"center\"/>");
		out.println("<button type=button class=\"btn\" onclick=\"window.location.href='/NOSQL/Aufgaben/main?save=fin'\">Zur&uuml;cksetzen</button></br>");
		out.println("<table class=\"table\">");
		out.println("<thead><tr><th>Art</th><th>Kathegorie</th><th>Tags</th><th>Autor</th><th>Zugeteilt</th><th>Zugeteilt am</th><th>Erstellt</th><th>Zuletzt ge&auml;ndert</th><th><a href=\"/NOSQL/Aufgaben/main/edit\"><span class=\"glyphicon glyphicon-plus\"></span></a></th><th></th></tr></thead>");
		out.println("<tbody>");
		Collections.sort(list);
		SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (Aufgabe temp : list) {
			out.println("<tr>");
			out.println("<td>" + temp.getArt() + "</td>");
			out.println("<td>" + temp.getKathegorie() + "</td>");
			out.println("<td>" + temp.getTags() + "</td>");
			out.println("<td>" + temp.getAutor() + "</td>");
			out.println("<td>" + temp.getZugeteilt() + "</td>");
			out.println("<td>" + simp.format(temp.getZugeteiltam()) + "</td>");
			out.println("<td>" + simp.format(temp.getCreated()) + "</td>");
			out.println("<td>" + simp.format(temp.getLastedit()) + "</td>");
			out.println("<td><a href=\"/NOSQL/Aufgaben/main/edit?inc="
					+ temp.getID()._inc()
					+ "&machine="
					+ temp.getID()._machine()
					+ "&time="
					+ temp.getID()._time()
					+ "\"><span class=\"glyphicon glyphicon-pencil\"></span></a></td><td><a href=/NOSQL/Aufgaben/main/remove?inc="
					+ temp.getID()._inc()
					+ "&machine="
					+ temp.getID()._machine()
					+ "&time="
					+ temp.getID()._time()
					+ "><span class=\"glyphicon glyphicon-remove\"></span></a></td>");
			out.println("</tr>");
		}
		out.println("</tbody></table>");
	}
}
