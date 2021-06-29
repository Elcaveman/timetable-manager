<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="http://localhost:8000/static/css/materialize.min.css">
    <link rel="stylesheet" href="http://localhost:8000/static/css/forms.css">
    <link rel="stylesheet" href="http://localhost:8000/static/css/style.css">
    <link href="http://localhost:8000/static/css/material icons.css" rel="stylesheet">
    <link rel="stylesheet" href="http://localhost:8000/static/css/tail.select-light-feather.min.css">
    <link rel="stylesheet" href="http://localhost:8000/static/css/font_awsome.min.css">
    <script src="http://localhost:8000/static/js/tail.select-full.min.js"></script>
    <title>Create Class</title>
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

    <div style="padding-bottom:1rem; ">
				<c:if test="${cl != null}">
                     <div style="text-align: center"><h2 style="color:#b57ebe;">Edit Class</h2></div>
                 </c:if>
                 <c:if test="${cl == null}">
                      <div style="text-align: center"><h2 style="color:#b57ebe;">Create new class</h2></div>
                 </c:if>
           
            <div class="divider"></div>
        </div>
    <main>
    <div class="container" style="text-align: center;">
        <div class="z-depth-1 grey lighten-4 row" style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">

    <div class="row">

       
			<c:if test="${cl != null}">
		            <form class="col s12" action="update" method="post">
		        </c:if>
		        
		        <c:if test="${cl == null}">
		            <form class="col s12" action="insert" method="post">
		        </c:if>
				
            <!-- class_id -->
			 <c:if test="${cl != null}">
            <div class="row number hidden">
              <div class="input-field col s12">
                <i class="material-icons prefix">account_circle</i>
                
                    <input type="hidden" name="id" data-length="60" value="<c:out value='${cl.getClassId()}' />">
                
                
              </div>
            </div>
			</c:if> 
            <!-- group number -->
            <div class="row number">
                <div class="input-field col s12">
                  <i class="material-icons prefix">account_circle</i>
                  <c:if test="${cl!=null}">
                  	<input type="number" name="group" data-length="60" size="45" value="<c:out value='${cl.getClassGroup()}' />">
                  </c:if>
                  <c:if test="${cl==null}">
                  	<input type="number" name="group" data-length="60" size="45">
                  </c:if>
                  <label for="group">Group Number</label>
                </div>
            </div>
            <!-- color -->
            <div class="row">
                    <label for="color">Color: </label>
                    <c:if test="${cl!=null}">
					<input class="input-color-picker" type="color" 
							data-id="color" name="color"  value="<c:out value='${cl.getClassColor()}' />">
					</c:if>
					<c:if test="${cl==null}">
					<input class="input-color-picker" type="color" 
							data-id="color" name="color">
					</c:if>
                    
            </div>
            <!-- faculty_fk -->
            <div class="row number hidden">
                <div class="input-field col s12">
                  <i class="material-icons prefix">account_circle</i>
                  <c:if test="${cl!=null}">
                  	<input id="faculty_fk" type="number" name="faculty_fk" value="<c:out value='${cl.getClassFacultyFk()}' />">
                  </c:if>
                  <c:if test="${cl==null}">
                  	<input id="faculty_fk" type="number" name="faculty_fk">
                  </c:if>
                  <label for="faculty_fk">faculty_id</label>
                </div>
            </div>
            <div class="row select">
                <label for="select-faculty">Faculty : </label>
				<select id="select-faculty" class="select-search" target ="faculty_fk" onchange="populateInputfromSelect()">
               <c:forEach var="faculty" items="${listFaculties}">
                       		<optgroup label='<c:out value="Year ${faculty.getFacultyYear()}"/>'>
                       			<c:if test="${faculty.getFacultyId() == cl.getClassFacultyFk()}">
                        			<option value='<c:out value="${faculty.getFacultyId()}"/>' selected>${faculty.getFacultyYear()} ${faculty.getFacultyAbrev()}</option>
                        		</c:if>
                        		<c:if test="${faculty.getFacultyId() != cl.getClassFacultyFk()}">
                        			<option value='<c:out value="${faculty.getFacultyId()}"/>'>${faculty.getFacultyYear()} ${faculty.getFacultyAbrev()}</option>
                        		</c:if>
                        	</optgroup>
                        </c:forEach>
            </select>
                <script>
                    tail.select(".select-search", {
                        search: true
                    });
                </script>
                
              </div>
        <!-- freetime -->
        <div class="row">
          <div class="col s3" style="padding:1rem;">
              <p >Freetime</p>
          </div>
           <div class="col s9">
           	<c:if test="${cl!=null}">
              <input type="text" name="free_time"  value="<c:out value='${cl.getClassFreeTime()}' />" class='hidden' id='FT_input'>
              </c:if>
              <c:if test="${cl==null}">
              <input type="text" name="free_time" class='hidden' id='FT_input'>
              </c:if>
              <table id="freetime"  class="col s6" ></table>
           </div>
            
           
        </div>
         <div>
                <button type="submit" class="btn wave-effect">submit</button>
            </div>
        
        
        </form>
      </div>  
</div></div>
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
    <script src="http://localhost:8000/static/js/forms.js"></script>
    <%@ include file="/WEB-INF/jsp/messages.jspf" %> 
</body>
</html>