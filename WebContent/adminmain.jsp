<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Aufgaben</title>
</head>
<body>
<p align="right"/>
<a href="/NOSQL/Aufgaben/admin?neu=neu">Logout</a></br>
<p align="center"/>
<font>User</font>
<form action="/NOSQL/Aufgaben/admin" method="post">
<table align="center"><tr>
<td><input name="suche" type="text"></td><td><input type="submit" value="Suche"></td>
</table>
</form>
<form action="/NOSQL/Aufgaben/admin" method="post">
<table align="center"><tr>
<td><input name="nanme" type="text"></td><td><input name="npwd" type="password"></td><td><input type="submit" value="Neuer User"></td>
</table>
</form>
</body>
</html>