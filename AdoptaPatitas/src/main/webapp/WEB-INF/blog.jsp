<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Blog</title>
	</head>
	<body>
		<h1>aqui ira el blog con los articulos</h1>
		<form action="/logout" method="POST">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="Cerrar Sesi�n" class="btn btn-dark"/>
		</form>
	</body>
</html>