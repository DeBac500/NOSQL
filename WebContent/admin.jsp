<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Admin</title>
</head>
<body>
<h1 align="center">Admin Login:</h1>
<form method="post">
	<table border="1" align="center">
  		<tr>
    		<td>
    			Benutzername:
    		</td>
    		<td>
    			<input name="adname" type="text">
    		</td>
  		</tr>
  		<tr>
    		<td>
    			Passwort:
    		</td>
    			<td >
    			<input name="adpwd" type="password">
    			</td>
  		</tr>
  		<tr>
  			<td colspan="2" align="center">
    			<input type="submit" value="Login">
    		</td>
  		</tr>
	</table>
</form>
</body>
</html>