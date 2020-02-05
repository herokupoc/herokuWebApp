<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<jsp:include page="import.jsp"/>

<%@ page import="java.util.*" %>
<%@ page import="com.herokuPOC.entity.*" %>

<%
	List<Airline> airlineList = (ArrayList)request.getAttribute("airlineList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	
	<script type="text/javascript" src="js/jquery-3.4.1.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function()
	    {
			$("#button1").click(function() {
				  $("#button1").html("This button has been clicked");
				  $.ajax({
						url: 'jsp/newAirline.jsp',
						success: function(respuesta) {
							console.log(respuesta);
							$("#button1").html(respuesta);
						},
						error: function(results) {
					        console.log("No se ha podido obtener la información: " + JSON.stringify(results));
					    }
					});
			});
			
			console.log("Ya está disponible");
			
			
			
	    });
		
		

	</script>
	
</head>
<body>

	<h2>Different example with jsp...</h2>
	
	<button id="button1" type="button">Click Me1!</button> 
	<button id="button2" type="button">Click Me2!</button> 
	<button id="button3" type="button">Click Me3!</button>

	<br/><br/>
	<table border="1">
		<thead>
			<th>Codigo</th>
			<th>Nombre</th>
		</thead>
		<tbody>
			<%
			for (Airline airline: airlineList)
			{
			%>
			<tr>
				<td><%=airline.getAirlineId()%></td>
				<td><%=airline.getAirlineName()%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
    
</body>
</html>