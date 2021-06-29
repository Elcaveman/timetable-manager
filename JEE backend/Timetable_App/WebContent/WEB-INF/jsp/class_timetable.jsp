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
           .hamburger-inner, .hamburger-inner::before, .hamburger-inner::after{
			background-color:white;
		}
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
    <title>Class Timetable</title>
</head>
<body class="parallax">
    
	<%@ include file="/WEB-INF/jsp/nav/student-nav.jspf" %> 


	
    <main>
        <div>
        <div style="text-align: center"><h2 style="color:#E57373 !important;">Class Timetable</h2></div>
        <div class="divider"></div>
        
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
    <script src="http://localhost:8000/static/js/student/timetable.js"></script>
     <%@ include file="/WEB-INF/jsp/messages.jspf" %> 
</body>
</html>