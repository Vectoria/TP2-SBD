<!DOCTYPE html>
<html>
<%@ page import="one.*, java.io.IOException, java.util.List"%>
<%@ page import="pojo.Pojo, db.Dao, java.util.List, java.io.IOException" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="pt-PT, en-US">
<meta name="keywords" content="ISEL, DEETC, JSP, Coffee">
<meta name="description" content="Visualização de cafés">
<meta name="owner" content="ISEL/DEETC - Doutor Porfírio Filipe">
<meta name="copyright" content="ISEL/DEETC/2022">
<meta name="createdate" content="20nov2022">
<meta name="lastupdate" content="14dec2023">
<meta http-equiv="Pragma" content="no-cache">
<title>View Coffees</title>
<link rel="stylesheet" type="text/css" href="style.css" media="all"/>
<link rel="icon" type="image/x-icon">
<style>
.but {
	background-color: Khaki;
    padding: 8px 20px;
    text-decoration:none;
    font-weight:bold;
    border-radius:5px;
    cursor:pointer;
}
</style>
</head>
<body>
<h1>Coffees List&nbsp;
<input title="Add New Coffee" type="button" value="New" onClick="javascript:window.open('form.jsp')"/>
&nbsp;<input title="Go back" type="button" value="Back" onclick="javascript:window.history.back()"/>
</h1>
<%
/* COF_NAME VARCHAR(32), SUP_DATE DATE, PRICE FLOAT, SALES INTEGER, TOTAL INTEGER */

String where=request.getParameter("where");
if(where==null)
	where = "";

String by="";
String param=null;
String order=request.getParameter("OrderByAsc");
if(order==null) {
	order=request.getParameter("OrderByDesc");
	if(order!=null)
		by=order+" DESC";
	param="OrderByAsc";
}
else {
	by=order+" ASC";
	param="OrderByDesc";
}
List<Pojo> list=Dao.getAll(where, by);
%>
<table border='1' class="styled-table">
	<tr>
		<th nowrap style="text-align: left">
			<form method="post">
  				<input type="hidden" name="<%=param%>" value="cof_name" /> 
  				<a class="but" onclick="this.parentNode.submit();">Name</a>
  				<img id="cof_name"/>
			</form>
		</th>
		<th nowrap>
			<form method="post">
  				<input type="hidden" name="<%=param%>" value="sup_date" /> 
  				<a class="but" onclick="this.parentNode.submit();">Sup Date</a>
  				<img id="sup_date"/>
			</form>
		</th>
		<th nowrap>
			<form method="post">
  				<input type="hidden" name="<%=param%>" value="price" /> 
  				<a class="but" onclick="this.parentNode.submit();">Price &euro;</a>
  				<img id="price"/>
			</form>
		</th>
		<th nowrap>
			<form method="post">
  				<input type="hidden" name="<%=param%>" value="sales" /> 
  				<a class="but" onclick="this.parentNode.submit();">Sales</a>
  				<img id="sales"/>
			</form>		
		</th>
		<th nowrap>
			<form method="post">
  				<input type="hidden" name="<%=param%>" value="total" /> 
  				<a class="but" onclick="this.parentNode.submit();">Total</a>
  				<img id="total"/>
			</form>	
		</th>		
		<th colspan='2'>Command</th>
	</tr>
