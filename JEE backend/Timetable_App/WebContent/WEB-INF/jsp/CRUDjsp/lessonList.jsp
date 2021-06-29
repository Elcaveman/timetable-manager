<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<html>
<head>
	 <link rel="shortcut icon" href="http://localhost:8000/static/img/logo.png" type="image/x-icon" >
        
      <link rel="stylesheet" type="text/css" href="http://localhost:8000/static/css/material icons.css"> 
      <link rel="stylesheet" type="text/css" href="http://localhost:8000/static/css/materialize.min.css"> 
       <link rel="stylesheet" type="text/css" href="http://localhost:8000/static/css/style.css">
       <style>
       	table{
       		width:70%;
       	}
       .material-icons{
      vertical-align: bottom;
       }
       </style>
    <title>Lesson list</title>
</head>
<body>
		<c:if test="${sessionScope.id == null }">
		   <%@ include file="../nav/student-nav.jspf" %> 
		</c:if>
      <c:if test="${sessionScope.type == 'admin'}">
		   <%@ include file="../nav/admin-nav.jspf" %>
		</c:if>
		<c:if test="${sessionScope.type == 'prof'}">
		   <%@ include file="../nav/teacher-nav.jspf" %> 
		</c:if>
		
		<div style="text-align:-webkit-center; margin-bottom:1rem;">
	 <h2>Lessons List</h2>
	 <div class="divider"></div>
	</div>
	
	
	
	<div align="center">
    	<a href="/Timetable_App/lesson/new">
    	 <div class="btn wave-effect" style="background-color:#696969;">
     	   <i class="material-icons">add</i>Add New lesson
    	 </div>
     	</a>
        <table border="1" class="responsive-table highlight">
            <tr>
			
            <!-- private Long id;
			private Long teacher_fk;
			private Long class_fk;
			private Long room_fk ;
			private Long  subject_fk;
			private Long timetable_fk;
			private int total_lessons;
			private String lesson_occ;
			private String lesson_link;
			private String color;
			
			//Details select fields
			private String teacher_name;
			private String info;
			private String room_abrev;
			private String subject_abrev;
			private String class_freetime;
			private String teacher_freetime;
			private String class_color;-->
			
                <th>Id</th>
                <th>Teacher</th>
                <th>Class</th>
                <th>Room</th>
                <th>Subject</th>
                <th>Timetable</th>
                <th>Total Lessons</th>

                <th>Lesson Link</th>
                <th>Color</th>
                
                <th>Actions</th>
            </tr>
           
            <c:forEach var="lesson" items="${listlesson}">
                <tr>
                    <td><c:out value=" ${lesson.getLessonId()} " /></td>
                    <td><c:out value=" ${lesson.getLessonTeacherName()} " /></td>
                    <td><c:out value=" ${lesson.getLessonInfo()} " /></td>
                    <td><c:out value=" ${lesson.getLessonRoomAbrev()} " /></td>
                    <td><c:out value=" ${lesson.getLessonSubjectAbrev()} " /></td>
                    <td><c:out value=" ${lesson.getLessonTimetableFk()} " /></td>
                    <td><c:out value=" ${lesson.getTotalLessons()} " /></td>
                    <td><c:out value=" ${lesson.getLessonLink()} " /></td>
                    <td style='color:white;background-color:<c:out value="${lesson.getLessonColor()}" />;'><c:out value="${lesson.getLessonColor()}" /></td>
                    
                    
                    
					 <td>
                       <a href="/Timetable_App/lesson/edit?id=<c:out value='${lesson.getLessonId()}' />">
                        <i class="material-icons">create</i>Edit
                        </a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/Timetable_App/lesson/delete?id=<c:out value='${lesson.getLessonId()}' />">
                        <i class="material-icons">delete_forever</i>Delete</a>                     
                    </td>

                   
                </tr>
            </c:forEach>
        </table>
    </div> 
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
    <script src="http://localhost:8000/static/js/forms.js"></script>
    <%@ include file="/WEB-INF/jsp/messages.jspf" %> 	
</body>
</html>