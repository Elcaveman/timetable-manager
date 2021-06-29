<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="http://localhost:8000/static/img/logo.png" type="image/x-icon">
    <link href="http://localhost:8000/static/css/material icons.css" rel="stylesheet">
    <link rel="stylesheet" href="http://localhost:8000/static/css/materialize.min.css">
    <link rel="stylesheet" href="http://localhost:8000/static/css/style.css">
    <style>
      header, main, footer {
      padding-left: 30px;
      }
   </style>
    <title>Timetable choice|Admin</title>
</head>

<body>
   <c:if test="${sessionScope.id == null }">
		   <%@ include file="/WEB-INF/jsp/nav/student-nav.jspf" %> 
		</c:if>
      <c:if test="${sessionScope.type == 'admin'}">
		   <%@ include file="/WEB-INF/jsp/nav/admin-nav.jspf" %>
		</c:if>
		<c:if test="${sessionScope.type == 'prof'}">
		   <%@ include file="/WEB-INF/jsp/nav/teacher-nav.jspf" %>   
		</c:if>
	
  <div style="text-align:-webkit-center; margin-bottom:1rem;">
	 <h2>Timetable Choice - Admin</h2>
	 <div class="divider"></div>
	</div> 
  <main>
      
    <div class="row" id="cards_container"></div>
  </main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    var sidenavs = document.querySelectorAll('.sidenav')
    for (var i = 0; i < sidenavs.length; i++){
	    M.Sidenav.init(sidenavs[i]);
    }
    var collapsibles = document.querySelectorAll('.collapsible')
    for (var i = 0; i < collapsibles.length; i++){
	    M.Collapsible.init(collapsibles[i]);
    }});
  

</script>
<script src="http://localhost:8000/static/js/admin/Choices.js"></script>
<%@ include file="/WEB-INF/jsp/messages.jspf" %> 
</body>
