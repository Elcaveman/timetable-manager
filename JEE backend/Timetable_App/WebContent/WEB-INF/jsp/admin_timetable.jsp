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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
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
        .yugi{
        	background-color:#234061 !important;
        }
        
        
    </style>
    <title>Timetable Editor</title>
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
        <div style="text-align: center"><h2 style="color:#E57373 !important;">Timetable editor</h2></div>
        <div class="divider"></div>
        <div class="button-list">
          <button class="waves-effect waves-light btn yugi" id="generate_button">Generate TimeTable</button>
          <button class="waves-effect waves-light btn yugi" id="complete_button">Complete TimeTable</button>
          <button class="waves-effect waves-light btn yugi" id="lesson_button">Create Lesson</button>
          <button class="waves-effect waves-light btn yugi" id="reset_button">Reset TimeTable</button>
        </div>
      </div>
        <div class="leftovers-container">
            <p style="text-align:center;">Unset Lessons</p>
          <div class="leftovers" id="TT_leftovers"></div>
          <div style="display:flex;flex-direction:row-reverse;">
            <button class="waves-effect waves-light btn purple lighten-3 disabled" style="margin:0.5rem;" id="undo_button">Undo</button>
            <button class="waves-effect waves-light btn purple lighten-3" style="margin:0.5rem;" id="save_button">Save</button>
          </div>
        </div>
        <div class="timetable">
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
    <script src="http://localhost:8000/static/js/admin/timetable.js"></script>
    <%@ include file="/WEB-INF/jsp/messages.jspf" %> 
</body>
</html>