<%
for(Pojo c:list){
%>
	<tr><td style="text-align: left"><%=c.getName()%></td>
		<td style="text-align: center"><%=c.getSup_date()%></td>
		<td style="text-align: right"><%=c.getPrice()%></td>
		<td style="text-align: right"><%=c.getSales()%></td>
		<td style="text-align: right"><%=c.getTotal()%></td>
		<td>
			<form method="post" action="form.jsp">
  				<input type="hidden" name="old_cof_name" value="<%=c.getName()%>" />
  				<a class="but" onclick="this.parentNode.submit();">Edit</a>
			</form>	
		</td>
		<td>
			<form method="post" action="DeleteServlet">
  				<input type="hidden" value="<%=c.getName()%>" 		id="old_cof_name" 	name="old_cof_name" />
  				<input type="hidden" value="<%=c.getSup_date()%>"  	id="old_sup_date" 	name="old_sup_date"/>
				<input type="hidden" value="<%=c.getPrice()%>" 		id="old_price" 		name="old_price"/>
				<input type="hidden" value="<%=c.getSales()%>" 		id="old_sales" 		name="old_sales"/>
				<input type="hidden" value="<%=c.getTotal()%>" 		id="old_total" 		name="old_total"/>
  				<input value="index.jsp" type="hidden" name="to" id="to"/>
  				<script>
  					document.getElementById("to").value=
  						window.location.pathname.slice(window.location.pathname.lastIndexOf('/') + 1);
  				</script>
  				<a class="but" onclick="this.parentNode.submit();">Delete</a>
			</form>	
		</td>
		<%}%>
</table>
<%
order=request.getParameter("OrderByAsc");
if(order==null) {
	order=request.getParameter("OrderByDesc");
	if(order!=null){
	%>
	<script>
		document.getElementById('<%=order%>').src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATCAYAAAByUDbMAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA2ZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDowMTgwMTE3NDA3MjA2ODExQjM4MkY2QzVGRUYwRTJDNCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo4MkFEQzYxQTIyQzExMUUxQTFGMUFEQUQ1QjJBNTM4QyIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo4MkFEQzYxOTIyQzExMUUxQTFGMUFEQUQ1QjJBNTM4QyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M1IE1hY2ludG9zaCI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjAyODAxMTc0MDcyMDY4MTFCMzgyRjZDNUZFRjBFMkM0IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjAxODAxMTc0MDcyMDY4MTFCMzgyRjZDNUZFRjBFMkM0Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+1fsfwAAAAJdJREFUeNpi/P//PwO1ABMDFcGoYaOG0cMwFmyC1Y33IoHUAiD+h8MBGa31SvOJddk6IN4PxBxY8FEgXkO0N4G2/gRSLUD8GU3qPRA3A+U/kxpmIBege2U9EB/ApYERX6kBDDtlILUDiFWA+AkQuwNddY2s2ARqvAukJgDxbyCehM8gnLGJBmYDsSoQTyWkkHHQFo4AAQYAAA0piq4hbqwAAAAASUVORK5CYII=";
		document.getElementById('<%=order%>').title="Descending order!"
	</script>
	<%
	}
}
else {
%>
<script>
	document.getElementById('<%=order%>').src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATCAYAAAByUDbMAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA2ZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDowMTgwMTE3NDA3MjA2ODExQjM4MkY2QzVGRUYwRTJDNCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo4MkFEQzYxNjIyQzExMUUxQTFGMUFEQUQ1QjJBNTM4QyIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo4MkFEQzYxNTIyQzExMUUxQTFGMUFEQUQ1QjJBNTM4QyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M1IE1hY2ludG9zaCI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjAyODAxMTc0MDcyMDY4MTFCMzgyRjZDNUZFRjBFMkM0IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjAxODAxMTc0MDcyMDY4MTFCMzgyRjZDNUZFRjBFMkM0Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+z5ABTAAAAI5JREFUeNpi/P//PwO1ABMDFQELIQXVjfe4gFQbEJe11iv9otRl2UCcBcSphBQy4gszoKu0gNROIJYB4jtA7AF03V2SXQY0iBFIFUMNAgEVIM6DipPsTQcgDkQTSwRia5IMA9rOC6RqgVgQTQokXgOUZyfFZSFQF/zAgh2BOIjkCBjQRDtq2Khh9DAMIMAAT9AmNBDSXegAAAAASUVORK5CYII=";
	document.getElementById('<%=order%>').title="Ascending ordering!"
</script>
<%
}
%>
</body>
</html>