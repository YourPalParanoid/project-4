<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="../css/style.css" rel="stylesheet">
    <title>Client Home Page Page</title>
</head>
<body>

<h1 style="color: deepskyblue"> Welcome to the Summer 2026 Project 3 Enterprise System </h1>
<h1 style="color: lime"> A Servlet/JSP-based Multi-Tiered Enterprise Application Using a Tomcat Container </h1>

<hr>

<p>You are connected to the Project 3 Enterprise System database as an <em style="color: deepskyblue">accountant-level</em> user. Please select the operation you would like to perform from the list below.</p>

<form action="../accountant" method="post">
<div class="radio">
    <div class="radioButton">
        <input type="radio" name="radio" id="radio1" class="radio" checked value="1">
        <label class=radio for="radio1">Get the Maximum Status of All Suppliers <em style="color: black;"> (Returns a Maximum Value)</em></label>
    </div>
    <div class="radioButton">
        <input type="radio" name="radio" id="radio2" class="radio" value="2" >
        <label for="radio2" class="radio">Get the Total Weight of All Parts <em style="color: black;"> (Returns a Sum)</em></label>
   </div>
    <div class="radioButton">
        <input type="radio" name="radio" id="radio3" class="radio" value="3">
        <label for="radio3" class="radio">Get the Total Number of Shipments <em style="color: black;"> (Returns Current Number of Shipments in Total)</em></label>
    </div>
    <div class="radioButton">
        <input type="radio" name="radio" id="radio4" class="radio" value="4">
        <label for="radio4" class="radio">Get the Name and Number of the Job With the Most Workers <em style="color: black;"> (Returns Two Values)</em></label>
    </div>
    <div class="radioButton">
        <input type="radio" name="radio" id="radio5" class="radio" value="5">
        <label for="radio5" class="radio">List the Name and Status of Every Supplier <em style="color: black;"> (Returns a List of Supplier Names and Their Status)</em></label>
    </div>
</div>
<div style="display: flex;height: 40px; column-gap: 20px">
    <button type="submit"> Execute Command </button>
    <button type="reset" style="color: yellow"> Clear Results </button>
</div>
</form>


<p> All execution results will appear below this line </p>
<hr>
<p> Execution Results: </p>

</body>
</html>