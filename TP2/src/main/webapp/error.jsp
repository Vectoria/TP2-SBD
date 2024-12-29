<%@page isErrorPage = "true" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="pt-PT, en-US">
<title>Página de erro</title>
</head>
<body>
 <h1> Ocorreu um erro inesperado! </h1>
 <b>Exception:</b><br> 
<%= exception.toString() %>
</body>
</html>