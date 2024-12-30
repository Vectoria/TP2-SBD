<!DOCTYPE html>
<html>
<%@ page import="one.*, java.time.LocalDate"%>
<%@ page import="pojo.*, java.time.LocalDate"%>
<%@ page import="db.*, java.time.LocalDate"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="pt-PT, en-US">
<meta name="keywords" content="ISEL, DEETC, JSP, Coffee">
<meta name="description" content="Manipulação de dados">
<meta name="owner" content="ISEL/DEETC - Doutor Porfírio Filipe">
<meta name="copyright" content="ISEL/DEETC/2022">
<meta name="createdate" content="20nov2022">
<meta name="lastupdate" content="14dec2023">
<meta http-equiv="Pragma" content="no-cache">
<title>Edit Coffees</title>

<!-- Exemplo tradicionalmente designado por: CRUD 
						Create					INSERT
						Read (Retrieve)			SELECT
						Update					UPDATE
						Delete (Destroy)		DELETE 

 	 ABCD: Add, Browse, Change and Delete
	 BREAD: Browse, Read, Edit, Add and Delete
	 VADE(R): View, Add, Delete, Edit (e Restore, para sistemas com processos transacionais)
	 VEIA: Visualizar, Excluir, Inserir, Alterar 
-->
</head>
<body>
<form id="search" name="search" method="get" action="index.jsp">
	<input type="hidden" id="where" name="where"/>
	<input type="hidden" id="param"/>
</form>
<script>
	function go(name) {
		if (name == 'cof_name') {
			/* document.getElementById('param').value=document.getElementById(name).value;
			document.getElementById('param').name='old_cof_name';  // muda o nome do input
			document.getElementById('search').action='form.jsp';*/
			document.getElementById('where').value = "cof_name like '%"
					+ document.getElementById(name).value + "%'";
			document.getElementById('search').action = 'index.jsp';
		} else if (name == 'price') {
			document.getElementById('where').value = 'round(price,2)=round('
					+ document.getElementById(name).value + ',2)';
			document.getElementById('search').action = 'index.jsp';
		} else if (name == 'sup_date') 
			document.getElementById('where').value = name + "='"
				+ document.getElementById(name).value+"'";
			else
				document.getElementById('where').value = name + "="
				+ document.getElementById(name).value;
		//document.getElementById('param').value=document.getElementById(name).value;
		//document.getElementById('param').name=name;	
		document.getElementById('search').submit();
	}
</script>
<%
String pk_coffee = request.getParameter("old_cof_name");
String name = "";
String titulo="";
String accao="";
Pojo c = new Pojo();
if(pk_coffee!=null && !pk_coffee.isBlank()) {
	c = Dao.getByName(pk_coffee);
	if(c!=null)
		name = c.getName();
	else
		name = pk_coffee;
	titulo="Update Coffee";
	accao="EditServlet";
}
else {
	pk_coffee="";
	titulo="Add New Coffee";
	accao="SaveServlet";
}
%>
<h1><%=titulo%>&nbsp;
<input title="View coffees" type="button"  value="View"  onClick="javascript:window.open('index.jsp')"/>
&nbsp;<input title="Go back" type="button" value="Back"  onclick="javascript:window.history.back()"/>
</h1>
<form action="<%=accao%>" method="post">
<input type="hidden" value="<%=pk_coffee%>" 		id="old_cof_name" 	name="old_cof_name"/>
<input type="hidden" value="<%=c.getSup_date()%>"  	id="old_sup_date" 	name="old_sup_date"/>
<input type="hidden" value="<%=c.getPrice()%>" 		id="old_price" 		name="old_price"/>
<input type="hidden" value="<%=c.getSales()%>" 		id="old_sales" 		name="old_sales"/>
<input type="hidden" value="<%=c.getTotal()%>" 		id="old_total" 		name="old_total"/>

<input value="form.jsp" type="hidden" name="from" id="from"/>
<script>
	document.getElementById("from").value=
		window.location.pathname.slice(window.location.pathname.lastIndexOf('/') + 1);
</script>
<input value="index.jsp" type="hidden" name="to"/>
<table>
	<tr>
		<td>
			<label for="cof_name">Name:</label>
		</td>
		<td>
	        <input type="text" value="<%=name%>" id="cof_name" name="cof_name" maxlength="32" 
	        pattern="[a-zA-Z _ÁÉÍÓÚàáãâéêíóõôúç]{5,32}" size="32" title="Coffe name (5..32)"/>
	        &nbsp;<input type="button" value="Search" onclick="go('cof_name');"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="sup_date">Sup Date:</label>
		</td>
		<td>
	        <input type="date" value="<%=c.getSup_date()%>" id="sup_date" name="sup_date" title="Supply date..."/>
	        &nbsp;<input type="button" value="Search" onclick="go('sup_date');"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="price">Price &nbsp;&euro;:</label>
		</td>
		<td>
			<input type="number" value="<%=c.getPrice()%>" id="price" name="price"  
				pattern="[0-9]+([\.,][0-9]+)?" min="0.50" max="50.00" step="0.05"
	            title="This should be a number with up to 2 decimal places." style="width: 115px; "/>
	        &nbsp;<input type="button" value="Search" onclick="go('price');"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="sales">Sales:</label>
		</td>
		<td>
			<input type="number" value="<%=c.getSales()%>" id="sales" name="sales"  
				min="0" max="1000" step="1"
	            title="This should be an integer." style="width: 115px; "/>
	        &nbsp;<input type="button" value="Search" onclick="go('sales');"/>
		</td>
	</tr>
	<tr>
		<td>
			<label for="total">Total:</label>
		</td>
		<td>
			<input type="number" value="<%=c.getTotal()%>" id="total" name="total"  
				min="0" max="1000" step="1"
	            title="This should be an integer." style="width: 115px; "/>
	        &nbsp;<input type="button" value="Search" onclick="go('total');"/>
		</td>
	</tr>
	<tr>
		<td colspan="2"><input type="submit" value="Save coffee"/></td>
	</tr>
</table>
</form>
<!-- <iframe style="width:100%;height:100%" src="index.jsp" title="Visualização"></iframe>  -->
<br/>
</body>
</html>