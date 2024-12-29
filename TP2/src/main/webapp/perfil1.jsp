<!DOCTYPE html>
<html>
<%@page errorPage="error.jsp" %> 
<%@page import="usr.*, pojo.Cliente, db.ClienteDao"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%User x=Check.login(request, response,1);%> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="pt-PT, en-US">
<meta name="keywords" content="ISEL, DEETC, JSP, Users">
<meta name="description" content="Edição de Utilizadores">
<meta name="owner" content="ISEL/DEETC - Doutor Porfírio Filipe">
<meta name="copyright" content="ISEL/DEETC/2022">
<meta name="createdate" content="20nov2022">
<meta name="lastupdate" content="11dec2023">
<meta http-equiv="Pragma" content="no-cache">
<title>Example</title>
<style>
@font-face {
    font-family: ChristmasFont;
    src: url(fonts/MountainsofChristmas-Regular.ttf);
}
p{
	font-size: 2em;
	font-family: 'ChristmasFont', serif;
	margin: 5px;
}
</style>
</head>
<body>
<h2>(<%=x.getProfile()%>) <%=x.welcome()%></h2>
<br/>
<p>
Best wishes for this exciting new job! We'll always remember you as a great boss.
</p>
<br>
<p style="font-family:verdana">
Cliente<br/>
1 - Reservar veículo, seleciona tipo ou modelo, o parque de levantamento e o período do aluguer. Após validação, apresenta o custo final previsto para o aluguer, expresso na moeda de preferência do cliente, ao qual pode ser aplicado um desconto indicando o respetivo código.<br/>
2 - Consultar estado das reservas, avaliações, custo final previsto e efetivo expresso na moeda de preferência.<br/>
3 - Consultar reputação e descontos.<br/>
</p>
<br/>

<!-- Add this after the welcome message -->
<%
if(x!=null){
ClienteDao clienteDao = new ClienteDao();
Cliente cliente = clienteDao.getById(x.getNif());

if (cliente != null) {
    double reputacao = cliente.getAvaliacaoCliente();
    
    String reputacaoMd = String.format("# Sua Reputação\n\n" +
                                      "**Avaliação**: %.1f/5.0\n\n" +
                                      "*Esta avaliação é baseada no histórico de alugueres*", 
                                      reputacao);
%>
    <div style="font-family: verdana; white-space: pre-wrap;">
        <%= reputacaoMd %>
    </div>
<%
} else {
%>
    <div style="font-family: verdana">
        # Cliente não encontrado
        
        *Não foi possível encontrar suas informações de cliente.*
    </div>
<%
}
}
%>
<input title="Go back" type="button" value="Back" onClick="javascript:window.history.back()"/>
</body>
</html>