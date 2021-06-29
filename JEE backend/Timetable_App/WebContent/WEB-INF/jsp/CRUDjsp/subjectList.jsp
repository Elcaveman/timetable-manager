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
    <title>subject list</title>
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
	 <h2>Subjects List</h2>
	 <div class="divider"></div>
	</div>
   
     

   
    <div align="center">
    	<a href="/Timetable_App/subject/new">
    	 <div class="btn wave-effect" style="background-color:#696969;">
     	   <i class="material-icons">add</i>Add New subject
    	 </div>
     	</a>
        <table border="1" class="responsive-table highlight">
            <tr>
            <!-- private Long id;
			private String module;
			private String submodule;
			private String type;
			private String abrev;
			private String color;-->
                <th>Id</th>
                <th>Module</th>
                <th>Sub-module</th>
                <th>Subject Type</th>
                <th>Abreviation</th>
                <th>Color</th>
                
                <th>Actions</th>
            </tr>
           
            <c:forEach var="subject" items="${listSubject}">
                <tr>
                    <td><c:out value="${subject.getSubjectId()} " /></td>
                    <td><c:out value="${subject.getSubjectModule()}" /></td>
                    <td><c:out value="${subject.getSubjectSubmodule()}" /></td>
                    <td><c:out value="${subject.getSubjectType()}" /></td>
                    <td><c:out value="${subject.getSubjectAbrev()}" /></td>
                    <td style='color:white;background-color:<c:out value="${subject.getSubjectColor()}" />;'><c:out value="${subject.getSubjectColor()}" /></td>
                    
                    <td>
                        <a href="/Timetable_App/subject/edit?id=<c:out value='${subject.getSubjectId()}' />">
                        <i class="material-icons">create</i>Edit
                        </a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/Timetable_App/subject/delete?id=<c:out value='${subject.getSubjectId()}' />">
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