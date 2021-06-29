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
    <link rel="stylesheet" href="http://localhost:8000/static/css/timetable.css">
    <link rel="stylesheet" href="http://localhost:8000/static/css/font_awsome.min.css">
    <style>
    	.class{
        	width:10% !important;
        }
        .day{
        	width: 5% !important;
        }
        .cell{
        	width: 21.25%;
        }

    </style>
    <title>Teacher Timetable</title>
</head>
<body class="parallax">
	<c:if test="${sessionScope.id == null }">
		   <%@ include file="/WEB-INF/jsp/nav/student-nav.jspf" %> 
		</c:if>
      <c:if test="${sessionScope.type == 'admin'}">
		   <%@ include file="/WEB-INF/jsp/nav/admin-nav.jspf" %>
		</c:if>
		<c:if test="${sessionScope.type == 'prof'}">
		   <%@ include file="/WEB-INF/jsp/nav/teacher-nav.jspf" %>   
		</c:if>
    
    <main>
        <div>
        <div style="text-align: center"><h2 style="color:#E57373 !important;">Teacher Timetable</h2></div>
        <div class="divider"></div>
        
      </div>
        <div class="timetable" style="margin-bottom: 3rem;">
            <table id="TT_container" ></table>
        </div>
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
    <script>var teacher_id=${teacher};</script>
    <script src="http://localhost:8000/static/js/teacher/timetable.js"></script>
    <%@ include file="/WEB-INF/jsp/messages.jspf" %> 
</body>
</html>
 