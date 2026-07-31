<!DOCTYPE html>
<%
	String message = (String) session.getAttribute("message");
	if(message == null) message = " ";
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/style.css" rel="stylesheet">
    <title>Client Home Page Page</title>
</head>
<body>

<h1 style="color: red"> Welcome to the Summer 2026 Project 3 Enterprise System </h1>
<h1 style="color: deepskyblue"> A Servlet/JSP-based Multi-Tiered Enterprise Application Using a Tomcat Container </h1>

<hr>

<p>You are connected to the Project 3 Enterprise System database as a <em style="color: red">client-level</em> user. Please enter any SQL query or update command in the box below.</p>

<form action="../client" method="post">
	<div style="height: 500px;">
		    <label for="query"></label>
		    <textarea
		            id="query"
		            name="query"
		            rows="20"
		            cols="90"
		            required
		    ></textarea>
	</div>
	
	<div style="display: flex;height: 40px; column-gap: 20px">
	    <button type="submit"> Execute Command </button>
	    <button type="reset" style="color: red" onclick="javascript:eraseText();"> Reset Form </button>
	    <button type="button" style="color: yellow" onclick="eraseData();"> Clear Results </button>
	</div>
</form>

<script>
	function eraseData()
	{
		var table = document.getElementById('data');
		table.innerHTML = '';
	}
</script>

<p> All execution results will appear below this line </p>
<hr>
<p> Execution Results: </p>
<center>
	<table id="data" style="background-color: white; color: black; text-align: center">
		<%=message%>
	</table>
</center>

</body>
</html>